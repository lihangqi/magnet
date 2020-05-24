package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.sql.DataFrame;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.ValueMappersInfo;

/**
 * Spark值映射器
 * @author: huan
 * @Date: 2020-04-08
 * @Description:
 */
public interface SparkValueMappersHandler extends WorkStageHandler {
    /**
     * 值映射处理
     * @Author huan
     * @Date 2020-04-08
     * @Param [df, valueMappersInfo]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    DataFrame handle(DataFrame df, ValueMappersInfo valueMappersInfo);

}
