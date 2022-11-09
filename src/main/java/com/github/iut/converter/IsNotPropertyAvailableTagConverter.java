package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This converter will add prepend/open/close content to the nested sql snippets.
 * The value of property attribute will be used in the alternative "if/test" statements.
 *
 * !!!!WARNING!!!
 * This tag is thought to only be used in the case of map, hence
 * this convert will not include the logic that check whether a property is a valid property in a
 * plain class
 *
 *
 * Case1:
 *
 * Before:
 *         <isNotPropertyAvailable prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true">
 *             name = #name#
 *             <isEmpty property="bar">
 *                  and 1 = 1
 *              </isEmpty>
 *         </isNotPropertyAvailable>
 * After:  <if test="!_parameter.containsKey('foo')">
 *              <![CDATA[ and ( ]]>name = #name#)
 *              <trim prefixOverrides="AND|OR">
 *                 <isEmpty property="bar">
 *                      and 1 = 1
 *                 </isEmpty>
 *              </trim><![CDATA[ ) ]]>
 *         </if>
 *
 */
public class IsNotPropertyAvailableTagConverter implements IConverter{
    private static final Logger log = LogManager.getLogger(IsNotPropertyAvailableTagConverter.class);

    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .prependAttributeValue2TextContent("isNotPropertyAvailable", "open")
                .prependAttributeValue2TextContent("isNotPropertyAvailable", "prepend")
                .appendAttributeValue2TextContent("isNotPropertyAvailable", "close")
                .createNewAttribute("isNotPropertyAvailable", "test", (document, node) -> {
                    final String attributeValue = node.getAttribute("property");
                    if (attributeValue == null || attributeValue.length() == 0) {
                        log.warn("文件{}中isNotPropertyAvailable标签没有property属性，请检查语句是否合法", xmlDocument.getFileName());
                        node.getParentNode().insertBefore(document.createComment("警告！！此处可能存在转换错误，请检查SQL语句是否合法"), node);
                    }
                    return String.format("!_parameter.containsKey('%s')", attributeValue);
                })
                .prependWrapChildTag(e->"true".equals(e.getAttribute("removeFirstPrepend")), "isNotPropertyAvailable", "trim", e->e.setAttribute("prefixOverrides", "AND|OR"))
                .removeAttribute("isNotPropertyAvailable", "property", "prepend", "open", "close", "removeFirstPrepend")
                .convertTagName("isNotPropertyAvailable", "if", e -> true);
    }
}
