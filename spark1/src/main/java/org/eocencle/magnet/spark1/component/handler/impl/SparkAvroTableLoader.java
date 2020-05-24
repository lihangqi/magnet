package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.spark1.component.handler.SparkTableDataFrameLoader;

/**
 * SparkAvro格式表作业节点类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class SparkAvroTableLoader extends SparkTableDataFrameLoader {

    public SparkAvroTableLoader(TableInfo tableInfo) {
        super(tableInfo);
    }

    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-01-21
     * @Param [df]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    @Override
    public JavaRDD<Row> createRDD(DataFrame df) {
        return df.toJavaRDD();
    }

    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-01-21
     * @Param [context, src]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    @Override
    public DataFrame createDataFrame(Context context, String src) {
        ((JavaSparkContext) context.getContext()).hadoopConfiguration()
                .set("avro.mapred.ignore.inputs.without.extension", "false");
        return ((SQLContext) context.getSQLContext()).read().format("com.databricks.spark.avro").load(src);
    }
}
