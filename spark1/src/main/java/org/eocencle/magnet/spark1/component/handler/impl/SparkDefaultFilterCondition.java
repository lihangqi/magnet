package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.eocencle.magnet.core.mapping.FilterField;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.spark1.component.handler.SparkFilterCondition;

import java.util.Arrays;
import java.util.List;

/**
 * Spark默认过滤条件接口
 * @author: huan
 * @Date: 2020-03-12
 * @Description:
 */
public class SparkDefaultFilterCondition implements SparkFilterCondition {
    @Override
    public DataFrame filter(List<FilterField> filterFields, DataFrame df) {
        DataFrame result = null;
        if (null != filterFields && 0 != filterFields.size()) {
            Column first = this.setCondition(filterFields.get(0), df);
            Column column = first, tmp = null;
            FilterField field = null;
            for (int i = 1; i < filterFields.size(); i ++) {
                field = filterFields.get(i);
                tmp = this.setCondition(field, df);
                if (CoreTag.FILTER_JOIN_AND.equals(field.getType())) {
                    column.and(tmp);
                } else if (CoreTag.FILTER_JOIN_OR.equals(field.getType())) {
                    column.or(tmp);
                }
                column = tmp;
            }
            result = df.filter(first);
        } else {
            result = df;
        }

        return result;
    }

    /**
     * 设置过滤条件
     * @Author huan
     * @Date 2020-03-14
     * @Param [filterField, df]
     * @Return org.apache.spark.sql.Column
     * @Exception
     * @Description
     **/
    private Column setCondition(FilterField filterField, DataFrame df) {
        Column column = df.col(filterField.getField());
        if (CoreTag.FILTER_CONDITION_ISNULL.equals(filterField.getType())) {
            column = column.isNull();
        } else if (CoreTag.FILTER_CONDITION_ISNOTNULL.equals(filterField.getType())) {
            column = column.isNotNull();
        } else if (CoreTag.FILTER_CONDITION_EQUALTO.equals(filterField.getType())) {
            column = column.equalTo(filterField.getValue());
        } else if (CoreTag.FILTER_CONDITION_NOTEQUALTO.equals(filterField.getType())) {
            column = column.notEqual(filterField.getValue());
        } else if (CoreTag.FILTER_CONDITION_GREATERTHAN.equals(filterField.getType())) {
            column = column.gt(filterField.getValue());
        } else if (CoreTag.FILTER_CONDITION_GREATERTHANOREQUALTO.equals(filterField.getType())) {
            column = column.geq(filterField.getValue());
        } else if (CoreTag.FILTER_CONDITION_LESSTHAN.equals(filterField.getType())) {
            column = column.lt(filterField.getValue());
        } else if (CoreTag.FILTER_CONDITION_LESSTHANOREQUALTO.equals(filterField.getType())) {
            column = column.leq(filterField.getValue());
        } else if (CoreTag.FILTER_CONDITION_IN.equals(filterField.getType())) {
            column = column.in(Arrays.asList(filterField.getValue().split(CoreTag.SPLIT_COMMA)));
        } else if (CoreTag.FILTER_CONDITION_BETWEEN.equals(filterField.getType())) {
            column = column.between(filterField.getStart(), filterField.getEnd());
        } else if (CoreTag.FILTER_CONDITION_PREFIX.equals(filterField.getType())) {
            column = column.startsWith(filterField.getValue());
        } else if (CoreTag.FILTER_CONDITION_SUFFIX.equals(filterField.getType())) {
            column = column.endsWith(filterField.getValue());
        } else if (CoreTag.FILTER_CONDITION_CONTAIN.equals(filterField.getType())) {
            column = column.contains(filterField.getValue());
        }
        return column;
    }
}
