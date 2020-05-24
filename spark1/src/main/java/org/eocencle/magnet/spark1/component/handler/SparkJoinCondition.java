package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.sql.DataFrame;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.FilterInfo;

import java.util.List;

/**
 * Spark关联条件接口
 * @author: huan
 * @Date: 2020-04-05
 * @Description:
 */
public interface SparkJoinCondition extends WorkStageHandler {
    /**
     * 关联条件
     * @Author huan
     * @Date 2020-04-05
     * @Param [filterFields, left, right, type]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    DataFrame on(List<FilterInfo.FilterField> filterFields, DataFrame left, DataFrame right, String type);
}
