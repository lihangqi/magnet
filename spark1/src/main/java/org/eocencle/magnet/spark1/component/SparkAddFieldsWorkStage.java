package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.spark1.component.handler.SparkAddFieldsHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark添加字段作业节点类
 * @author: huan
 * @Date: 2020-04-28
 * @Description:
 */
public class SparkAddFieldsWorkStage extends AddFieldsWorkStage {
    // Spark添加字段处理器
    private SparkAddFieldsHandler sparkAddFieldsHandler;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.sparkAddFieldsHandler = (SparkAddFieldsHandler) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        SparkWorkStageResult prevResult =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.addFieldsInfo.getRef());

        // 创建RDD
        JavaRDD<Row> rdd = this.sparkAddFieldsHandler.createRDD(prevResult, this.addFieldsInfo);
        // 创建DataFrame
        DataFrame df = this.sparkAddFieldsHandler.createDataFrame ((SQLContext) parameter.getContext().getSQLContext(), rdd);

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.addFieldsInfo.getId());
        result.setAlias(this.addFieldsInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}
