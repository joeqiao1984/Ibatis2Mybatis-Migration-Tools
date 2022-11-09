package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class SelectTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/SelectTagConverterTest/source1.xml");
        final Document ret = new SelectTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/SelectTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/SelectTagConverterTest/source2.xml");
        final Document ret = new SelectTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/SelectTagConverterTest/source2.xml");
    }

    @Test
    public void convert3() throws Exception {
        final Document document = loadXML("xmls/SelectTagConverterTest/source3.xml");
        final Document ret = new SelectTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/SelectTagConverterTest/expect/convert3.xml");
    }
}