package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.spark1.component.handler.SparkTableDataFrameLoader;

/**
 * SparkRC格式表作业节点类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class SparkRCTableLoader extends SparkTableDataFrameLoader {

    public SparkRCTableLoader(TableInfo tableInfo) {
        super(tableInfo);
    }

    @Override
    public JavaRDD<Row> createRDD(DataFrame df) {
        return null;
    }

    @Override
    public DataFrame createDataFrame(Context context, String src) {
        return null;
    }

}
