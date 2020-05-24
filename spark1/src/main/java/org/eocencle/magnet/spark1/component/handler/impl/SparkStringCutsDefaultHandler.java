package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.eocencle.magnet.core.mapping.StringCutsInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictList;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkStringCutsHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Spark字符串分割默认处理器类
 * @author: huan
 * @Date: 2020-04-24
 * @Description:
 */
public class SparkStringCutsDefaultHandler implements SparkStringCutsHandler {
    // schema
    private StructType schema;

    @Override
    public JavaRDD<Row> createRDD(SparkWorkStageResult prevResult, StringCutsInfo stringCutsInfo) {
        this.schema = prevResult.getDf().schema();
        List<StringCutsInfo.StringCut> cutList = stringCutsInfo.getStringCuts();
        int[] idx = new int[cutList.size()];
        for (int i = 0; i < cutList.size(); i ++) {
            this.schema = this.schema.add(cutList.get(i).getTagField(), DataTypes.StringType, true);
            idx[i] = this.schema.fieldIndex(cutList.get(i).getField());
        }

        // 生成RDD
        return prevResult.getRdd().mapPartitions((Iterator<Row> rowIterator) -> {
            List<Row> rows = new ArrayList<>();
            Row row = null;
            StringCutsInfo.StringCut cut = null;
            String start = null, end = null;
            // 生成新row
            StrictList<Row> result = new StrictList<>();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();

                // 获取当前字段
                StrictList<Object> list = new StrictList<>();
                for (int i = 0; i < row.length(); i ++) {
                    list.add(row.get(i));
                }

                Object[] fieldObjs = new Object[list.size() + idx.length];
                fieldObjs = list.deepCopy().toArray(fieldObjs);
                for (int i = 0; i < idx.length; i ++) {
                    cut = cutList.get(i);
                    start = cut.getStart();
                    end = cut.getEnd();
                    if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
                        fieldObjs[list.size() + i] =
                                row.get(idx[i]).toString().substring(Integer.parseInt(start), Integer.parseInt(end));
                    } else if (StringUtils.isBlank(cut.getStart()) && StringUtils.isNotBlank(cut.getEnd())) {
                        fieldObjs[list.size() + i] = row.get(idx[i]).toString().substring(0, Integer.parseInt(end));
                    } else if (StringUtils.isNotBlank(cut.getStart()) && StringUtils.isBlank(cut.getEnd())) {
                        fieldObjs[list.size() + i] = CoreTag.STRING_BLANK;
                    } else {
                        fieldObjs[list.size() + i] = row.get(idx[i]).toString();
                    }
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