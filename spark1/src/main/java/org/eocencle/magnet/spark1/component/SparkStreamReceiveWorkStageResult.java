package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.eocencle.magnet.core.component.WorkStageResult;

/**
 * Spark流接收作业结果类
 * @author: huan
 * @Date: 2020-06-28
 * @Description:
 */
public class SparkStreamReceiveWorkStageResult extends WorkStageResult {
    // 流批次数据
    private JavaRDD<String> lines;

    public SparkStreamReceiveWorkStageResult(JavaRDD<String> lines) {
        this.lines = lines;
    }

    public JavaRDD<String> getLines() {
        return lines;
    }

    public void setLines(JavaRDD<String> lines) {
        this.lines = lines;
    }
}
