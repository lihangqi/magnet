package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.eocencle.magnet.core.mapping.SplitFieldToRowsInfo;
import org.eocencle.magnet.core.util.StrictList;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkSplitFieldToRowsHandler;

/**
 * Spark列分隔转行处理器默认实现类
 * @author: huan
 * @Date: 2020-04-15
 * @Description:
 */
public class SparkDefaultSplitFieldToRowsHandler implements SparkSplitFieldToRowsHandler {
    // schema
    private StructType schema;

    @Override
    public JavaRDD<Row> createRDD(SparkWorkStageResult prevResult, SplitFieldToRowsInfo splitFieldToRowsInfo) {
        this.schema = prevResult.getDf().schema();
        int idx = this.schema.fieldIndex(splitFieldToRowsInfo.getField());
        this.schema = this.schema.add(splitFieldToRowsInfo.getTagField(), DataTypes.StringType, true);
        if (StringUtils.isNotBlank(splitFieldToRowsInfo.getRowNumField())) {
            this.schema = this.schema.add(splitFieldToRowsInfo.getRowNumField(), DataTypes.LongType, true);
        }

        // 生成RDD
        return prevResult.getRdd().flatMap((Row row) -> {
            // 分割字段
            String[] spls = row.get(idx).toString().split(splitFieldToRowsInfo.getSeparator(), -1);

            // 获取当前字段
            StrictList<Object> list = new StrictList<>();
            for (int i = 0; i < row.length(); i ++) {
                list.add(row.get(i));
            }

            // 生成新row
            StrictList<Row> result = new StrictList<>();
            long rowNum = 1;
            Object[] fieldObjs = null;
            for (String spl: spls) {
                if (StringUtils.isNotBlank(splitFieldToRowsInfo.getRowNumField())) {
                    fieldObjs = new Object[list.size() + 2];
                } else {
                    fieldObjs = new Object[list.size() + 1];
                }

                // 深度复制row
                fieldObjs = list.deepCopy().toArray(fieldObjs);
                // 目标字段
                fieldObjs[fieldObjs.length - 2] = String.valueOf(spl);
                // 行号
                if (StringUtils.isNotBlank(splitFieldToRowsInfo.getRowNumField())) {
                    fieldObjs[fieldObjs.length - 1] = Long.valueOf(rowNum);
                    rowNum ++;
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
