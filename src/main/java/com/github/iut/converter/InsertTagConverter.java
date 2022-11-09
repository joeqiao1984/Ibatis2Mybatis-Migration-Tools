package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

/**
 * Before: <insert parameterClass="abcd"></insert>
 * After:  <insert parameterType="abcd"></insert>
 */
public class InsertTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument.convertAttributeName("insert", "parameterClass", "parameterType");
    }
}
