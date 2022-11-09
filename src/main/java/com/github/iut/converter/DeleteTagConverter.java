package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

/**
 * Before: <delete parameterClass="abcd"></delete>
 * After:  <delete parameterType="abcd"></delete>
 */
public class DeleteTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument.convertAttributeName("delete", "parameterClass", "parameterType");
    }
}
