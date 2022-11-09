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
 * Before: <isNotEqual prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true" compareValue="3">name = #name#</isNotEqual>
 * After:  <if test="foo.toString() != '3'.toString()"><![CDATA[ and ( ]]>name = #name#<![CDATA[ ) ]]></if>
 *
 * Case2:
 *
 * Before:
 *         <isNotEqual prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true" compareProperty="bar">
 *             name = #name#
 *             <isEmpty property="bar">
 *                  and 1 = 1
 *             </isEmpty>
 *         </isNotEqual>
 * After:  <if test="!(foo.toString().equals(bar.toString()))">
 *              <![CDATA[ and ( ]]>name = #name#)
 *              <trim prefixOverrides="AND|OR">
 *                 <isEmpty property="bar">
 *                      and 1 = 1
 *                 </isEmpty>
 *              </trim><![CDATA[ ) ]]>
 *         </if>
 */
public class IsNotEqualTagConverter implements IConverter{
    private static final Logger log = LogManager.getLogger(IsNotEqualTagConverter.class);

    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .prependAttributeValue2TextContent("isNotEqual", "open")
                .prependAttributeValue2TextContent("isNotEqual", "prepend")
                .appendAttributeValue2TextContent("isNotEqual", "close")
                .createNewAttribute("isNotEqual", "test", (document, node) -> {
                    final String attributeValue = node.getAttribute("property");
                    if (attributeValue == null || attributeValue.length() == 0) {
                        log.warn("文件{}中isNotEqual标签没有property属性，请检查语句是否合法", xmlDocument.getFileName());
                        node.getParentNode().insertBefore(document.createComment("警告！！此处可能存在转换错误，请检查SQL语句是否合法"), node);
                        return "";
                    }

                    if (node.hasAttribute("compareValue")) {
                        final String compareValue = node.getAttribute("compareValue");
                        return String.format("%s.toString() != '%s'.toString()", attributeValue, compareValue);
                    }else{
                        node.getParentNode().insertBefore(document.createComment("注意！！以下写法为防止两字段类型不同时比较失败，请根据实际情况进行调整"), node);
                        final String compareProperty = node.getAttribute("compareProperty");
                        return String.format("!(%s.toString().equals(%s.toString()))", attributeValue, compareProperty);
                    }
                })
                .prependWrapChildTag(e->"true".equals(e.getAttribute("removeFirstPrepend")), "isNotEqual", "trim", e->e.setAttribute("prefixOverrides", "AND|OR"))
                .removeAttribute("isNotEqual", "prepend", "open", "close", "property", "removeFirstPrepend", "compareProperty", "compareValue")
                .convertTagName("isNotEqual", "if", e -> true);
    }
}
