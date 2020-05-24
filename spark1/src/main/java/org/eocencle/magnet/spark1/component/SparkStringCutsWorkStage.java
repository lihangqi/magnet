package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.spark1.component.handler.SparkStringCutsHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark字符串切割作业节点类
 * @author: huan
 * @Date: 2020-04-24
 * @Description:
 */
public class SparkStringCutsWorkStage extends StringCutsWorkStage {
    // Spark字符串切割处理器
    private SparkStringCutsHandler sparkStringCutsHandler;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.sparkStringCutsHandler = (SparkStringCutsHandler) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        SparkWorkStageResult prevResult =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.stringCutsInfo.getRef());

        // 创建RDD
        JavaRDD<Row> rdd = this.sparkStringCutsHandler.createRDD(prevResult, this.stringCutsInfo);

        // 创建DataFrame
        DataFrame df = this.sparkStringCutsHandler.createDataFrame ((SQLContext) parameter.getContext().getSQLContext(), rdd);

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.stringCutsInfo.getId());
        result.setAlias(this.stringCutsInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}