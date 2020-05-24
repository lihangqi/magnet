package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.log4j.Logger;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.mapping.OutputInfo;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkOutputer;

/**
 * Spark分支输出器类
 * @author: huan
 * @Date: 2020-01-31
 * @Description:
 */
public class SparkBranchOutputer implements SparkOutputer {
    private static final Logger logger = Logger.getLogger(SparkBranchOutputer.class);

    @Override
    public WorkStageResult output(OutputInfo outputInfo, SparkWorkStageResult result) {
        SparkWorkStageResult cloneResult = new SparkWorkStageResult();
        cloneResult.setId(outputInfo.getId());
        cloneResult.setRdd(result.getRdd());
        cloneResult.setDf(result.getDf());
        cloneResult.setTableName(result.getTableName());
        cloneResult.setRegTableName(result.getRegTableName());
        logger.info("Clone result '" + outputInfo.getRef() + "' and set id to '" + outputInfo.getId() + "'");
        return cloneResult;
    }
}
