package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.*;
import org.eocencle.magnet.core.mapping.RowNumInfo;
import org.eocencle.magnet.spark1.component.handler.SparkRowNumHandler;
import scala.Tuple2;

/**
 * Spark默认行号生成类
 * @author: huan
 * @Date: 2020-06-07
 * @Description:
 */
public class SparkDefaultRowNumHandler implements SparkRowNumHandler {
    @Override
    public JavaRDD<Row> createRDD(DataFrame df) {
        StructField[] fieldTypes = df.schema().fields();

        return df.javaRDD().zipWithUniqueId().map((Tuple2<Row, Long> tuple2) -> {
            Row row = tuple2._1;
            Object[] fields = new Object[fieldTypes.length + 1];
            for (int i = 0; i < fieldTypes.length; i++) {
                fields[i] = row.get(i);
            }
            fields[fieldTypes.length] = tuple2._2 + 1;
            return RowFactory.create(fields);
        });
    }

    @Override
    public DataFrame createDataFrame(SQLContext sqlContext, RowNumInfo rowNumInfo, DataFrame df, JavaRDD<Row> rdd) {
        StructType structType = df.schema();

        String rownumField = rowNumInfo.getField();
        if (StringUtils.isNotBlank(rownumField)) {
            structType = structType.add(rownumField, DataTypes.LongType, true);
        }

        return sqlContext.createDataFrame(rdd, structType);
    }
}
