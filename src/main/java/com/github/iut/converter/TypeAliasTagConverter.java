package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * This converter will comment out typeAlias node and add warning information for further process.
 * Case1
 * Before:
 * <pre>
 *     <typeAlias alias="taskinfo" type="com.test.openframework.asf.model.TaskInstancePo"/>
 * </pre>
 * After:
 * <pre>
 *
 * </pre>
 */

public class TypeAliasTagConverter implements IConverter{
    private static final Logger log = LogManager.getLogger(TypeAliasTagConverter.class);
    private static final String WARNING_INFO = "\n\t警告！！typeAlias节点请集中在MyBatis配置文件中配置\n\t";
    private List<String> removedTypeAlias;

    public TypeAliasTagConverter(final List<String> removedTypeAlias) {
        this.removedTypeAlias = removedTypeAlias;
    }

    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        final NodeList nodeList = xmlDocument.getDocument().getElementsByTagName("typeAlias");
        final List<Element> aliasList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Element aliasNode = (Element) nodeList.item(i);
            aliasList.add(aliasNode);
        }

        aliasList.stream().forEach(aliasNode -> {
            final String typeAliasContent = String.format("<typeAlias alias=\"%s\" type=\"%s\"/>",
                    aliasNode.getAttribute("alias"),
                    aliasNode.getAttribute("type")
            );
            removedTypeAlias.add(typeAliasContent);
            final Comment commentNode = xmlDocument.getDocument().createComment(
                    WARNING_INFO + typeAliasContent + "\n");
            aliasNode.getParentNode().insertBefore(commentNode, aliasNode);
            aliasNode.getParentNode().removeChild(aliasNode);
        });
        return xmlDocument;
    }
}
