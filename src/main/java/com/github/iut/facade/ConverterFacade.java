package com.github.iut.facade;

import com.github.iut.converter.IConverter;
import com.github.iut.ext.DocumentExt;
import org.w3c.dom.Document;

import java.util.Arrays;
import java.util.List;
import com.github.iut.converter.*;

public class ConverterFacade {
    public Document convert(Document input, String fileName, List<String> typeAliasList) {
        final List<IConverter> converterList = initConverters(typeAliasList);
        DocumentExt docExt = DocumentExt.of(input, fileName);
        for (IConverter converter : converterList) {
            docExt = converter.convert(docExt);
        }
        return docExt.getDocument();
    }

    private List<IConverter> initConverters(final List<String> typeAliasList) {
        return Arrays.asList(
                new DynamicTagConverter(),
                new IterateTagConverter(),
                new SqlMapTagConverter(),
                new ResultTagConverter(),
                new ResultMapTagConverter(),
                new TypeAliasTagConverter(typeAliasList),
                new SelectTagConverter(),
                new UpdateTagConverter(),
                new InsertTagConverter(),
                new DeleteTagConverter(),
                new StatementTagConverter(),
                new ProcedureTagConverter(),
                new ParameterMapTagConverter(),
                new IsEmptyTagConverter(),
                new IsEqualTagConverter(),
                new IsGreaterEqualTagConverter(),
                new IsGreaterThanTagConverter(),
                new IsLessEqualTagConverter(),
                new IsLessThanTagConverter(),
                new IsNotEmptyTagConverter(),
                new IsNotEqualTagConverter(),
                new IsNotNullTagConverter(),
                new IsNotParameterPresentTagConverter(),
                new IsNotPropertyAvailableTagConverter(),
                new IsNullTagConverter(),
                new IsParameterPresentTagConverter(),
                new IsPropertyAvailableTagConverter(),
                new ParameterConverter()
                );
    }
}
