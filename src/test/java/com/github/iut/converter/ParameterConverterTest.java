package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class ParameterConverterTest {
    @Test
    public void convert1() {
        final Document document = loadXML("xmls/ParameterConverterTest/source1.xml");
        final Document ret = new ParameterConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ParameterConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() {
        final Document document = loadXML("xmls/ParameterConverterTest/source2.xml");
        final Document ret = new ParameterConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ParameterConverterTest/expect/convert2.xml");
    }

    @Test
    public void convert3() {
        final Document document = loadXML("xmls/ParameterConverterTest/source3.xml");
        final Document ret = new ParameterConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ParameterConverterTest/expect/convert3.xml");
    }

    @Test
    public void convert4() {
        final Document document = loadXML("xmls/ParameterConverterTest/source4.xml");
        final Document ret = new ParameterConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ParameterConverterTest/expect/convert4.xml");
    }

    @Test
    public void convert5() {
        final Document document = loadXML("xmls/ParameterConverterTest/source5.xml");
        final Document ret = new ParameterConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ParameterConverterTest/expect/convert5.xml");
    }

    @Test
    public void convert6() {
        final Document document = loadXML("xmls/ParameterConverterTest/source6.xml");
        final Document ret = new ParameterConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ParameterConverterTest/expect/convert6.xml");
    }

    @Test
    public void convert7() {
        final Document document = loadXML("xmls/ParameterConverterTest/source7.xml");
        final Document ret = new ParameterConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ParameterConverterTest/expect/convert7.xml");
    }
}