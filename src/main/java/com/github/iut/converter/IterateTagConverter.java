package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This converter will replace property variable with item varaible to the nested sql snippets.
 * The value of prepend attribute will be add to "open" attribute.
 * The value of conjunction attribute will be used in the alternative "separator" attribute.
 * Attribute "open" and "close" will remain the same.
 * If removeFirstPrepend is set to "true" or "iterate", there will be an outter tag <trim> to wrap converted node
 */

public class IterateTagConverter implements IConverter {
    private static final Logger log = LogManager.getLogger(IterateTagConverter.class);
    private static final Pattern pattern = Pattern.compile("(\\[\\])");
    private static final Pattern noPropertyPattern = Pattern.compile("(\\#\\[\\])");//fixme
    private static final Pattern noPropertyWithNamePattern = Pattern.compile("(\\#\\w*\\[\\])");//fixme
    private static final Pattern SHARP_VARIABLE_REG = Pattern.compile("#([\\w\\[\\]\\.]+\\[\\])([\\w\\.]*(\\[\\d+\\])*:?\\w*)#");
    private static final Pattern DOLLAR_VARIABLE_REG = Pattern.compile("\\$([\\w\\[\\]\\.]+\\[\\])([\\w\\.]*(\\[\\d+\\])*:?\\w*)\\$");
    private static final Pattern ARRAY_REG = Pattern.compile("\\[\\d+\\]");

    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        final Map<String, String> propertyItemMap = new HashMap<>();
        return preProcess(xmlDocument, propertyItemMap)
                .recursiveWalk(xmlDocument.getDocument().getDocumentElement(),
                        e -> {
                        },
                        e -> {
                            if (e.getTagName().equals("iterate")) {
                                e.setAttribute("item",(e.hasAttribute("property"))?propertyItemMap.get(e.getAttribute("property")):"item");
                                e.setAttribute("collection",(e.hasAttribute("property"))?getPropertyVal(xmlDocument.getFileName(), e.getAttribute("property"),propertyItemMap):"list");

                                if (e.hasAttribute("removeFirstPrepend")) {
                                    log.warn("文件{}中<iterate>标签使用了removeFirstPrepend属性，请检查转换后代码是否可以正确执行", xmlDocument.getFileName());
                                }
                            }else{
                                if (e.hasAttribute("property")) {
                                    final String propertyValue = e.getAttribute("property");
                                    String newPropertyValue = getPropertyVal(xmlDocument.getFileName(), propertyValue,propertyItemMap);
                                    e.setAttribute("property", newPropertyValue);
                                }
                            }
                        },
                        e -> {
                            e.setTextContent(noPropertyPattern.matcher(e.getTextContent()).replaceAll("#item"));
                            final String textContent = convertVariable(xmlDocument.getFileName(), e.getTextContent(), propertyItemMap);
                            e.setTextContent(textContent);
                        }
                )
                .createNewAttribute("iterate", "open", (document, element) -> Arrays.asList("prepend", "open").stream()
                        .filter(e -> element.hasAttribute(e))
                        .map(e -> element.getAttribute(e))
                        .collect(Collectors.joining(" ")))
                .convertAttributeName("iterate", "conjunction", "separator")
                .wrapCurrentTag(e -> "true".equals(e.getAttribute("removeFirstPrepend")) || "iterate".equals(e.getAttribute("removeFirstPrepend")),
                        "iterate", "trim", e -> e.setAttribute("prefixOverrides", "AND|OR"))
                .removeAttribute("iterate", "prepend", "property", "removeFirstPrepend")
                .convertTagName("iterate", "foreach", e -> true);
    }

    private DocumentExt preProcess(final DocumentExt xmlDocument, Map<String, String> propertyItemMap) {
        final NodeList nodeList = xmlDocument.getDocument().getElementsByTagName("iterate");
        final Set<String> itemNames = new HashSet<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Element iterateNode = (Element) nodeList.item(i);
            if (!iterateNode.hasAttribute("property")) {
                xmlDocument.recursiveWalk(iterateNode,
                        e -> {},
                        e -> {},
                        e -> {
                            e.setTextContent(noPropertyWithNamePattern.matcher(e.getTextContent()).replaceAll("#item"));
                        });
            }else{
                final String propertyVal = iterateNode.getAttribute("property");
                propertyItemMap.put(propertyVal, getItemName(propertyVal, itemNames));
            }
        }
        return xmlDocument;
    }

    private static String getItemName(String property, final Set<String> itemNames) {
        final String propertyPart = getPropertyPart(property);
        String itemName = propertyPart + "Item";
        int i = 1;
        while (itemNames.contains(itemName)) {
            itemName = propertyPart + "Item" + i++;
        }
        itemNames.add(itemName);
        return itemName;
    }

    private static String getPropertyVal(String fileName, String originalProp, Map<String, String> iterPropItemMap) {
        final int lastIndex = originalProp.lastIndexOf("[]");
        if (lastIndex > 0) {
            String iterProp = originalProp.substring(0, lastIndex);
            String propertyPart = originalProp.substring(lastIndex + 2);
            final String itemName = iterPropItemMap.get(iterProp);
            if (itemName == null) {
                log.warn("文件{}中iterate引用属性找不到对应记录，原始property属性为{}", fileName, originalProp);
            }
            return itemName + propertyPart;
        }
        return originalProp;
    }

    private static String getPropertyPart(String property) {
        final int dotIndex = property.lastIndexOf(".");
        if (dotIndex < 0) {
            return property;
        }else{
            return property.substring(dotIndex + 1);
        }
    }

    private static String convertVariable(final String fileName, String content, Map<String, String> iterPropItemMap) {
        boolean hasConverted = false;
        Matcher matcher = SHARP_VARIABLE_REG.matcher(content);
        String ret = content;
        while (matcher.find()) {
            String itemPart = matcher.group(1);
            itemPart = itemPart.substring(0, itemPart.length() - 2);
            final String item = iterPropItemMap.get(itemPart);
            if (item == null) {
                log.warn("文件{}中sql片段中iterate引用属性找不到对应记录，属性为{}", fileName, itemPart);
            }
            ret = matcher.replaceFirst("#" + item + "$2" +"#");
            matcher = SHARP_VARIABLE_REG.matcher(ret);
            hasConverted = true;
        }

        matcher = DOLLAR_VARIABLE_REG.matcher(ret);
        while (matcher.find()) {
            String itemPart = matcher.group(1);
            itemPart = itemPart.substring(0, itemPart.length() - 2);
            final String item = iterPropItemMap.get(itemPart);
            if (item == null) {
                log.warn("文件{}中sql片段中iterate引用属性找不到对应记录，属性为{}", fileName, itemPart);
            }
            ret = matcher.replaceFirst("\\$" + item + "$2" +"\\$");
            matcher = DOLLAR_VARIABLE_REG.matcher(ret);
            hasConverted = true;
        }

        if (hasConverted) {
            matcher = ARRAY_REG.matcher(ret);
            if (matcher.find()) {
                log.warn("文件{}中存在<iterate>标签中数组通过下标访问的情况，请检查转换后代码是否可以正确执行", fileName);
            }
        }


        return ret;
    }


}
