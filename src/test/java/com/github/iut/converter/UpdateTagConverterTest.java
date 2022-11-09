package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class UpdateTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/UpdateTagConverterTest/source1.xml");
        final Document ret = new UpdateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/UpdateTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/UpdateTagConverterTest/source2.xml");
        final Document ret = new UpdateTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/UpdateTagConverterTest/source2.xml");
    }

}