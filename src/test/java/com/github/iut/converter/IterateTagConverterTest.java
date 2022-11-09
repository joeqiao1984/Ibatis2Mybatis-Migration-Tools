package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class IterateTagConverterTest {
    @Test
    public void convert1() {
        final Document document = loadXML("xmls/IterateTagConverterTest/source1.xml");
        final Document ret = new IterateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IterateTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() {
        final Document document = loadXML("xmls/IterateTagConverterTest/source2.xml");
        final Document ret = new IterateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IterateTagConverterTest/expect/convert2.xml");
    }

    @Test
    public void convert3() {
        final Document document = loadXML("xmls/IterateTagConverterTest/source3.xml");
        final Document ret = new IterateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IterateTagConverterTest/expect/convert3.xml");
    }

    @Test
    public void convert4() {
        final Document document = loadXML("xmls/IterateTagConverterTest/source4.xml");
        final Document ret = new IterateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IterateTagConverterTest/expect/convert4.xml");
    }

    @Test
    public void convert5() {
        final Document document = loadXML("xmls/IterateTagConverterTest/source5.xml");
        final Document ret = new IterateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IterateTagConverterTest/expect/convert5.xml");
    }

    @Test
    public void convert6() {
        final Document document = loadXML("xmls/IterateTagConverterTest/source6.xml");
        final Document ret = new IterateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IterateTagConverterTest/expect/convert6.xml");
    }

    @Test
    public void convert7() {
        final Document document = loadXML("xmls/IterateTagConverterTest/source7.xml");
        final Document ret = new IterateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IterateTagConverterTest/expect/convert7.xml");
    }

    @Test
    public void convert8() {
        final Document document = loadXML("xmls/IterateTagConverterTest/source8.xml");
        final Document ret = new IterateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IterateTagConverterTest/expect/convert8.xml");
    }

    @Test
    public void convert9() {
        final Document document = loadXML("xmls/IterateTagConverterTest/source9.xml");
        final Document ret = new IterateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IterateTagConverterTest/expect/convert9.xml");
    }

    @Test
    public void convert10() {
        final Document document = loadXML("xmls/IterateTagConverterTest/source10.xml");
        final Document ret = new IterateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IterateTagConverterTest/expect/convert10.xml");
    }
}