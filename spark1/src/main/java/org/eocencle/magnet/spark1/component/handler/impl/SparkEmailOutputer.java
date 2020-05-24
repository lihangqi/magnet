package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.mapping.OutputInfo;
import org.eocencle.magnet.core.util.EmailUtil;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkOutputer;

import java.util.List;

/**
 * Spark邮件输出器类
 * @author: huan
 * @Date: 2020-03-21
 * @Description:
 */
public class SparkEmailOutputer implements SparkOutputer {
    @Override
    public WorkStageResult output(OutputInfo outputInfo, SparkWorkStageResult result) {
        List<String> collects = result.getRdd().map((Row row) -> {
            String[] val = new String[row.length()];
            for (int i = 0; i < row.length(); i++) {
                if (row.isNullAt(i)) {
                    val[i] = CoreTag.STRING_BLANK;
                } else {
                    val[i] = row.get(i).toString();
                }
            }
            return StringUtils.join(val, outputInfo.getSeparator()) + "\n";
        }).collect();
        StringBuilder sb = new StringBuilder();
        for (String data: collects) {
            sb.append(data);
        }
        EmailUtil.sendEmail(sb.toString(), outputInfo.getOutputConfig());

        return null;
    }
}
