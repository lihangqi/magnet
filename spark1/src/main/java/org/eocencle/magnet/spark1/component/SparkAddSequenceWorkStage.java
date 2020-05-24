package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.spark1.component.handler.SparkAddSequenceHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark添加序列作业节点类
 * @author: huan
 * @Date: 2020-04-30
 * @Description:
 */
public class SparkAddSequenceWorkStage extends AddSequenceWorkStage {
    // Spark添加序列处理器
    private SparkAddSequenceHandler sparkAddSequenceHandler;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.sparkAddSequenceHandler = (SparkAddSequenceHandler) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        SparkWorkStageResult prevResult =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.addSequenceInfo.getRef());

        // 创建RDD
        JavaRDD<Row> rdd = this.sparkAddSequenceHandler.createRDD(prevResult, this.addSequenceInfo);
        // 创建DataFrame
        DataFrame df = this.sparkAddSequenceHandler.createDataFrame ((SQLContext) parameter.getContext().getSQLContext(), rdd);

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.addSequenceInfo.getId());
        result.setAlias(this.addSequenceInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}
