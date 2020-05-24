package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.eocencle.magnet.core.mapping.FilterInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.spark1.component.handler.SparkJoinCondition;

import java.util.List;

/**
 * Spark默认on条件接口
 * @author: huan
 * @Date: 2020-04-05
 * @Description:
 */
public class SparkDefaultOnCondition implements SparkJoinCondition {
    @Override
    public DataFrame on(List<FilterInfo.FilterField> filterFields, DataFrame left, DataFrame right, String type) {
        Column first = left.col(filterFields.get(0).getField()).equalTo(right.col(filterFields.get(0).getValue()));
        Column column = first, tmp = null;
        FilterInfo.FilterField field = null;
        for (int i = 1; i < filterFields.size(); i ++) {
            field = filterFields.get(i);
            tmp = left.col(field.getField()).equalTo(right.col(field.getValue()));
            if (CoreTag.FILTER_JOIN_AND.equals(field.getType())) {
                column.and(tmp);
            } else if (CoreTag.FILTER_JOIN_OR.equals(field.getType())) {
                column.or(tmp);
            }
            column = tmp;
        }

        return left.join(right, first, type);
    }
}
