package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This converter will add prepend/open/close content to the nested sql snippets.
 * The value of property attribute will be used in the alternative "if/test" statements.
 *
 * !!!!WARNING!!!
 * This tag is thought to only be used in the case of plain property, hence
 * this convert will not include the logic that check whether a map or an array's size is greater than 0
 *
 *
 * Case1:
 *
 * Before:
 *         <isNotEmpty prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true">
 *             name = #name#
 *             <isEmpty property="bar">
 *                  and 1 = 1
 *             </isEmpty>
 *         </isNotEmpty>
 * After:  <if test="foo != null and !(@java.lang.String@valueOf(foo).equals(''))">
 *              <![CDATA[ and ( ]]>name = #name#)
 *              <trim prefixOverrides="AND|OR">
 *                 <isEmpty property="bar">
 *                      and 1 = 1
 *                 </isEmpty>
 *              </trim><![CDATA[ ) ]]>
 *         </if>
 *
 */
public class IsNotEmptyTagConverter implements IConverter{
    private static final Logger log = LogManager.getLogger(IsNotEmptyTagConverter.class);

    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .prependAttributeValue2TextContent("isNotEmpty", "open")
                .prependAttributeValue2TextContent("isNotEmpty", "prepend")
                .appendAttributeValue2TextContent("isNotEmpty", "close")
                .createNewAttribute("isNotEmpty", "test", (document, node) -> {
                    final String attributeValue = node.getAttribute("property");
                    if (attributeValue == null || attributeValue.length() == 0) {
                        log.warn("文件{}中isNotEmpty标签没有property属性，请检查语句是否合法", xmlDocument.getFileName());
                        node.getParentNode().insertBefore(document.createComment("警告！！此处可能存在转换错误，请检查SQL语句是否合法"), node);
                    }
                    return String.format("%s != null and !(@java.lang.String@valueOf(%s).equals(''))", attributeValue, attributeValue);
                })
                .prependWrapChildTag(e->"true".equals(e.getAttribute("removeFirstPrepend")), "isNotEmpty", "trim", e->e.setAttribute("prefixOverrides", "AND|OR"))
                .removeAttribute("isNotEmpty", "prepend", "open", "close", "property", "removeFirstPrepend")
                .convertTagName("isNotEmpty", "if", e -> true);
    }
}
