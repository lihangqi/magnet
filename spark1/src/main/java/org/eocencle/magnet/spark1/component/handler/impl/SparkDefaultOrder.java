package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.mapping.OrderInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.spark1.component.handler.SparkOrder;

/**
 * Spark默认排序类
 * @author: huan
 * @Date: 2020-03-16
 * @Description:
 */
public class SparkDefaultOrder implements SparkOrder {
    @Override
    public JavaRDD<Row> createRDD(DataFrame df) {
        return df.toJavaRDD();
    }

    @Override
    public DataFrame createDataFrame(OrderInfo orderInfo, DataFrame df) {
        DataFrame result = null;
        String[] fields = orderInfo.getField().split(CoreTag.SPLIT_COMMA);
        if (null != fields && 0 != fields.length) {
            Column[] columns = new Column[fields.length];
            for (int i = 0; i < fields.length; i ++) {
                columns[i] = this.setOrder(fields[i], df);
            }
            result = df.orderBy(columns);
        } else {
            result = df;
        }

        return result;
    }

    /**
     * 设置排序
     * @Author huan
     * @Date 2020-03-16
     * @Param [field, df]
     * @Return org.apache.spark.sql.Column
     * @Exception
     * @Description
     **/
    private Column setOrder(String field, DataFrame df) {
        String[] complete = field.trim().split(CoreTag.STRING_SPACE);
        Column column = df.col(complete[0]);
        if (1 == complete.length) {
            column = column.asc();
        } else {
            if (CoreTag.ORDERBY_ASC.equals(complete[1].toLowerCase())) {
                column = column.asc();
            } else if (CoreTag.ORDERBY_DESC.equals(complete[1].toLowerCase())) {
                column = column.desc();
            }
        }
        return column;
    }
}
