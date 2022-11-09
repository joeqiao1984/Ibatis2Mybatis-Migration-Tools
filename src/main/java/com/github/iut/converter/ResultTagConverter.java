package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * This converter will convert result tag form ibatis to mybatis.
 * This converter should be placed before ResultMapTag
 *
 * Case1
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *     </resultMap>
 * </pre>
 *
 * Case2
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" groupBy="insid">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" groupBy="insid">
 *         <id property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *     </resultMap>
 * </pre>
 *
 * Case3
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" groupBy="insEngShrtnm">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" groupBy="insEngShrtnm">
 *         <id property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *         <result property="insid" column="ins_id" />
 *     </resultMap>
 * </pre>
 *
 * Case4
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" groupBy="notexist">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <!--警告！！原始语句存在错误，指定的groupBy字段无法找到对应的result节点-->
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" groupBy="notexist">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *     </resultMap>
 * </pre>
 *
 * Case5
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" groupBy="insid,insChnFullnm">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <!--警告！！暂不支持groupBy包含多个字段的情况，请人工处理-->
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" groupBy="insid,insChnFullnm">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *     </resultMap>
 * </pre>
 *
 * Case6
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" resultMap="anotherMap" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo">
 *         <result property="insid" column="ins_id" />
 *         <collection property="insEngShrtnm" resultMap="anotherMap" />
 *     </resultMap>
 * </pre>
 *
 * Case7
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="{pstRlId=pstRlId,pstBlngInsid=insid}" select="otherSelect" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo">
 *         <result property="insid" column="ins_id" />
 *         <collection property="insEngShrtnm" column="{pstRlId=pstRlId,pstBlngInsid=insid}" select="otherSelect" />
 *     </resultMap>
 * </pre>
 *
 * Case8
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo">
 *         <result property="insEngShrtnm" column="{pstRlId=pstRlId,pstBlngInsid=insid}" select="otherSelect" />
 *         <result property="insid" column="ins_id" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo">
 *         <result property="insid" column="ins_id" />
 *         <collection property="insEngShrtnm" column="{pstRlId=pstRlId,pstBlngInsid=insid}" select="otherSelect" />
 *     </resultMap>
 * </pre>
 */

public class ResultTagConverter implements IConverter{
    private static final Logger log = LogManager.getLogger(ResultTagConverter.class);

    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        final DocumentExt ret = change2IdTag(xmlDocument)
                .convertTagName("result", "collection", e -> e.hasAttribute("resultMap") || e.hasAttribute("select"));
        return moveCollection2LastNode(ret);
    }

    private static DocumentExt change2IdTag(DocumentExt xmlDocument) {
        final NodeList nodeList = xmlDocument.getDocument().getElementsByTagName("resultMap");
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Element resultMapNode = (Element) nodeList.item(i);
            if (resultMapNode.hasAttribute("groupBy")) {
                final String idNodeProperty = resultMapNode.getAttribute("groupBy");
                if (idNodeProperty.contains(",")) {
                    log.warn("文件{}中resultMap标签中存在groupBy属性包含多个字段的情况，暂不支持转换请人工处理", xmlDocument.getFileName());
                    resultMapNode.getParentNode().insertBefore(xmlDocument.getDocument()
                            .createComment("警告！！暂不支持groupBy包含多个字段的情况，请人工处理"), resultMapNode);
                    continue;
                }
                final NodeList childNodes = resultMapNode.getChildNodes();

                Element idNode = null;
                for (int j = 0; j < childNodes.getLength(); j++) {
                    final Node item = childNodes.item(j);
                    if (Node.ELEMENT_NODE == item.getNodeType()) {
                        Element resultNode = (Element) item;
                        if (idNodeProperty.equals(resultNode.getAttribute("property"))) {
                            idNode = resultNode;
                            break;
                        }
                    }
                }

                if (idNode == null) {
                    log.warn("文件{}中resultMap标签的groupBy字段无法找到对应的result节点", xmlDocument.getFileName());
                    resultMapNode.getParentNode().insertBefore(xmlDocument.getDocument()
                            .createComment("警告！！原始语句存在错误，指定的groupBy字段无法找到对应的result节点"), resultMapNode);
                }else{
                    final Node firstChild = resultMapNode.getFirstChild();
                    resultMapNode.insertBefore(idNode, firstChild);
                    xmlDocument.getDocument().renameNode(idNode, null, "id");
                }
            }
        }
        return xmlDocument;
    }

    private static DocumentExt moveCollection2LastNode(DocumentExt xmlDocument) {
        final NodeList nodeList = xmlDocument.getDocument().getElementsByTagName("collection");

        final List<Element> notLastChildCollectionNodes = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Element collectionNode = (Element) nodeList.item(i);
            final Node parentNode = collectionNode.getParentNode();
            if (!collectionNode.equals(parentNode.getLastChild())) {
                notLastChildCollectionNodes.add(collectionNode);
            }
        }

        for (Element notLastChildCollectionNode : notLastChildCollectionNodes) {
            final Node parentNode = notLastChildCollectionNode.getParentNode();
            parentNode.appendChild(notLastChildCollectionNode);
        }
        return xmlDocument;
    }
}
