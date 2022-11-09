package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class IsEmptyTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/IsEmptyTagConverterTest/source1.xml");
        final Document ret = new IsEmptyTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsEmptyTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/IsEmptyTagConverterTest/source2.xml");
        final Document ret = new IsEmptyTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsEmptyTagConverterTest/expect/convert2.xml");
    }

    @Test
    public void convert3() throws Exception {
        final Document document = loadXML("xmls/IsEmptyTagConverterTest/source3.xml");
        final Document ret = new IsEmptyTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsEmptyTagConverterTest/expect/convert3.xml");
    }

    @Test
    public void convert4() throws Exception {
        final Document document = loadXML("xmls/IsEmptyTagConverterTest/source4.xml");
        final Document ret = new IsEmptyTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsEmptyTagConverterTest/expect/convert4.xml");
    }

    @Test
    public void convert5() throws Exception {
        final Document document = loadXML("xmls/IsEmptyTagConverterTest/source5.xml");
        final Document ret = new IsEmptyTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsEmptyTagConverterTest/expect/convert5.xml");
    }

    @Test
    public void convert6() throws Exception {
        final Document document = loadXML("xmls/IsEmptyTagConverterTest/source6.xml");
        final Document ret = new IsEmptyTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsEmptyTagConverterTest/expect/convert6.xml");
    }

    @Test
    public void convert7() throws Exception {
        final Document document = loadXML("xmls/IsEmptyTagConverterTest/source7.xml");
        final Document ret = new IsEmptyTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsEmptyTagConverterTest/expect/convert7.xml");
    }

    @Test
    public void convert8() throws Exception {
        final Document document = loadXML("xmls/IsEmptyTagConverterTest/source8.xml");
        final Document ret = new IsEmptyTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsEmptyTagConverterTest/expect/convert8.xml");
    }
}