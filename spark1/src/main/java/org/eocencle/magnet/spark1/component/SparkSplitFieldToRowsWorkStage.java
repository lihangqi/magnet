package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.spark1.component.handler.SparkSplitFieldToRowsHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark列分隔转行作业节点类
 * @author: huan
 * @Date: 2020-04-13
 * @Description:
 */
public class SparkSplitFieldToRowsWorkStage extends SplitFieldToRowsWorkStage {
    // Spark列分隔转行处理器
    private SparkSplitFieldToRowsHandler sparkSplitFieldToRowsHandler;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.sparkSplitFieldToRowsHandler = (SparkSplitFieldToRowsHandler) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        SparkWorkStageResult prevResult =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.splitFieldToRowsInfo.getRef());

        // 创建RDD
        JavaRDD<Row> rdd = this.sparkSplitFieldToRowsHandler.createRDD(prevResult, this.splitFieldToRowsInfo);

        // 创建DataFrame
        DataFrame df = this.sparkSplitFieldToRowsHandler.createDataFrame ((SQLContext) parameter.getContext().getSQLContext(), rdd);

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.splitFieldToRowsInfo.getId());
        result.setAlias(this.splitFieldToRowsInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}
