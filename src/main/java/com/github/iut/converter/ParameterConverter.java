package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import com.github.iut.util.StringRegUtils;


/**
 * This converter will convert all ibatis variable placeholders to mybatis-format placeholders
 *
 * Case1
 * Before: #id#
 * After: #{id}
 *
 * Case2
 * Before: #id:VARCHAR#
 * After: #{id,jdbcType=VARCHAR}
 *
 * Case3
 * Before: $id$
 * After: ${id}
 *
 * Case4
 * Before: #id:unknown#
 * AFTER: #{id}
 *
 * Case5
 * Before: #id[].a[].b[].c:VARCHAR#
 * After: #{id[].a[].b[].c,jdbcType=VARCHAR}
 * */

public class ParameterConverter implements IConverter{

    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument
                .recursiveWalk(xmlDocument.getDocument().getDocumentElement(),
                        e -> {},
                        e -> {},
                        e -> {
                            final String originalContent = e.getTextContent();
                            if (originalContent != null) {
                                e.setTextContent(StringRegUtils.convertDollarVariable(StringRegUtils.convertSharpVariable(originalContent)));
                            }
                        });
    }
}
