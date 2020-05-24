package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.AddFieldsInfo;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;

/**
 * Spark添加字段处理器接口
 * @author: huan
 * @Date: 2020-04-28
 * @Description:
 */
public interface SparkAddFieldsHandler extends WorkStageHandler {
    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-04-28
     * @Param [prevResult, addFieldsInfo]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    JavaRDD<Row> createRDD(SparkWorkStageResult prevResult, AddFieldsInfo addFieldsInfo);
    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-04-28
     * @Param [sqlContext, rdd]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    DataFrame createDataFrame(SQLContext sqlContext, JavaRDD rdd);
}
