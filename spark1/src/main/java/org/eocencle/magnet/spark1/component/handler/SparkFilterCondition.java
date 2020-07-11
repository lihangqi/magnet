package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.sql.DataFrame;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.FilterField;

import java.util.List;

/**
 * Spark过滤条件接口
 * @author: huan
 * @Date: 2020-03-12
 * @Description:
 */
public interface SparkFilterCondition extends WorkStageHandler {
    /**
     * 过滤验证
     * @Author huan
     * @Date 2020-03-12
     * @Param [filterFields, df]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    DataFrame filter(List<FilterField> filterFields, DataFrame df);
}
