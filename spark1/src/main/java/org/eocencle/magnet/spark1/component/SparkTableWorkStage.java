package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.spark1.component.handler.SparkTableDataFrameLoader;
import org.eocencle.magnet.spark1.component.handler.SparkTableLoader;
import org.eocencle.magnet.spark1.component.handler.SparkTableRDDLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark表作业节点类
 * @author: huan
 * @Date: 2020-02-01
 * @Description:
 */
public class SparkTableWorkStage extends TableWorkStage {
    // 表加载器
    private SparkTableLoader tableLoader;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.tableLoader = (SparkTableLoader) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();
        Context context = parameter.getContext();

        JavaRDD<Row> rdd = null;
        DataFrame df = null;
        if (this.tableLoader instanceof SparkTableRDDLoader) {
            SparkTableRDDLoader loader = (SparkTableRDDLoader) this.tableLoader;
            // 创建RDD
            rdd = loader.createRDD((JavaSparkContext) context.getContext(), this.tableInfo.getSrc());
            // 创建DataFrame
            df = loader.createDataFrame((SQLContext) context.getSQLContext(), this.tableInfo.getFields(), rdd);
        } else {
            SparkTableDataFrameLoader loader = (SparkTableDataFrameLoader) this.tableLoader;
            // 创建DataFrame
            df = loader.createDataFrame(context, this.tableInfo.getSrc());
            // 创建RDD
            rdd = loader.createRDD(df);
        }

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.tableInfo.getId());
        result.setAlias(this.tableInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}
