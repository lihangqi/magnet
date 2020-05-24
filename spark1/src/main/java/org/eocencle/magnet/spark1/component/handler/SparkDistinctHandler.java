package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.DistinctInfo;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;

/**
 * Spark去重接口
 * @author: huan
 * @Date: 2020-04-26
 * @Description:
 */
public interface SparkDistinctHandler extends WorkStageHandler {
    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-04-26
     * @Param [prevResult, distinctInfo]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    JavaRDD<Row> createRDD(SparkWorkStageResult prevResult, DistinctInfo distinctInfo);
    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-04-26
     * @Param [sqlContext, rdd]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    DataFrame createDataFrame(SQLContext sqlContext, JavaRDD rdd);
}
