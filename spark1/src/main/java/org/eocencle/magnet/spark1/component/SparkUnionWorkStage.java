package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.util.CoreTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark合并作业节点类
 * @author: huan
 * @Date: 2020-04-03
 * @Description:
 */
public class SparkUnionWorkStage extends UnionWorkStage {

    @Override
    public void initHandler(WorkStageHandler handler) {

    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        String[] refs = this.unionInfo.getRefs().split(CoreTag.SPLIT_COMMA);
        WorkStageComposite parent = this.getParent();

        // 创建DataFrame
        DataFrame df = ((SparkWorkStageResult) parent.getIdResult(refs[0])).getDf();

        for (int i = 1; i < refs.length; i ++) {
            df = df.unionAll(((SparkWorkStageResult) parent.getIdResult(refs[i])).getDf());
        }

        // 创建RDD
        JavaRDD<Row> rdd = df.toJavaRDD();

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.unionInfo.getId());
        result.setAlias(this.unionInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}
