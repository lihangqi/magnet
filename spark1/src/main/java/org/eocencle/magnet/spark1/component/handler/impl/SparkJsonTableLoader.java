package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.spark1.component.handler.SparkTableDataFrameLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SparkJson格式表作业节点类
 * @author: huan
 * @Date: 2020-06-21
 * @Description:
 */
public class SparkJsonTableLoader extends SparkTableDataFrameLoader {

    public SparkJsonTableLoader(TableInfo tableInfo) {
        super(tableInfo);
    }

    @Override
    public JavaRDD<Row> createRDD(DataFrame df) {
        return df.toJavaRDD();
    }

    @Override
    public DataFrame createDataFrame(Context context, String src) {
        SQLContext sqlContext = ((SQLContext) context.getSQLContext());
        DataFrame df = sqlContext.read().json(src);
        StructType schema = df.schema();
        StrictMap<StructField> fieldStrictMap = new StrictMap<>("JSON fields");
        for (StructField field: schema.fields()) {
            fieldStrictMap.put(field.name(), field);
        }

        List<StructField> structFields = new ArrayList<StructField>();
        StrictMap<DataSourceField> targetFields = this.tableInfo.getFields();
        for (Map.Entry<String, DataSourceField> targetField: targetFields.entrySet()) {
            structFields.add(fieldStrictMap.get(targetField.getValue().getName()));
        }

        JavaRDD<Row> rdd = df.toJavaRDD().map((Row row) -> {
            Object[] fields = new Object[targetFields.size()];
            int i = 0;
            for (Map.Entry<String, DataSourceField> targetField : targetFields.entrySet()) {
                fields[i] = row.get(row.fieldIndex(targetField.getValue().getName()));
                i++;
            }
            return RowFactory.create(fields);
        });

        return sqlContext.createDataFrame(rdd, DataTypes.createStructType(structFields));
    }
}
