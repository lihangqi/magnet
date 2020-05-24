package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.GroupInfo;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;

/**
 * Spark分组器
 * @author: huan
 * @Date: 2020-03-10
 * @Description:
 */
public interface SparkGrouper extends WorkStageHandler {
    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-03-10
     * @Param [groupInfo, prevResult]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    JavaRDD<Row> createRDD(GroupInfo groupInfo, SparkWorkStageResult prevResult);
    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-03-10
     * @Param [sqlContext, groupInfo, prevResult, rdd]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    DataFrame createDataFrame(SQLContext sqlContext, GroupInfo groupInfo, SparkWorkStageResult prevResult, JavaRDD<Row> rdd);
}
