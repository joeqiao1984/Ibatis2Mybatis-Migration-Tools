package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

public class ParameterMapTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument.convertAttributeName("parameterMap", "class", "type");
    }
}
