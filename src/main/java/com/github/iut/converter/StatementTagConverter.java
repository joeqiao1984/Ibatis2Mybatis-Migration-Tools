package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

/**
 * Case1:
 *
 * Before: <statement parameterClass="abcd"></statement>
 * After:  <update parameterType="abcd"></update>
 *
 * Case2:
 *
 * Before: <statement parameterClass="abcd" resultClass="abcd"></statement>
 * After:  <select parameterType="abcd" resultType="abcd"></select>
 */
public class StatementTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .convertAttributeName("statement", "parameterClass", "parameterType")
                .convertAttributeName("statement", "resultClass", "resultType")
                .removeAttribute("statement", "remapResults")
                .convertTagName("statement", "select", e -> e.hasAttribute("resultType"))
                .convertTagName("statement", "update", e -> !e.hasAttribute("resultType"));

    }
}
