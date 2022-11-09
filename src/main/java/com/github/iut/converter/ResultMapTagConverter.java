package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This converter will convert resultMap tag form ibatis to mybatis.
 * This converter will add an autoMapping attribute which will let the mapping behavior be equivalent from ibatis to mybatis
 * This converter should be placed after ResultTag
 *
 * Case1
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo">
 *         <result property="insid" column="ins_id" />
 *         <result property="insChnFullnm" column="INS_CHN_FULLNM" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <resultMap type="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" autoMapping="false">
 *         <result property="insid" column="ins_id" />
 *         <result property="insChnFullnm" column="INS_CHN_FULLNM" />
 *     </resultMap>
 * </pre>
 *
 * Case2
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" extends="parent">
 *         <result property="insid" column="ins_id" />
 *         <result property="insChnFullnm" column="INS_CHN_FULLNM" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <resultMap type="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" extends="parent" autoMapping="false">
 *         <result property="insid" column="ins_id" />
 *         <result property="insChnFullnm" column="INS_CHN_FULLNM" />
 *     </resultMap>
 * </pre>
 *
 * Case3
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" groupBy="insid">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" resultMap="other" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <resultMap type="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" autoMapping="false">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" resultMap="other" />
 *     </resultMap>
 * </pre>
 *
 * Case4
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" groupBy="insid">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="other" select="selectSql" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <resultMap type="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" autoMapping="false">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="other" select="selectSql" />
 *     </resultMap>
 * </pre>
 *
 * Case5
 * Before:
 * <pre>
 *     <resultMap class="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" groupBy="insid">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *     </resultMap>
 * </pre>
 * After:
 * <pre>
 *     <!--警告！！此ResultMap包含groupBy属性，iBatis中groupBy属性有去重作用，Mybatis id标签无此特性，请检查是否存在问题-->
 *     <resultMap type="com.test.data.vdo.InsInfoVdo" id="InsInfoVdo" autoMapping="false">
 *         <result property="insid" column="ins_id" />
 *         <result property="insEngShrtnm" column="INS_ENG_SHRTNM" />
 *     </resultMap>
 * </pre>
 */
public class ResultMapTagConverter implements IConverter{
    private static final Logger log = LogManager.getLogger(ResultMapTagConverter.class);
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return checkGroupBy(xmlDocument)
                .convertAttributeName("resultMap", "class", "type")
                .createNewAttribute("resultMap","autoMapping",(document, element) -> "false")
                .removeAttribute("resultMap", "groupBy");
    }

    private static DocumentExt checkGroupBy(final DocumentExt xmlDocument) {
        final NodeList nodeList = xmlDocument.getDocument().getElementsByTagName("resultMap");
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Element resultMapNode = (Element) nodeList.item(i);
            if (resultMapNode.hasAttribute("groupBy")) {
                final NodeList childNodes = resultMapNode.getChildNodes();
                boolean isResultNodeAllRegular = true;
                for (int j = 0; j < childNodes.getLength(); j++) {
                    final Node item = childNodes.item(j);
                    if (Node.ELEMENT_NODE == item.getNodeType()) {
                        final Element resultNode = (Element) item;
                        final boolean hasColumnAttr = resultNode.hasAttribute("column");
                        final boolean hasSelectAttr = resultNode.hasAttribute("select");
                        if (hasColumnAttr == false || (hasColumnAttr && hasSelectAttr)) {
                            isResultNodeAllRegular = false;
                            break;
                        }
                    }

                }

                if (isResultNodeAllRegular == true) {
                    /* If ResultMap have groupby attribute, while all its child nodes are regular result node(only have  property and column attr)
                     * we can not simply replace groupby with id tag since they have different behavior
                     * Add warning here
                     */
                    log.warn("文件{}中ResultMap包含groupBy属性，iBatis中groupBy属性有去重作用，Mybatis id标签无此特性，请检查是否存在问题", xmlDocument.getFileName());
                    resultMapNode.getParentNode().insertBefore(xmlDocument.getDocument()
                            .createComment("警告！！此ResultMap包含groupBy属性，iBatis中groupBy属性有去重作用，Mybatis id标签无此特性，请检查是否存在问题"), resultMapNode);
                }
            }
        }
        return xmlDocument;
    }
}
