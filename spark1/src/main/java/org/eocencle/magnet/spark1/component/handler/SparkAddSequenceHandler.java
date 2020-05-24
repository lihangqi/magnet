package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.AddSequenceInfo;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;

/**
 * Spark添加序列处理器接口
 * @author: huan
 * @Date: 2020-04-30
 * @Description:
 */
public interface SparkAddSequenceHandler extends WorkStageHandler {
    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-04-30
     * @Param [prevResult, addSequenceInfo]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    JavaRDD<Row> createRDD(SparkWorkStageResult prevResult, AddSequenceInfo addSequenceInfo);
    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-04-30
     * @Param [sqlContext, rdd]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    DataFrame createDataFrame(SQLContext sqlContext, JavaRDD rdd);
}
