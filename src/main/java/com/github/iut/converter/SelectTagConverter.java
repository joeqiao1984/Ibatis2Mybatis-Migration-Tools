package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

/**
 * Before: <select parameterClass="abcd" resultClass="abcd" remapResults="true"></select>
 * After:  <select parameterType="abcd" resultType="abcd"></select>
 */
public class SelectTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument.convertAttributeName("select", "parameterClass", "parameterType")
                .convertAttributeName("select", "resultClass", "resultType")
                .removeAttribute("select", "remapResults");
    }

}
