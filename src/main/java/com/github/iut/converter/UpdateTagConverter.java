package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

/**
 * Before: <update parameterClass="abcd"></update>
 * After:  <update parameterType="abcd"></update>
 */
public class UpdateTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument.convertAttributeName("update", "parameterClass", "parameterType");
    }
}
