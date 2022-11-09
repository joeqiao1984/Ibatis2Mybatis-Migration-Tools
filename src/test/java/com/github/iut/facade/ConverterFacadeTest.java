package com.github.iut.facade;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.util.ArrayList;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;

class ConverterFacadeTest {
    @Test
    public void convert1() throws Exception {
        final Document document = loadXML("xmls/IntegrateTest/source1.xml");
        final Document ret = new ConverterFacade().convert(document,"testInput", new ArrayList<>());
        assertXmlEqual(ret, "xmls/IntegrateTest/expect/convert1.xml");
    }

}