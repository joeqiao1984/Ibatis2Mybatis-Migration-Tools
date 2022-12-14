package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This converter will add prepend/open/close content to the nested sql snippets.
 * The value of property/compareValue/compareProperty attribute will be used in the alternative "if/test" statements.
 *
 * !!!!WARNING!!!
 * This tag is thought to only be used in the case of plain property
 *
 *
 * Case1:
 *
 * Before: <isLessThan prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true" compareValue="3">name = #name#</isLessThan>
 * After:  <if test="foo lt 3"><![CDATA[ and ( ]]>name = #name#<![CDATA[ ) ]]></if>
 *
 * Case2:
 *
 * Before:
 *         <isLessThan prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true" compareProperty="bar">
 *             name = #name#
 *             <isEmpty property="bar">
 *                  and 1 = 1
 *             </isEmpty>
 *         </isLessThan>
 * After:  <if test="foo lt bar">
 *              <![CDATA[ and ( ]]>name = #name#)
 *              <trim prefixOverrides="AND|OR">
 *                 <isEmpty property="bar">
 *                      and 1 = 1
 *                 </isEmpty>
 *              </trim>
 *              <![CDATA[ ) ]]>
 *         </if>
 */
public class IsLessThanTagConverter implements IConverter{
    private static final Logger log = LogManager.getLogger(IsLessThanTagConverter.class);

    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .prependAttributeValue2TextContent("isLessThan", "open")
                .prependAttributeValue2TextContent("isLessThan", "prepend")
                .appendAttributeValue2TextContent("isLessThan", "close")
                .createNewAttribute("isLessThan", "test", (document, node) -> {
                    final String attributeValue = node.getAttribute("property");
                    if (attributeValue == null || attributeValue.length() == 0) {
                        log.warn("??????{}???isLessThan????????????property????????????????????????????????????", xmlDocument.getFileName());
                        node.getParentNode().insertBefore(document.createComment("??????????????????????????????????????????????????????SQL??????????????????"), node);
                        return "";
                    }

                    String compareValue = node.hasAttribute("compareValue") ? node.getAttribute("compareValue") : node.getAttribute("compareProperty");
                    if (compareValue == null || compareValue.length() == 0) {
                        log.warn("??????{}???isLessThan?????????compareValue???compareProperty????????????????????????????????????????????????", xmlDocument.getFileName());
                        node.getParentNode().insertBefore(document.createComment("??????????????????????????????????????????????????????SQL??????????????????"), node);
                        return "";
                    }

                    return String.format("%s lt %s", attributeValue, compareValue);
                })
                .prependWrapChildTag(e->"true".equals(e.getAttribute("removeFirstPrepend")), "isLessThan", "trim", e->e.setAttribute("prefixOverrides", "AND|OR"))
                .removeAttribute("isLessThan", "prepend", "open", "close", "property", "removeFirstPrepend", "compareProperty", "compareValue")
                .convertTagName("isLessThan", "if", e -> true);
    }
}
