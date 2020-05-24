package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.eocencle.magnet.core.mapping.AddSequenceInfo;
import org.eocencle.magnet.core.util.StrictList;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkAddSequenceHandler;
import scala.Tuple2;

/**
 * Spark添加序列默认处理器类
 * @author: huan
 * @Date: 2020-04-30
 * @Description:
 */
public class SparkAddSequenceDefaultHandler implements SparkAddSequenceHandler {
    // schema
    private StructType schema;

    @Override
    public JavaRDD<Row> createRDD(SparkWorkStageResult prevResult, AddSequenceInfo addSequenceInfo) {
        this.schema = prevResult.getDf().schema();
        this.schema = this.schema.add(addSequenceInfo.getTagField(), DataTypes.LongType, true);
        long init = Long.parseLong(addSequenceInfo.getInit());
        long step = Long.parseLong(addSequenceInfo.getStep());

        // 生成RDD
        return prevResult.getRdd().zipWithUniqueId().map((Tuple2<Row, Long> v1) -> {
            Row row = v1._1;
            long idx = v1._2;
            idx = idx * step + init;

            // 获取当前字段
            StrictList<Object> list = new StrictList<>();
            for (int i = 0; i < row.length(); i ++) {
                list.add(row.get(i));
            }

            Object[] fieldObjs = new Object[list.size() + 1];
            fieldObjs = list.deepCopy().toArray(fieldObjs);
            fieldObjs[fieldObjs.length - 1] = idx;

            return RowFactory.create(fieldObjs);
        });
    }

    @Override
    public DataFrame createDataFrame(SQLContext sqlContext, JavaRDD rdd) {
        return sqlContext.createDataFrame(rdd, this.schema);
    }
}
