package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class IsParameterPresentTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/IsParameterPresentTagConverterTest/source1.xml");
        final Document ret = new IsParameterPresentTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsParameterPresentTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/IsParameterPresentTagConverterTest/source2.xml");
        final Document ret = new IsParameterPresentTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsParameterPresentTagConverterTest/expect/convert2.xml");
    }

    @Test
    public void convert3() throws Exception {
        final Document document = loadXML("xmls/IsParameterPresentTagConverterTest/source3.xml");
        final Document ret = new IsParameterPresentTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsParameterPresentTagConverterTest/expect/convert3.xml");
    }

    @Test
    public void convert4() throws Exception {
        final Document document = loadXML("xmls/IsParameterPresentTagConverterTest/source4.xml");
        final Document ret = new IsParameterPresentTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsParameterPresentTagConverterTest/expect/convert4.xml");
    }

    @Test
    public void convert5() throws Exception {
        final Document document = loadXML("xmls/IsParameterPresentTagConverterTest/source5.xml");
        final Document ret = new IsParameterPresentTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsParameterPresentTagConverterTest/expect/convert5.xml");
    }

    @Test
    public void convert6() throws Exception {
        final Document document = loadXML("xmls/IsParameterPresentTagConverterTest/source6.xml");
        final Document ret = new IsParameterPresentTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsParameterPresentTagConverterTest/expect/convert6.xml");
    }

    @Test
    public void convert7() throws Exception {
        final Document document = loadXML("xmls/IsParameterPresentTagConverterTest/source7.xml");
        final Document ret = new IsParameterPresentTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/IsParameterPresentTagConverterTest/expect/convert7.xml");
    }
}