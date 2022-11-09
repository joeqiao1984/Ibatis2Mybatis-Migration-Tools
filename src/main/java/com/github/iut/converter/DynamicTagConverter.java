package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This converter will convert dynamic tag to trim tag.
 * The value of prepend and open attributes will be translated to prefix
 * The value of close attribute will be translated to suffix
 * The value of prefixOverrides is evaluted from nested nodes' prepend values
 * <p>
 * Case1
 * Before:
 * <pre>
 *     <select id="find" resultMap="findResult" parameterClass="xxx">
 *         select * from demo where 1=1
 *         <dynamic prepend="and">
 *             <isNotEmpty property="id">
 *                 id = #id#
 *             </isNotEmpty>
 *         </dynamic>
 *     </select>
 * </pre>
 * After:
 * <pre>
 *      <select id="find" resultMap="findResult" parameterClass="xxx">
 *         select * from demo where 1=1
 *         <trim prefix="and">
 *             <isNotEmpty property="id">
 *                 id = #id#
 *             </isNotEmpty>
 *         </trim>
 *     </select>
 * </pre>
 * <p>
 *
 * Case2
 * Before:
 * <pre>
 *     <select id="find" resultMap="findResult" parameterClass="xxx">
 *         select * from demo where 1=1
 *         <dynamic prepend="and" open="(" close=")">
 *             <isNotEmpty property="id">
 *                 id = #id#
 *             </isNotEmpty>
 *         </dynamic>
 *     </select>
 * </pre>
 * After:
 * <pre>
 *      <select id="find" resultMap="findResult" parameterClass="xxx">
 *         select * from demo where 1=1
 *         <trim prefix="and (" suffix=")">
 *             <isNotEmpty property="id">
 *                 id = #id#
 *             </isNotEmpty>
 *         </trim>
 *     </select>
 * </pre>
 *
 * Case3
 * Before:
 * <pre>
 *     <select id="find" resultMap="findResult" parameterClass="xxx">
 *         select * from demo where 1=1
 *         <dynamic>
 *             <isNotEmpty property="id" prepend="and">
 *                 id = #id#
 *             </isNotEmpty>
 *             <isNotEmpty property="name" prepend="or">
 *                 name = #name#
 *             </isNotEmpty>
 *         </dynamic>
 *     </select>
 * </pre>
 * After:
 * <pre>
 *     <select id="find" resultMap="findResult" parameterClass="xxx">
 *         select * from demo where 1=1
 *         <trim>
 *             <isNotEmpty property="id" prepend="and">
 *                 id = #id#
 *             </isNotEmpty>
 *             <isNotEmpty property="name" prepend="or">
 *                 name = #name#
 *             </isNotEmpty>
 *         </trim>
 *     </select>
 * </pre>
 *
 * Case4
 * Before:
 * <pre>
 *     <select id="find" resultMap="findResult" parameterClass="long">
 *         select * from demo
 *         <dynamic prepend="where">
 *             <isNotEmpty property="id" prepend="and">
 *                 id = #id#
 *             </isNotEmpty>
 *             <isNotEmpty property="name" prepend="or">
 *                 name = #name#
 *             </isNotEmpty>
 *         </dynamic>
 *     </select>
 * </pre>
 * After:
 * <pre>
 *     <select id="find" resultMap="findResult" parameterClass="long">
 *         select * from demo
 *         <trim prefix="where" prefixOverrides="and|or">
 *             <isNotEmpty property="id" prepend="and">
 *                 id = #id#
 *             </isNotEmpty>
 *             <isNotEmpty property="name" prepend="or">
 *                 name = #name#
 *             </isNotEmpty>
 *         </trim>
 *     </select>
 * </pre>
 */

public class DynamicTagConverter implements IConverter {
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .createNewAttribute("dynamic", "prefix", (document, element) ->
                        Arrays.asList("prepend", "open")
                                .stream()
                                .filter(e -> element.hasAttribute(e))
                                .map(e -> element.getAttribute(e))
                                .collect(Collectors.joining(" ")))
                .createNewAttribute("dynamic", "prefixOverrides", (document, element) ->
                        element.hasAttribute("prefix") ? collectChildPrependValue(element.getChildNodes())
                                .stream()
                                .map(e -> e.trim())
                                .collect(Collectors.joining("|")) : "")
                .convertAttributeName("dynamic", "close", "suffix")
                .removeAttribute("dynamic", "prepend", "open", "close")
                .convertTagName("dynamic", "trim", e -> true);
    }

    private Set<String> collectChildPrependValue(NodeList children) {
        final Set<String> ret = new LinkedHashSet<>();
        for (int i = 0; i < children.getLength(); i++) {
            final Node item = children.item(i);
            if (Node.ELEMENT_NODE == item.getNodeType()) {
                Element element = (Element) item;
                if (element.hasAttribute("prepend")) {
                    ret.add(element.getAttribute("prepend"));
                }
            }
        }
        return ret;
    }
}
