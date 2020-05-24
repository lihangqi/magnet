package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.StringCutsInfo;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;

/**
 * Spark字符串分割处理器接口
 * @author: huan
 * @Date: 2020-04-24
 * @Description:
 */
public interface SparkStringCutsHandler extends WorkStageHandler {
    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-04-24
     * @Param [prevResult, stringCutsInfo]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    JavaRDD<Row> createRDD(SparkWorkStageResult prevResult, StringCutsInfo stringCutsInfo);
    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-04-24
     * @Param [sqlContext, rdd]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    DataFrame createDataFrame(SQLContext sqlContext, JavaRDD rdd);
}