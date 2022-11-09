package com.github.iut.ext;

import com.github.iut.util.StringRegUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DocumentExt {
    private String fileName;
    private Document document;

    private DocumentExt(final Document document, final String fileName) {
        this.document = document;
        this.fileName = fileName;
    }

    public static DocumentExt of(Document document, String fileName) {
        return new DocumentExt(document, fileName);
    }

    public Document getDocument() {
        return document;
    }

    public String getFileName() {
        return fileName;
    }

    public DocumentExt convertTagName(String oldTag, String newTag, Predicate<Element> filter) {
        final NodeList tags = this.document.getElementsByTagName(oldTag);
        for (int i = 0; i < tags.getLength(); i++) {
            final Element node = (Element) tags.item(i);
            if (filter.test(node)) {
                this.document.renameNode(node, null, newTag);
            }
        }
        return this;
    }

    public DocumentExt convertAttributeName(String tag, String oldAttribute, String newAttribute) {
        final NodeList tags = this.document.getElementsByTagName(tag);
        for (int i = 0; i < tags.getLength(); i++) {
            final Element node = (Element) tags.item(i);
            if (node.hasAttribute(oldAttribute)) {
                final String attributeValue = node.getAttribute(oldAttribute);
                node.removeAttribute(oldAttribute);
                node.setAttribute(newAttribute, attributeValue);
            }
        }
        return this;
    }

    public DocumentExt removeAttribute(String tag, String... attributes) {
        final NodeList tags = this.document.getElementsByTagName(tag);
        for (int i = 0; i < tags.getLength(); i++) {
            final Element node = (Element) tags.item(i);
            Arrays.stream(attributes).forEach(e -> node.removeAttribute(e));
        }
        return this;
    }

    public DocumentExt convertAttributeValue(String tag, String attribute, Function<String, String> converter) {
        final NodeList tags = this.document.getElementsByTagName(tag);
        for (int i = 0; i < tags.getLength(); i++) {
            final Element node = (Element) tags.item(i);
            if (node.hasAttribute(attribute)) {
                node.setAttribute(attribute, converter.apply(node.getAttribute(attribute)));
            }
        }
        return this;
    }

    public DocumentExt createNewAttribute(String tag, String attribute, BiFunction<Document, Element, String> converter) {
        final NodeList tags = this.document.getElementsByTagName(tag);
        for (int i = 0; i < tags.getLength(); i++) {
            final Element node = (Element) tags.item(i);
            final String attributValue = converter.apply(this.document, node);
            if (attributValue != null && attributValue.length() > 0) {
                node.setAttribute(attribute, attributValue);
            }
        }
        return this;
    }

    public DocumentExt prependAttributeValue2TextContent(String tag, String attribute) {
        return this.convertAttributeValue2TextContent(tag, attribute, ((node, attributeValue) -> {
            prependChildContent(node, attributeValue);
        }));
    }

    public DocumentExt appendAttributeValue2TextContent(String tag, String attribute) {
        return this.convertAttributeValue2TextContent(tag, attribute, ((node, attributeValue) -> {
            appendChildContent(node, attributeValue);
        }));
    }

    public DocumentExt prependWrapChildTag(Predicate<Element> predicate, String tag, String newTagName, Consumer<Element> nodeSetter) {
        final NodeList tags = this.document.getElementsByTagName(tag);
        for (int i = 0; i < tags.getLength(); i++) {
            final Element parentNode = (Element) tags.item(i);
            if (predicate.test(parentNode)) {
                final Element newElement = this.document.createElement(newTagName);
                final NodeList childNodes = parentNode.getChildNodes();
                final int originalLength = childNodes.getLength();
                if (originalLength > 0) {
                    findFirstNonTextContent(childNodes).ifPresent(j->{
                        findLastNonTextContent(childNodes).ifPresent(k -> {
                            final List<Node> toBeWrappedNodes = IntStream.rangeClosed(j, k).mapToObj(l -> childNodes.item(l)).collect(Collectors.toList());
                            Node lastTextNode = null;
                            if (k < originalLength - 1) { //the last node is not included
                                lastTextNode = childNodes.item(k + 1);
                            }
                            toBeWrappedNodes.stream().forEach(e->newElement.appendChild(e));
                            if (lastTextNode != null) {
                                parentNode.insertBefore(newElement, lastTextNode);
                            }else{
                                parentNode.appendChild(newElement);
                            }
                        });
                    });
                }
                nodeSetter.accept(newElement);
            }
        }
        return this;
    }

    public DocumentExt wrapCurrentTag(Predicate<Element> predicate, String tag, String newTagName, Consumer<Element> nodeSetter) {
        final NodeList tags = this.document.getElementsByTagName(tag);
        for (int i = 0; i < tags.getLength(); i++) {
            final Element currentNode = (Element) tags.item(i);
            if (predicate.test(currentNode)) {
                final Element newElement = this.document.createElement(newTagName);
                final Node parentNode = currentNode.getParentNode();
                parentNode.insertBefore(newElement, currentNode);
                newElement.appendChild(currentNode);
                nodeSetter.accept(newElement);
            }
        }
        return this;
    }


    public DocumentExt recursiveWalk(Node startNode, Consumer<Element> preProcessor, Consumer<Element> postProcessor, Consumer<Node> textConsumer) {
        doRecursiveWalk(startNode, preProcessor, postProcessor, textConsumer);
        return this;
    }

    void doRecursiveWalk(Node node, Consumer<Element> preProcessor, Consumer<Element> postProcessor, Consumer<Node> textConsumer) {
        if (Node.ELEMENT_NODE == node.getNodeType()) {
            preProcessor.accept((Element) node);
            if (node.hasChildNodes()) {
                final NodeList childNodes = node.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    final Node childNode = childNodes.item(i);
                    doRecursiveWalk(childNode, preProcessor, postProcessor, textConsumer);
                }
            }
            postProcessor.accept((Element) node);
        } else if (Node.TEXT_NODE == node.getNodeType() || Node.CDATA_SECTION_NODE == node.getNodeType()) {
            textConsumer.accept(node);
        }
    }

    DocumentExt prependChildContent(Node parentNode, String content) {
        if (parentNode.hasChildNodes()) {
            final Node firstChild = parentNode.getFirstChild();
            if (firstChild.getNodeType() == Node.CDATA_SECTION_NODE) {
                firstChild.setTextContent(StringRegUtils.prepend(firstChild.getTextContent(), content));
            }else{
                parentNode.insertBefore(this.document.createCDATASection(String.format(" %s ", content)), firstChild);
            }
        }else{
            parentNode.appendChild(this.document.createCDATASection(String.format(" %s ", content)));
        }
        return this;
    }

    DocumentExt appendChildContent(Node parentNode, String content) {
        if (parentNode.hasChildNodes()) {
            final Node lastChild = parentNode.getLastChild();
            if (lastChild.getNodeType() == Node.CDATA_SECTION_NODE) {
                lastChild.setTextContent(StringRegUtils.append(lastChild.getTextContent(), content));
            }else{
                parentNode.appendChild(this.document.createCDATASection(String.format(" %s ", content)));
            }
        }else{
            parentNode.appendChild(this.document.createCDATASection(String.format(" %s ", content)));
        }

        return this;
    }

    DocumentExt convertAttributeValue2TextContent(String tag, String attribute, BiConsumer<Node, String> converter) {
        final NodeList tags = this.document.getElementsByTagName(tag);
        for (int i = 0; i < tags.getLength(); i++) {
            final Element node = (Element) tags.item(i);
            if (node.hasAttribute(attribute)) {
                converter.accept(node, node.getAttribute(attribute));
            }
        }
        return this;
    }

    static Optional<Integer> findFirstNonTextContent(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node node = nodeList.item(i);
            if (Node.TEXT_NODE != node.getNodeType() && Node.CDATA_SECTION_NODE != node.getNodeType()) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    static Optional<Integer> findLastNonTextContent(NodeList nodeList) {
        for (int i = nodeList.getLength() - 1; i >= 0; i--) {
            final Node node = nodeList.item(i);
            if (Node.TEXT_NODE != node.getNodeType() && Node.CDATA_SECTION_NODE != node.getNodeType()) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }


}
