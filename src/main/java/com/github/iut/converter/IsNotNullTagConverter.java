package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

/**
 * This converter will add prepend/open/close content to the nested sql snippets.
 * The value of property attribute will be used in the alternative "if/test" statements.
 *
 * Case1:
 *
 * Before: <isNotNull prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true">name = #name#</isNotNull>
 * After:  <if test="foo != null"><![CDATA[ and ( ]]>name = #name#<![CDATA[ ) ]]></if>
 *
 * Case2:
 *
 * Before: <isNotNull open="(" close =")" property="foo" removeFirstPrepend="true">
 *              name = #name#
 *              <isEmpty property="bar">
 *                  and 1 = 1
 *              </isEmpty>
 *         </isNotNull>
 * After:  <if test="_parameter != null">
 *              <![CDATA[ and ( ]]>name = #name#)
 *              <trim prefixOverrides="AND|OR">
 *                 <isEmpty property="bar">
 *                      and 1 = 1
 *                 </isEmpty>
 *              </trim><![CDATA[ ) ]]>
 *         </if>
 */
public class IsNotNullTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .prependAttributeValue2TextContent("isNotNull", "open")
                .prependAttributeValue2TextContent("isNotNull", "prepend")
                .appendAttributeValue2TextContent("isNotNull", "close")
                .createNewAttribute("isNotNull", "test", (document, node) -> {
                    String attributeValue = (node.hasAttribute("property"))?node.getAttribute("property"):"_parameter";
                    return String.format("%s != null", attributeValue);
                })
                .prependWrapChildTag(e->"true".equals(e.getAttribute("removeFirstPrepend")), "isNotNull", "trim", e->e.setAttribute("prefixOverrides", "AND|OR"))
                .removeAttribute("isNotNull", "prepend", "open", "close", "property", "removeFirstPrepend")
                .convertTagName("isNotNull", "if", e -> true);
    }
}
