package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class ResultMapTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/ResultMapTagConverterTest/source1.xml");
        final Document ret = new ResultMapTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ResultMapTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/ResultMapTagConverterTest/source2.xml");
        final Document ret = new ResultMapTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ResultMapTagConverterTest/expect/convert2.xml");
    }

    @Test
    public void convert3() throws Exception {
        final Document document = loadXML("xmls/ResultMapTagConverterTest/source3.xml");
        final Document ret = new ResultMapTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ResultMapTagConverterTest/expect/convert3.xml");
    }

    @Test
    public void convert4() throws Exception {
        final Document document = loadXML("xmls/ResultMapTagConverterTest/source4.xml");
        final Document ret = new ResultMapTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ResultMapTagConverterTest/expect/convert4.xml");
    }

    @Test
    public void convert5() throws Exception {
        final Document document = loadXML("xmls/ResultMapTagConverterTest/source5.xml");
        final Document ret = new ResultMapTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ResultMapTagConverterTest/expect/convert5.xml");
    }
}