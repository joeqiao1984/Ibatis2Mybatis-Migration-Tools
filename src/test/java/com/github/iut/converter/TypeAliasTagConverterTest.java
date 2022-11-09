package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import static com.github.iut.XmlTestUtils.assertXmlEqual;
import static com.github.iut.XmlTestUtils.loadXML;
import static org.assertj.core.api.Assertions.assertThat;

class TypeAliasTagConverterTest {
    @Test
    public void convert1() throws Exception {
        final List<String> retList = new ArrayList<>();
        final Document document = loadXML("xmls/TypeAliasTagConverterTest/source1.xml");
        final Document ret = new TypeAliasTagConverter(retList).convert(DocumentExt.of(document,"testInput")).getDocument();
        assertXmlEqual(ret, "xmls/TypeAliasTagConverterTest/expect/convert1.xml");
        assertThat(retList.size()).isEqualTo(4);
        assertThat(retList.get(0)).isEqualTo("<typeAlias alias=\"taskinfo\" type=\"com.test.openframework.asf.model.TaskInstancePo\"/>");
        assertThat(retList.get(1)).isEqualTo("<typeAlias alias=\"taskdata\" type=\"com.test.openframework.asf.model.TaskInstanceDataPo\"/>");
        assertThat(retList.get(2)).isEqualTo("<typeAlias alias=\"taskdefineinfo\" type=\"com.test.openframework.asf.model.TaskDefinePo\"/>");
        assertThat(retList.get(3)).isEqualTo("<typeAlias alias=\"taskdefinedata\" type=\"com.test.openframework.asf.model.TaskDefineDataPo\"/>");
    }
}