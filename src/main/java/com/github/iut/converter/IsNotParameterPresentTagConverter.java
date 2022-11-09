package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

/**
 * This converter will add prepend/open/close content to the nested sql snippets.
 * Use _parameter in the alternative "if/test" statements to implement equivalent logic.
 *
 * Case1:
 *
 * Before: <isNotParameterPresent prepnd="and" open="(" close =")" removeFirstPrepend="true">name = #name#</isNotParameterPresent>
 * After:  <if test="_parameter == null"><![CDATA[ and ( ]]>name = #name#<![CDATA[ ) ]]></if>
 *
 * Case2:
 *
 * Before: <isNotParameterPresent open="(" close =")" property="foo" removeFirstPrepend="true">
 *              name = #name#
 *              <isEmpty property="bar">
 *                  and 1 = 1
 *              </isEmpty>
 *         </isNotParameterPresent>
 * After:  <if test="_parameter == null">
 *              <![CDATA[ and ( ]]>name = #name#)
 *              <trim prefixOverrides="AND|OR">
 *                 <isEmpty property="bar">
 *                      and 1 = 1
 *                 </isEmpty>
 *              </trim><![CDATA[ ) ]]>
 *         </if>
 */
public class IsNotParameterPresentTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .prependAttributeValue2TextContent("isNotParameterPresent", "open")
                .prependAttributeValue2TextContent("isNotParameterPresent", "prepend")
                .appendAttributeValue2TextContent("isNotParameterPresent", "close")
                .createNewAttribute("isNotParameterPresent", "test", (document, node) -> "_parameter == null")
                .prependWrapChildTag(e->"true".equals(e.getAttribute("removeFirstPrepend")), "isNotParameterPresent", "trim", e->e.setAttribute("prefixOverrides", "AND|OR"))
                .removeAttribute("isNotParameterPresent", "prepend", "open", "close", "removeFirstPrepend")
                .convertTagName("isNotParameterPresent", "if", e -> true);
    }
}
