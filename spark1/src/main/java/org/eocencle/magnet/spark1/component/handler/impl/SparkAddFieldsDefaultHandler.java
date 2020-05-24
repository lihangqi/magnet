package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.StructType;
import org.eocencle.magnet.core.mapping.AddFieldsInfo;
import org.eocencle.magnet.core.util.StrictList;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkAddFieldsHandler;
import org.eocencle.magnet.spark1.util.SparkUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Spark添加字段默认处理器类
 * @author: huan
 * @Date: 2020-04-28
 * @Description:
 */
public class SparkAddFieldsDefaultHandler implements SparkAddFieldsHandler {
    // schema
    private StructType schema;

    @Override
    public JavaRDD<Row> createRDD(SparkWorkStageResult prevResult, AddFieldsInfo addFieldsInfo) {
        this.schema = prevResult.getDf().schema();
        List<AddFieldsInfo.AddField> fields = addFieldsInfo.getAddFields();
        for (AddFieldsInfo.AddField field: fields) {
            this.schema = this.schema.add(field.getName(),
                    SparkUtil.dataTypeMapping(field.getType(), field.getPrecision()), true);
        }

        // 生成RDD
        return prevResult.getRdd().mapPartitions((Iterator<Row> rowIterator) -> {
            List<Row> rows = new ArrayList<>();
            Row row = null;
            AddFieldsInfo.AddField field = null;
            // 生成新row
            StrictList<Row> result = new StrictList<>();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();

                // 获取当前字段
                StrictList<Object> list = new StrictList<>();
                for (int i = 0; i < row.length(); i ++) {
                    list.add(row.get(i));
                }

                Object[] fieldObjs = new Object[list.size() + fields.size()];
                fieldObjs = list.deepCopy().toArray(fieldObjs);
                for (int i = 0; i < fields.size(); i ++) {
                    field = fields.get(i);
                    fieldObjs[list.size() + i] = SparkUtil.rddValueMapping(field.getType(), field.getValue(), field.getFormat());
                }

                result.add(RowFactory.create(fieldObjs));
            }

            return result;
        });
    }

    @Override
    public DataFrame createDataFrame(SQLContext sqlContext, JavaRDD rdd) {
        return sqlContext.createDataFrame(rdd, this.schema);
    }
}
