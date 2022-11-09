package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

/**
 * Before: <sqlMap namespace="com.test"></sqlMap>
 * After:  <mapper namespace="com.test"></mapper>
 */
public class SqlMapTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument.convertTagName("sqlMap", "mapper", e->true);
    }
}
