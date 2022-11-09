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
 * Before: <isGreaterEqual prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true" compareValue="3">name = #name#</isGreaterEqual>
 * After:  <if test="foo gte 3"><![CDATA[ and ( ]]>name = #name#<![CDATA[ ) ]]></if>
 *
 * Case2:
 *
 * Before: <isGreaterEqual prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true" compareProperty="bar">
 *              name = #name#
 *              <isEmpty property="bar">
 *                  and 1 = 1
 *              </isEmpty>
 *         </isGreaterEqual>
 * After:  <if test="foo gte bar">
 *              <![CDATA[ and ( ]]>name = #name#)
 *              <trim prefixOverrides="AND|OR">
 *                 <isEmpty property="bar">
 *                     and 1 = 1
 *                 </isEmpty>
 *              </trim><![CDATA[ ) ]]>
 *         </if>
 */
public class IsGreaterEqualTagConverter implements IConverter{
    private static final Logger log = LogManager.getLogger(IsGreaterEqualTagConverter.class);

    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .prependAttributeValue2TextContent("isGreaterEqual", "open")
                .prependAttributeValue2TextContent("isGreaterEqual", "prepend")
                .appendAttributeValue2TextContent("isGreaterEqual", "close")
                .createNewAttribute("isGreaterEqual", "test", (document, node) -> {
                    final String attributeValue = node.getAttribute("property");
                    if (attributeValue == null || attributeValue.length() == 0) {
                        log.warn("文件{}中isGreaterEqual标签没有property属性，请检查语句是否合法", xmlDocument.getFileName());
                        node.getParentNode().insertBefore(document.createComment("警告！！此处可能存在转换错误，请检查SQL语句是否合法"), node);
                        return "";
                    }

                    String compareValue = node.hasAttribute("compareValue") ? node.getAttribute("compareValue") : node.getAttribute("compareProperty");
                    if (compareValue == null || compareValue.length() == 0) {
                        log.warn("文件{}中isGreaterEqual标签中compareValue、compareProperty属性均不存在，请检查语句是否合法", xmlDocument.getFileName());
                        node.getParentNode().insertBefore(document.createComment("警告！！此处可能存在转换错误，请检查SQL语句是否合法"), node);
                        return "";
                    }

                    return String.format("%s gte %s", attributeValue, compareValue);
                })
                .prependWrapChildTag(e->"true".equals(e.getAttribute("removeFirstPrepend")), "isGreaterEqual", "trim", e->e.setAttribute("prefixOverrides", "AND|OR"))
                .removeAttribute("isGreaterEqual", "prepend", "open", "close", "property", "removeFirstPrepend", "compareProperty", "compareValue")
                .convertTagName("isGreaterEqual", "if", e -> true);
    }
}
