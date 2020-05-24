package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.eocencle.magnet.core.mapping.GroupInfo;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkGrouper;

/**
 * Spark有状态流分组类
 * @author: huan
 * @Date: 2020-05-18
 * @Description: 
 */
public class SparkStreamStateGrouper implements SparkGrouper {
    @Override
    public JavaRDD<Row> createRDD(GroupInfo groupInfo, SparkWorkStageResult prevResult) {
        return null;
    }

    @Override
    public DataFrame createDataFrame(SQLContext sqlContext, GroupInfo groupInfo, SparkWorkStageResult prevResult, JavaRDD<Row> rdd) {
        StructType structType = prevResult.getDf().schema();

        String rownumField = groupInfo.getRownumField();
        if (StringUtils.isNotBlank(rownumField)) {
            structType = structType.add(rownumField, DataTypes.LongType, true);
        }

        return sqlContext.createDataFrame(rdd, structType);
    }
}
