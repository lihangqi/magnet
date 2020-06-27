package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.RowNumInfo;

/**
 * Spark行号生成器
 * @author: huan
 * @Date: 2020-06-07
 * @Description:
 */
public interface SparkRowNumHandler extends WorkStageHandler {
    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-06-07
     * @Param [df]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     */
    JavaRDD<Row> createRDD(DataFrame df);

    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-06-07
     * @Param [sqlContext, rowNumInfo, df, rdd]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     */
    DataFrame createDataFrame(SQLContext sqlContext, RowNumInfo rowNumInfo, DataFrame df, JavaRDD<Row> rdd);
}
