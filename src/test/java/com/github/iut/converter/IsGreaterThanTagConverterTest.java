package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class IsGreaterThanTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/IsGreaterThanTagConverterTest/source1.xml");
        final Document ret = new IsGreaterThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterThanTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/IsGreaterThanTagConverterTest/source2.xml");
        final Document ret = new IsGreaterThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterThanTagConverterTest/expect/convert2.xml");
    }

    @Test
    public void convert3() throws Exception {
        final Document document = loadXML("xmls/IsGreaterThanTagConverterTest/source3.xml");
        final Document ret = new IsGreaterThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterThanTagConverterTest/expect/convert3.xml");
    }

    @Test
    public void convert4() throws Exception {
        final Document document = loadXML("xmls/IsGreaterThanTagConverterTest/source4.xml");
        final Document ret = new IsGreaterThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterThanTagConverterTest/expect/convert4.xml");
    }

    @Test
    public void convert5() throws Exception {
        final Document document = loadXML("xmls/IsGreaterThanTagConverterTest/source5.xml");
        final Document ret = new IsGreaterThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterThanTagConverterTest/expect/convert5.xml");
    }

    @Test
    public void convert6() throws Exception {
        final Document document = loadXML("xmls/IsGreaterThanTagConverterTest/source6.xml");
        final Document ret = new IsGreaterThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterThanTagConverterTest/expect/convert6.xml");
    }

    @Test
    public void convert7() throws Exception {
        final Document document = loadXML("xmls/IsGreaterThanTagConverterTest/source7.xml");
        final Document ret = new IsGreaterThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterThanTagConverterTest/expect/convert7.xml");
    }

    @Test
    public void convert8() throws Exception {
        final Document document = loadXML("xmls/IsGreaterThanTagConverterTest/source8.xml");
        final Document ret = new IsGreaterThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterThanTagConverterTest/expect/convert8.xml");
    }

    @Test
    public void convert9() throws Exception {
        final Document document = loadXML("xmls/IsGreaterThanTagConverterTest/source9.xml");
        final Document ret = new IsGreaterThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterThanTagConverterTest/expect/convert9.xml");
    }
}