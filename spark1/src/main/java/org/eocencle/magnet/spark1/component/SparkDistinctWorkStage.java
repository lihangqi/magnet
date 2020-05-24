package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.spark1.component.handler.SparkDistinctHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark去重作业节点类
 * @author: huan
 * @Date: 2020-03-15
 * @Description:
 */
public class SparkDistinctWorkStage extends DistinctWorkStage {
    // Spark去重默认处理器
    private SparkDistinctHandler sparkDistinctHandler;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.sparkDistinctHandler = (SparkDistinctHandler) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        SparkWorkStageResult prevResult =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.distinctInfo.getRef());

        // 创建RDD
        JavaRDD<Row> rdd = this.sparkDistinctHandler.createRDD(prevResult, this.distinctInfo);
        // 创建DataFrame
        DataFrame df = this.sparkDistinctHandler.createDataFrame((SQLContext) parameter.getContext().getSQLContext(), rdd);

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.distinctInfo.getId());
        result.setAlias(this.distinctInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}
