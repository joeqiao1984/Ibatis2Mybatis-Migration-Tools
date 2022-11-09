package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class IsNotPropertyAvailableTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/IsNotPropertyAvailableTagConverterTest/source1.xml");
        final Document ret = new IsNotPropertyAvailableTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsNotPropertyAvailableTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/IsNotPropertyAvailableTagConverterTest/source2.xml");
        final Document ret = new IsNotPropertyAvailableTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsNotPropertyAvailableTagConverterTest/expect/convert2.xml");
    }

    @Test
    public void convert3() throws Exception {
        final Document document = loadXML("xmls/IsNotPropertyAvailableTagConverterTest/source3.xml");
        final Document ret = new IsNotPropertyAvailableTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsNotPropertyAvailableTagConverterTest/expect/convert3.xml");
    }

    @Test
    public void convert4() throws Exception {
        final Document document = loadXML("xmls/IsNotPropertyAvailableTagConverterTest/source4.xml");
        final Document ret = new IsNotPropertyAvailableTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsNotPropertyAvailableTagConverterTest/expect/convert4.xml");
    }

    @Test
    public void convert5() throws Exception {
        final Document document = loadXML("xmls/IsNotPropertyAvailableTagConverterTest/source5.xml");
        final Document ret = new IsNotPropertyAvailableTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsNotPropertyAvailableTagConverterTest/expect/convert5.xml");
    }

    @Test
    public void convert6() throws Exception {
        final Document document = loadXML("xmls/IsNotPropertyAvailableTagConverterTest/source6.xml");
        final Document ret = new IsNotPropertyAvailableTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsNotPropertyAvailableTagConverterTest/expect/convert6.xml");
    }
}