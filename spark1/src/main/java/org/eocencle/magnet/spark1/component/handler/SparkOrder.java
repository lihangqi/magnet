package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.OrderInfo;

/**
 * Spark排序器
 * @author: huan
 * @Date: 2020-03-16
 * @Description:
 */
public interface SparkOrder extends WorkStageHandler {
    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-03-16
     * @Param [orderInfo, prevResult]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    JavaRDD<Row> createRDD(DataFrame df);

    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-03-16
     * @Param [orderInfo, df]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    DataFrame createDataFrame(OrderInfo orderInfo, DataFrame df);
}
