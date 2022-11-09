package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

/**
 * This converter will add prepend/open/close content to the nested sql snippets.
 * The value of property attribute will be used in the alternative "if/test" statements.
 *
 * Case1:
 *
 * Before: <isNull prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true">name = #name#</isNull>
 * After:  <if test="foo == null"><![CDATA[ and ( ]]>name = #name#<![CDATA[ ) ]]></if>
 *
 * Case2:
 *
 * Before:
 *         <isNull prepnd="and" open="(" close =")" removeFirstPrepend="true">
 *             name = #name#
 *             <isEmpty>
 *                  and 1 = 1
 *             </isEmpty>
 *         </isNull>
 * After:  <if test="_parameter == null">
 *              <![CDATA[ and ( ]]>name = #name#)
 *              <trim prefixOverrides="AND|OR">
 *                 <isEmpty property="bar">
 *                      and 1 = 1
 *                 </isEmpty>
 *              </trim><![CDATA[ ) ]]>
 *         </if>
 */
public class IsNullTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .prependAttributeValue2TextContent("isNull", "open")
                .prependAttributeValue2TextContent("isNull", "prepend")
                .appendAttributeValue2TextContent("isNull", "close")
                .createNewAttribute("isNull", "test", (document, node) -> {
                    String attributeValue = (node.hasAttribute("property"))?node.getAttribute("property"):"_parameter";
                    return String.format("%s == null", attributeValue);
                })
                .prependWrapChildTag(e->"true".equals(e.getAttribute("removeFirstPrepend")), "isNull", "trim", e->e.setAttribute("prefixOverrides", "AND|OR"))
                .removeAttribute("isNull", "prepend", "open", "close", "property", "removeFirstPrepend")
                .convertTagName("isNull", "if", e -> true);
    }
}
