package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.eocencle.magnet.core.mapping.DistinctInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictList;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkDistinctHandler;
import scala.Tuple2;

import java.util.List;

/**
 * Spark去重默认实现类
 * @author: huan
 * @Date: 2020-04-26
 * @Description:
 */
public class SparkDistinctDefaultHandler implements SparkDistinctHandler {
    // schema
    private StructType schema;

    @Override
    public JavaRDD<Row> createRDD(SparkWorkStageResult prevResult, DistinctInfo distinctInfo) {
        boolean hasCntField = StringUtils.isNotBlank(distinctInfo.getCntField());
        this.schema = prevResult.getDf().schema();
        if (hasCntField) {
            this.schema = this.schema.add(distinctInfo.getCntField(), DataTypes.IntegerType, true);
        }

        List<DistinctInfo.DistinctField> fields = distinctInfo.getDistinctFields();

        JavaPairRDD<String, Tuple2<Row, Integer>> pairRdd = prevResult.getRdd().mapToPair((Row row) -> {
            String key = null;
            if (0 == fields.size()) {
                key = row.mkString(CoreTag.SPLIT_INVISIBLE1);
            } else {
                String[] keys = new String[fields.size()];
                DistinctInfo.DistinctField field = null;
                for (int i = 0; i < fields.size(); i++) {
                    field = fields.get(i);
                    keys[i] = row.get(schema.fieldIndex(field.getField())).toString();
                    if (Boolean.parseBoolean(field.getIgnoreCase())) {
                        keys[i] = keys[i].toUpperCase();
                    }
                }
                key = StringUtils.join(keys, CoreTag.SPLIT_INVISIBLE1);
            }
            return new Tuple2<>(key, new Tuple2<>(row, 1));
        });

        JavaPairRDD<String, Tuple2<Row, Integer>> mergeRdd =
                pairRdd.reduceByKey((Tuple2<Row, Integer> v1, Tuple2<Row, Integer> v2) -> new Tuple2<>(v1._1, v1._2 + v2._2));

        return mergeRdd.map((Tuple2<String, Tuple2<Row, Integer>> v1) -> {
            Row row = v1._2._1;
            int cnt = v1._2._2;

            // 获取当前字段
            StrictList<Object> list = new StrictList<>();
            for (int i = 0; i < row.length(); i ++) {
                list.add(row.get(i));
            }

            // 生成新row
            Object[] fieldObjs = null;
            if (hasCntField) {
                fieldObjs = new Object[list.size() + 1];
            } else {
                fieldObjs = new Object[list.size()];
            }

            // 深度复制row
            fieldObjs = list.deepCopy().toArray(fieldObjs);
            // 计数
            if (hasCntField) {
                fieldObjs[fieldObjs.length - 1] = Integer.valueOf(cnt);
            }

            return RowFactory.create(fieldObjs);
        });
    }

    @Override
    public DataFrame createDataFrame(SQLContext sqlContext, JavaRDD rdd) {
        return sqlContext.createDataFrame(rdd, this.schema);
    }
}
