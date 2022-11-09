package com.github.iut.ext;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;
import static org.assertj.core.api.Assertions.assertThat;

class DocumentExtTest {
    @Test
    public void convertTagName1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").convertTagName("sqlMap", "sqlMap1", (e->e.hasAttribute("namespace"))).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/convertTagName1.xml");
    }

    @Test
    public void convertTagName2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").convertTagName("sqlMap", "sqlMap1", (e->e.hasAttribute("asdf"))).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/convertTagName2.xml");
    }

    @Test
    public void convertAttributeName1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").convertAttributeName("sqlMap", "namespace", "id").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/convertAttributeName1.xml");
    }

    @Test
    public void convertAttributeName2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").convertAttributeName("sqlMap", "id", "namespace").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/source.xml");
    }

    @Test
    public void removeAttribute1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").removeAttribute("sqlMap", "namespace").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/removeAttribute1.xml");
    }

    @Test
    public void removeAttribute2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").removeAttribute("sqlMap", "id").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/source.xml");
    }

    @Test
    public void removeAttribute3() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").removeAttribute("resultMap", "id","class").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/removeAttribute2.xml");
    }

    @Test
    public void convertAttributeValue1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").convertAttributeValue("sqlMap", "namespace",e->"1234").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/convertAttributeValue1.xml");
    }

    @Test
    public void convertAttributeValue2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").convertAttributeValue("sqlMap", "id",e->"1234").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/source.xml");
    }

    @Test
    public void createNewAttribute1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").createNewAttribute("sqlMap", "id",(doc,element)->"1234").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/createNewAttribute1.xml");
    }

    @Test
    public void createNewAttribute2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").createNewAttribute("sqlMap", "id",(doc,element)->null).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/createNewAttribute2.xml");
    }

    @Test
    public void createNewAttribute3() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").createNewAttribute("sqlMap", "id",(doc,element)->"").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/createNewAttribute3.xml");
    }

    @Test
    public void recursiveWalk1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/sourceRecursive.xml");
        Stack<String> stack = new Stack<>();
        final Document ret = DocumentExt.of(document,"testInput").recursiveWalk(document.getDocumentElement(),
                e -> {
                    if (e.getTagName().equals("iterate")) {
                        stack.push(e.getAttribute("property"));
                    }
                },
                e -> {
                    if (e.getTagName().equals("iterate")) {
                        stack.pop();
                    }
                },
                e -> {
                    if (!stack.empty()) {
                        e.setTextContent(stack.peek());
                    }
                }
        ).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/recursive1.xml");
    }

    @Test
    public void prependWrapChildTag1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"resultMap", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependWrapChildTag1.xml");
    }

    @Test
    public void prependWrapChildTag2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/source.xml");
    }

    @Test
    public void prependWrapChildTag3() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/sourceCDATA.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/sourceCDATA.xml");
    }

    @Test
    public void prependWrapChildTag4() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source1.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependWrapChildTag2.xml");
    }

    @Test
    public void prependWrapChildTag5() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/sourceCDATA2.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependWrapChildTag3.xml");
    }

    @Test
    public void prependWrapChildTag6() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source1.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->false,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/source1.xml");
    }

    @Test
    public void prependWrapChildTag7() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/sourceCDATA2.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->false,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/sourceCDATA2.xml");
    }

    @Test
    public void prependWrapChildTag8() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependWrapChildTag8.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/prependWrapChildTag8.xml");
    }

    @Test
    public void prependWrapChildTag9() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependWrapChildTag9.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependWrapChildTag4.xml");
    }

    @Test
    public void prependWrapChildTag10() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependWrapChildTag10.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependWrapChildTag5.xml");
    }

    @Test
    public void prependWrapChildTag11() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependWrapChildTag11.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependWrapChildTag6.xml");
    }

    @Test
    public void prependWrapChildTag12() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependWrapChildTag12.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependWrapChildTag7.xml");
    }

    @Test
    public void prependWrapChildTag13() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependWrapChildTag13.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependWrapChildTag8.xml");
    }

    @Test
    public void prependWrapChildTag14() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependWrapChildTag14.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependWrapChildTag9.xml");
    }

    @Test
    public void prependWrapChildTag15() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependWrapChildTag15.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependWrapChildTag(e->true,"select", "trim", e -> e.setAttribute("test","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependWrapChildTag10.xml");
    }

    @Test
    public void prependCDATAChild1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source2.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Document ret = DocumentExt.of(document,"testInput").prependChildContent(selectNode, "test").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependCDATAChild1.xml");
    }
    @Test
    public void prependCDATAChild2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source3.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Document ret = DocumentExt.of(document,"testInput").prependChildContent(selectNode, "test").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependCDATAChild2.xml");
    }

    @Test
    public void appendCDATAChild1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source4.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Document ret = DocumentExt.of(document,"testInput").appendChildContent(selectNode, "test").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/appendCDATAChild1.xml");
    }

    @Test
    public void appendCDATAChild2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source5.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Document ret = DocumentExt.of(document,"testInput").appendChildContent(selectNode, "test").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/appendCDATAChild2.xml");
    }

    @Test
    public void findFirstNonTextContent1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findFirstNonTextContent1.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findFirstNonTextContent(selectNode.getChildNodes());
        assertThat(ret.isPresent()).isFalse();
    }


    @Test
    public void findFirstNonTextContent2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findFirstNonTextContent2.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findFirstNonTextContent(selectNode.getChildNodes());
        assertThat(ret.isPresent()).isFalse();
    }

    @Test
    public void findFirstNonTextContent3() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findFirstNonTextContent3.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findFirstNonTextContent(selectNode.getChildNodes());
        assertThat(ret.isPresent()).isFalse();
    }

    @Test
    public void findFirstNonTextContent4() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findFirstNonTextContent4.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findFirstNonTextContent(selectNode.getChildNodes());
        assertThat(ret.get()).isEqualTo(0);
    }

    @Test
    public void findFirstNonTextContent5() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findFirstNonTextContent5.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findFirstNonTextContent(selectNode.getChildNodes());
        assertThat(ret.get()).isEqualTo(1);
    }

    @Test
    public void findFirstNonTextContent6() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findFirstNonTextContent6.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findFirstNonTextContent(selectNode.getChildNodes());
        assertThat(ret.get()).isEqualTo(1);
    }

    @Test
    public void findLastNonTextContent1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findLastNonTextContent1.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findLastNonTextContent(selectNode.getChildNodes());
        assertThat(ret.isPresent()).isFalse();
    }


    @Test
    public void findLastNonTextContent2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findLastNonTextContent2.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findLastNonTextContent(selectNode.getChildNodes());
        assertThat(ret.isPresent()).isFalse();
    }

    @Test
    public void findLastNonTextContent3() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findLastNonTextContent3.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findLastNonTextContent(selectNode.getChildNodes());
        assertThat(ret.isPresent()).isFalse();
    }

    @Test
    public void findLastNonTextContent4() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findLastNonTextContent4.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findLastNonTextContent(selectNode.getChildNodes());
        assertThat(ret.get()).isEqualTo(0);
    }

    @Test
    public void findLastNonTextContent5() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findLastNonTextContent5.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findLastNonTextContent(selectNode.getChildNodes());
        assertThat(ret.get()).isEqualTo(0);
    }

    @Test
    public void findLastNonTextContent6() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/findLastNonTextContent6.xml");
        final Node selectNode = document.getElementsByTagName("select").item(0);
        final Optional<Integer> ret = DocumentExt.findLastNonTextContent(selectNode.getChildNodes());
        assertThat(ret.get()).isEqualTo(0);
    }

    @Test
    public void convertAttributeValue2TextContent1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/source.xml");
        final List<String> ret = new ArrayList<>();
        DocumentExt.of(document,"testInput").convertAttributeValue2TextContent("result","property", (node, content)->{
            ret.add(content);
        });
        assertThat(ret.size()).isEqualTo(2);
        assertThat(ret.get(0)).isEqualTo("id");
        assertThat(ret.get(1)).isEqualTo("name");
    }

    @Test
    public void prependAttributeValue2TextContent1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependAttributeValue2TextContent1.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependAttributeValue2TextContent("select", "id").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependAttributeValue2TextContent1.xml");
    }

    @Test
    public void prependAttributeValue2TextContent2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependAttributeValue2TextContent2.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependAttributeValue2TextContent("select", "id").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependAttributeValue2TextContent2.xml");
    }

    @Test
    public void prependAttributeValue2TextContent3() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependAttributeValue2TextContent3.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependAttributeValue2TextContent("select", "id").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependAttributeValue2TextContent3.xml");
    }

    @Test
    public void prependAttributeValue2TextContent4() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/prependAttributeValue2TextContent4.xml");
        final Document ret = DocumentExt.of(document,"testInput").prependAttributeValue2TextContent("select", "id").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/prependAttributeValue2TextContent4.xml");
    }

    @Test
    public void appendAttributeValue2TextContent1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/appendAttributeValue2TextContent1.xml");
        final Document ret = DocumentExt.of(document,"testInput").appendAttributeValue2TextContent("select", "id").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/appendAttributeValue2TextContent1.xml");
    }

    @Test
    public void appendAttributeValue2TextContent2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/appendAttributeValue2TextContent2.xml");
        final Document ret = DocumentExt.of(document,"testInput").appendAttributeValue2TextContent("select", "id").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/appendAttributeValue2TextContent2.xml");
    }

    @Test
    public void appendAttributeValue2TextContent3() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/appendAttributeValue2TextContent3.xml");
        final Document ret = DocumentExt.of(document,"testInput").appendAttributeValue2TextContent("select", "id").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/appendAttributeValue2TextContent3.xml");
    }

    @Test
    public void appendAttributeValue2TextContent4() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/appendAttributeValue2TextContent4.xml");
        final Document ret = DocumentExt.of(document,"testInput").appendAttributeValue2TextContent("select", "id").getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/appendAttributeValue2TextContent4.xml");
    }

    @Test
    public void wrapCurrentTag1() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/wrapCurrentTag1.xml");
        final Document ret = DocumentExt.of(document,"testInput").wrapCurrentTag(e->true, "select", "test",e->e.setAttribute("asdf","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/wrapCurrentTag1.xml");
    }

    @Test
    public void wrapCurrentTag2() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/wrapCurrentTag2.xml");
        final Document ret = DocumentExt.of(document,"testInput").wrapCurrentTag(e->true, "select", "test",e->e.setAttribute("asdf","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/expect/wrapCurrentTag2.xml");
    }

    @Test
    public void wrapCurrentTag3() throws Exception {
        final Document document = loadXML("xmls/DocumentExtTest/wrapCurrentTag1.xml");
        final Document ret = DocumentExt.of(document,"testInput").wrapCurrentTag(e->false, "select", "test",e->e.setAttribute("asdf","1234")).getDocument();
        assertXmlEqual(ret, "xmls/DocumentExtTest/wrapCurrentTag1.xml");
    }
}