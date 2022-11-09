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
 * this convert will not include the logic that check whether a map or an array's size is equal to 0
 *
 *
 * Case1:
 *
 * Before: <isEmpty prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true">name = #name#</isEmpty>
 * After:  <if test="foo == null or @java.lang.String@valueOf(foo).equals('')">and (name = #name#)</if>
 *
 * Case2:
 *
 * Before: <isEmpty prepnd="and" open="(" close =")" property="foo" removeFirstPrepend="true">
 *             name = #name#
 *             <isEqual property="bar" compareValue="1">
 *                 and 1 = 1
 *             </isEqual>
 *         </isEmpty>
 * After: <if test="foo == null or @java.lang.String@valueOf(foo).equals('')">
 *             <![CDATA[ and ( ]]>name = #name#
 *                <trim prefixOverrides="AND|OR">
 *                    <isEqual property="bar" compareValue="1">
 *                        and 1 = 1
 *                    </isEqual>
 *                </trim><![CDATA[ ) ]]>
 *        </if>
 */
public class IsEmptyTagConverter implements IConverter{
    private static final Logger log = LogManager.getLogger(IsEmptyTagConverter.class);

    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .prependAttributeValue2TextContent("isEmpty", "open")
                .prependAttributeValue2TextContent("isEmpty", "prepend")
                .appendAttributeValue2TextContent("isEmpty", "close")
                .createNewAttribute("isEmpty", "test", (document, node) -> {
                    final String attributeValue = node.getAttribute("property");
                    if (attributeValue == null || attributeValue.length() == 0) {
                        log.warn("文件{}中isEmpty标签没有property属性，请检查语句是否合法", xmlDocument.getFileName());
                        node.getParentNode().insertBefore(document.createComment("警告！！此处可能存在转换错误，请检查SQL语句是否合法"), node);
                    }
                    return String.format("%s == null or @java.lang.String@valueOf(%s).equals('')", attributeValue, attributeValue);
                })
                .prependWrapChildTag(e->"true".equals(e.getAttribute("removeFirstPrepend")), "isEmpty", "trim", e->e.setAttribute("prefixOverrides", "AND|OR"))
                .removeAttribute("isEmpty", "prepend", "open", "close", "property", "removeFirstPrepend")
                .convertTagName("isEmpty", "if", e -> true);
    }
}
