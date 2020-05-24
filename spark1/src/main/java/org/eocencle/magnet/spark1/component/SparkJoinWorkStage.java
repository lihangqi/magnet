package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.spark1.component.handler.SparkJoinCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark关联作业节点类
 * @author: huan
 * @Date: 2020-04-04
 * @Description:
 */
public class SparkJoinWorkStage extends JoinWorkStage {
    // on条件
    private SparkJoinCondition sparkJoinCondition;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.sparkJoinCondition = (SparkJoinCondition) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        WorkStageComposite parent = this.getParent();
        DataFrame left = ((SparkWorkStageResult) parent.getIdResult(this.joinInfo.getLeftRef())).getDf();
        DataFrame right = ((SparkWorkStageResult) parent.getIdResult(this.joinInfo.getRightRef())).getDf();

        // 创建DataFrame
        DataFrame df = this.sparkJoinCondition.on(this.joinInfo.getFilterFields(), left, right, this.joinInfo.getType());

        // 创建RDD
        JavaRDD<Row> rdd = df.toJavaRDD();

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.joinInfo.getId());
        result.setAlias(this.joinInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}
