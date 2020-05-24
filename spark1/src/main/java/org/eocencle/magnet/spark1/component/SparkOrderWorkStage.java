package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.spark1.component.handler.impl.SparkDefaultOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark排序作业节点类
 * @author: huan
 * @Date: 2020-03-16
 * @Description:
 */
public class SparkOrderWorkStage extends OrderWorkStage {
    // 排序器
    private SparkDefaultOrder order;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.order = (SparkDefaultOrder) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        SparkWorkStageResult prevResult =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.orderInfo.getRef());

        // 创建DataFrame
        DataFrame df = this.order.createDataFrame(this.orderInfo, prevResult.getDf());
        // 创建RDD
        JavaRDD<Row> rdd = this.order.createRDD(df);

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.orderInfo.getId());
        result.setAlias(this.orderInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}
