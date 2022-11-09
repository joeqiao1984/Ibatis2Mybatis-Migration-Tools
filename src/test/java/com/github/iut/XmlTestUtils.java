package com.github.iut;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

import static com.github.iut.Main.PUBLIC_ID;
import static com.github.iut.Main.SYSTEM_ID;
import static org.xmlunit.assertj.XmlAssert.assertThat;

public class XmlTestUtils {
    public static Document loadXML(String filePath) {
        final InputSource inputSource = new InputSource(XmlTestUtils.class.getClassLoader().getResourceAsStream(filePath));
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            final DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            documentBuilder.setEntityResolver((publicId, systemId) -> {
                if (publicId.equals("-//mybatis.org//DTD Mapper 3.0//EN")) {
                    return new InputSource(XmlTestUtils.class.getClassLoader().getResourceAsStream("dtd/mybatis-3-mapper.dtd"));
                }else if(publicId.equals("-//iBATIS.com//DTD SQL Map 2.0//EN")){
                    return new InputSource(XmlTestUtils.class.getClassLoader().getResourceAsStream("dtd/sql-map-2.dtd"));
                }else{
                    return null;
                }
            });

            return documentBuilder.parse(inputSource);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static String print2Str(Document document) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        document.setXmlStandalone(true);
        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,  PUBLIC_ID );
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,  SYSTEM_ID );
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        try {
            final StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document),
                    new StreamResult(writer));
            return writer.toString();
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void assertXmlEqual(Document retDocument, String expectXmlFile) {
        assertThat(Input.fromDocument(retDocument)).and(Input.fromDocument(loadXML(expectXmlFile)))
                .ignoreWhitespace()
                .ignoreElementContentWhitespace()
                .areIdentical();
    }
}
