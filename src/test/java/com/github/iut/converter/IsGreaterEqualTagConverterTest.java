package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class IsGreaterEqualTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/IsGreaterEqualTagConverterTest/source1.xml");
        final Document ret = new IsGreaterEqualTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterEqualTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/IsGreaterEqualTagConverterTest/source2.xml");
        final Document ret = new IsGreaterEqualTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterEqualTagConverterTest/expect/convert2.xml");
    }

    @Test
    public void convert3() throws Exception {
        final Document document = loadXML("xmls/IsGreaterEqualTagConverterTest/source3.xml");
        final Document ret = new IsGreaterEqualTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterEqualTagConverterTest/expect/convert3.xml");
    }

    @Test
    public void convert4() throws Exception {
        final Document document = loadXML("xmls/IsGreaterEqualTagConverterTest/source4.xml");
        final Document ret = new IsGreaterEqualTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterEqualTagConverterTest/expect/convert4.xml");
    }

    @Test
    public void convert5() throws Exception {
        final Document document = loadXML("xmls/IsGreaterEqualTagConverterTest/source5.xml");
        final Document ret = new IsGreaterEqualTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterEqualTagConverterTest/expect/convert5.xml");
    }

    @Test
    public void convert6() throws Exception {
        final Document document = loadXML("xmls/IsGreaterEqualTagConverterTest/source6.xml");
        final Document ret = new IsGreaterEqualTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterEqualTagConverterTest/expect/convert6.xml");
    }

    @Test
    public void convert7() throws Exception {
        final Document document = loadXML("xmls/IsGreaterEqualTagConverterTest/source7.xml");
        final Document ret = new IsGreaterEqualTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterEqualTagConverterTest/expect/convert7.xml");
    }

    @Test
    public void convert8() throws Exception {
        final Document document = loadXML("xmls/IsGreaterEqualTagConverterTest/source8.xml");
        final Document ret = new IsGreaterEqualTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterEqualTagConverterTest/expect/convert8.xml");
    }

    @Test
    public void convert9() throws Exception {
        final Document document = loadXML("xmls/IsGreaterEqualTagConverterTest/source9.xml");
        final Document ret = new IsGreaterEqualTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsGreaterEqualTagConverterTest/expect/convert9.xml");
    }
}