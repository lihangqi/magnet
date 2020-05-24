package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.spark1.component.handler.SparkFilterCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark过滤作业节点抽象类
 * @author: huan
 * @Date: 2020-03-12
 * @Description:
 */
public class SparkFilterWorkStage extends FilterWorkStage {
    // 过滤器
    private SparkFilterCondition filterCondition;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.filterCondition = (SparkFilterCondition) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        SparkWorkStageResult prevResult =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.filterInfo.getRef());

        // 创建DataFrame
        DataFrame df = this.filterCondition.filter(this.filterInfo.getFilterFields(), prevResult.getDf());
        // 创建RDD
        JavaRDD<Row> rdd = df.toJavaRDD();

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.filterInfo.getId());
        result.setAlias(this.filterInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}
