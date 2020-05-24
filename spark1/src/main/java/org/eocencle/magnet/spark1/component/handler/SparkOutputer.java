package org.eocencle.magnet.spark1.component.handler;

import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.mapping.OutputInfo;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;

/**
 * Spark输出器接口
 * @author: huan
 * @Date: 2020-02-01
 * @Description:
 */
public interface SparkOutputer extends WorkStageHandler {
    /**
     * 输出
     * @Author huan
     * @Date 2020-02-01
     * @Param [outputInfo, result]
     * @Return org.eocencle.magnet.client.component.WorkStageResult
     * @Exception
     * @Description
     **/
    WorkStageResult output(OutputInfo outputInfo, SparkWorkStageResult result);
}
