package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class IsLessThanTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/IsLessThanTagConverterTest/source1.xml");
        final Document ret = new IsLessThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsLessThanTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/IsLessThanTagConverterTest/source2.xml");
        final Document ret = new IsLessThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsLessThanTagConverterTest/expect/convert2.xml");
    }

    @Test
    public void convert3() throws Exception {
        final Document document = loadXML("xmls/IsLessThanTagConverterTest/source3.xml");
        final Document ret = new IsLessThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsLessThanTagConverterTest/expect/convert3.xml");
    }

    @Test
    public void convert4() throws Exception {
        final Document document = loadXML("xmls/IsLessThanTagConverterTest/source4.xml");
        final Document ret = new IsLessThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsLessThanTagConverterTest/expect/convert4.xml");
    }

    @Test
    public void convert5() throws Exception {
        final Document document = loadXML("xmls/IsLessThanTagConverterTest/source5.xml");
        final Document ret = new IsLessThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsLessThanTagConverterTest/expect/convert5.xml");
    }

    @Test
    public void convert6() throws Exception {
        final Document document = loadXML("xmls/IsLessThanTagConverterTest/source6.xml");
        final Document ret = new IsLessThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsLessThanTagConverterTest/expect/convert6.xml");
    }

    @Test
    public void convert7() throws Exception {
        final Document document = loadXML("xmls/IsLessThanTagConverterTest/source7.xml");
        final Document ret = new IsLessThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsLessThanTagConverterTest/expect/convert7.xml");
    }

    @Test
    public void convert8() throws Exception {
        final Document document = loadXML("xmls/IsLessThanTagConverterTest/source8.xml");
        final Document ret = new IsLessThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsLessThanTagConverterTest/expect/convert8.xml");
    }

    @Test
    public void convert9() throws Exception {
        final Document document = loadXML("xmls/IsLessThanTagConverterTest/source9.xml");
        final Document ret = new IsLessThanTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsLessThanTagConverterTest/expect/convert9.xml");
    }
}