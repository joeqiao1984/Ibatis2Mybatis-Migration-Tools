package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class DeleteTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/DeleteTagConverterTest/source1.xml");
        final Document ret = new DeleteTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DeleteTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/DeleteTagConverterTest/source2.xml");
        final Document ret = new DeleteTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/DeleteTagConverterTest/source2.xml");
    }
}