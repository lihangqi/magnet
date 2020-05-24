package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.spark1.component.handler.SparkTableRDDLoader;
import org.eocencle.magnet.spark1.util.SparkUtil;

/**
 * Spark文本表加载类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class SparkTextTableLoader extends SparkTableRDDLoader {

    public SparkTextTableLoader(TableInfo tableInfo) {
        super(tableInfo);
    }

    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-01-21
     * @Param [context, src]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    @Override
    public JavaRDD<Row> createRDD(JavaSparkContext context, String src) {
        return SparkUtil.createRDD(context, src, tableInfo.getSeparator(), this.tableInfo.getFields());
    }

    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-01-21
     * @Param [sc, dst, rdd]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    @Override
    public DataFrame createDataFrame(SQLContext sqlContext, StrictMap<DataSourceField> dst, JavaRDD<Row> rdd) {
        return SparkUtil.createDataFrame(sqlContext, dst, rdd);
    }
}
