package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class ProcedureTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/ProcedureTagConverterTest/source1.xml");
        final Document ret = new ProcedureTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ProcedureTagConverterTest/expect/convert1.xml");
    }

    @Test
    public void convert2() throws Exception {
        final Document document = loadXML("xmls/ProcedureTagConverterTest/source2.xml");
        final Document ret = new ProcedureTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ProcedureTagConverterTest/expect/convert2.xml");
    }

    @Test
    public void convert3() throws Exception {
        final Document document = loadXML("xmls/ProcedureTagConverterTest/source3.xml");
        final Document ret = new ProcedureTagConverter().convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/ProcedureTagConverterTest/expect/convert3.xml");
    }
}