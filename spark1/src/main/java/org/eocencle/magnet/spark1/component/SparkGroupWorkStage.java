package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.spark1.component.handler.SparkGrouper;

import java.util.ArrayList;
import java.util.List;

/**
 * spark组作业节点类
 * @author: huan
 * @Date: 2020-02-02
 * @Description:
 */
public class SparkGroupWorkStage extends GroupWorkStage {
    // 分组器
    private SparkGrouper grouper;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.grouper = (SparkGrouper) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();
        Context context = parameter.getContext();

        SparkWorkStageResult prevResult =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.groupInfo.getRef());

        // 创建RDD
        JavaRDD<Row> rdd = this.grouper.createRDD(this.groupInfo, prevResult);
        // 创建DataFrame
        DataFrame df = this.grouper.createDataFrame((SQLContext) context.getSQLContext(), this.groupInfo, prevResult, rdd);

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.groupInfo.getId());
        result.setAlias(this.groupInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }

}
