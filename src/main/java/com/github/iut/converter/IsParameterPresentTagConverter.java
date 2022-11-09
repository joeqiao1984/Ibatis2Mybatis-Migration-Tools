package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

/**
 * This converter will add prepend/open/close content to the nested sql snippets.
 * Use _parameter in the alternative "if/test" statements to implement equivalent logic.
 *
 * Case1:
 *
 * Before: <isParameterPresent prepnd="and" open="(" close =")" removeFirstPrepend="true">name = #name#</isParameterPresent>
 * After:  <if test="_parameter != null"><![CDATA[ and ( ]]>name = #name#<![CDATA[ ) ]]></if>
 *
 * Case2:
 *
 * Before:
 *         <isParameterPresent prepnd="and" open="(" close =")" removeFirstPrepend="true">
 *             name = #name#
 *             <isEmpty property="bar">
 *                  and 1 = 1
 *              </isEmpty>
 *         </isParameterPresent>
 * After:  <if test="_parameter != null">
 *              <![CDATA[ and ( ]]>name = #name#)
 *              <trim prefixOverrides="AND|OR">
 *                 <isEmpty property="bar">
 *                      and 1 = 1
 *                 </isEmpty>
 *              </trim><![CDATA[ ) ]]>
 *         </if>
 */
public class IsParameterPresentTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .prependAttributeValue2TextContent("isParameterPresent", "open")
                .prependAttributeValue2TextContent("isParameterPresent", "prepend")
                .appendAttributeValue2TextContent("isParameterPresent", "close")
                .createNewAttribute("isParameterPresent", "test", (document, node) -> "_parameter != null")
                .prependWrapChildTag(e->"true".equals(e.getAttribute("removeFirstPrepend")), "isParameterPresent", "trim", e->e.setAttribute("prefixOverrides", "AND|OR"))
                .removeAttribute("isParameterPresent", "prepend", "open", "close", "removeFirstPrepend")
                .convertTagName("isParameterPresent", "if", e -> true);
    }
}
