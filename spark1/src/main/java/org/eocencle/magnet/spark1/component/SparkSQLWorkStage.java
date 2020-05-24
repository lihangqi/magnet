package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.context.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * sparkSQL作业节点类
 * @author: huan
 * @Date: 2020-01-22
 * @Description:
 */
public class SparkSQLWorkStage extends SQLWorkStage {

    @Override
    public void initHandler(WorkStageHandler handler) {

    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();
        Context context = parameter.getContext();

        // 创建DataFrame
        DataFrame df = this.createDataFrame((SQLContext) context.getSQLContext(), this.sqlInfo.getSql());
        // 创建RDD
        JavaRDD<Row> rdd = this.createRDD(df);

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.sqlInfo.getId());
        result.setAlias(this.sqlInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }

    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-01-22
     * @Param [df]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    private JavaRDD<Row> createRDD(DataFrame df) {
        return df.toJavaRDD();
    }

    /**
     * 执行SQL
     * @Author huan
     * @Date 2020-01-22
     * @Param [context, sql]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    private DataFrame createDataFrame(SQLContext context, String sql) {
        return context.sql(sql);
    }
}
