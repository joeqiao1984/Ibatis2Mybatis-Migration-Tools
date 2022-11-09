package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class SqlMapTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/SqlMapTagConverterTest/source.xml");
        final Document ret = new SqlMapTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/SqlMapTagConverterTest/expect/convert1.xml");
    }
}