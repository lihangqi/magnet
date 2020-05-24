package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.SplitFieldToRowsInfo;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;

/**
 * Spark列分隔转行处理器接口
 * @author: huan
 * @Date: 2020-04-15
 * @Description:
 */
public interface SparkSplitFieldToRowsHandler extends WorkStageHandler {
    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-04-15
     * @Param [prevResult, splitFieldToRowsInfo]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    JavaRDD<Row> createRDD(SparkWorkStageResult prevResult, SplitFieldToRowsInfo splitFieldToRowsInfo);
    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-04-15
     * @Param [sqlContext, rdd]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    DataFrame createDataFrame(SQLContext sqlContext, JavaRDD rdd);

}
