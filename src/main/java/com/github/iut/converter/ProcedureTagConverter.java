package com.github.iut.converter;

import com.github.iut.ext.DocumentExt;

/**
 * This converter convert procedure tag to update tag
 *
 * Case1
 * Before:
 * <pre>
 *     <procedure id="pro_dayEndChargeDataPrepare" parameterMap="pro_dayEndChargeDataPrepare_map">
 *         {call DayEndChargeDataPrepare(?,?,?,?,?,?)}
 *     </procedure>
 * </pre>
 * After:
 * <pre>
 *     <update id="pro_dayEndChargeDataPrepare" parameterMap="pro_dayEndChargeDataPrepare_map" statementType="CALLABLE">
 *         {call DayEndChargeDataPrepare(?,?,?,?,?,?)}
 *     </update>
 * </pre>
 *
 * Case2
 * Before:
 * <pre>
 *     <procedure id="P_A0861n002" parameterClass="java.util.Map">
 *         { call P_A0861n002(#empeAplformId#,#crtPsnId#,#aplyPsnId#,#VLD#,#INSERT#,#currentDtTm#) }
 *     </procedure>
 * </pre>
 * After:
 * <pre>
 *     <update id="P_A0861n002" parameterType="java.util.Map" statementType="CALLABLE">
 *         { call P_A0861n002(#empeAplformId#,#crtPsnId#,#aplyPsnId#,#VLD#,#INSERT#,#currentDtTm#) }
 *     </update>
 * </pre>
 */
public class ProcedureTagConverter implements IConverter{
    @Override
    public DocumentExt convert(final DocumentExt xmlDocument) {
        return xmlDocument.convertAttributeName("procedure", "parameterClass", "parameterType")
                .createNewAttribute("procedure","statementType",(document, element) -> "CALLABLE")
                .removeAttribute("procedure", "remapResults")
                .convertTagName("procedure", "update", e->true);
    }
}
