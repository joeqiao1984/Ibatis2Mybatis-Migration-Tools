package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class DynamicTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/DynamicTagConverterTest/source1.xml");
        final Document ret = new DynamicTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DynamicTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/DynamicTagConverterTest/source2.xml");
        final Document ret = new DynamicTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DynamicTagConverterTest/expect/convert2.xml");
    }

    @Test
    public void convert3() throws Exception {
        final Document document = loadXML("xmls/DynamicTagConverterTest/source3.xml");
        final Document ret = new DynamicTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DynamicTagConverterTest/expect/convert3.xml");
    }

    @Test
    public void convert4() throws Exception {
        final Document document = loadXML("xmls/DynamicTagConverterTest/source4.xml");
        final Document ret = new DynamicTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DynamicTagConverterTest/expect/convert4.xml");
    }

    @Test
    public void convert5() throws Exception {
        final Document document = loadXML("xmls/DynamicTagConverterTest/source5.xml");
        final Document ret = new DynamicTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DynamicTagConverterTest/expect/convert5.xml");
    }

    @Test
    public void convert6() throws Exception {
        final Document document = loadXML("xmls/DynamicTagConverterTest/source6.xml");
        final Document ret = new DynamicTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DynamicTagConverterTest/expect/convert6.xml");
    }

    @Test
    public void convert7() throws Exception {
        final Document document = loadXML("xmls/DynamicTagConverterTest/source7.xml");
        final Document ret = new DynamicTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DynamicTagConverterTest/expect/convert7.xml");
    }

    @Test
    public void convert8() throws Exception {
        final Document document = loadXML("xmls/DynamicTagConverterTest/source8.xml");
        final Document ret = new DynamicTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DynamicTagConverterTest/expect/convert8.xml");
    }

    @Test
    public void convert9() throws Exception {
        final Document document = loadXML("xmls/DynamicTagConverterTest/source9.xml");
        final Document ret = new DynamicTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DynamicTagConverterTest/expect/convert9.xml");
    }

    @Test
    public void convert10() throws Exception {
        final Document document = loadXML("xmls/DynamicTagConverterTest/source10.xml");
        final Document ret = new DynamicTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DynamicTagConverterTest/expect/convert10.xml");
    }
}