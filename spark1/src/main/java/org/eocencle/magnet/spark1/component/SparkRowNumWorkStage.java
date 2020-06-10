package org.eocencle.magnet.spark1.component;

import org.apache.log4j.Logger;
import org.eocencle.magnet.core.component.RowNumWorkStage;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;

import java.util.List;

/**
 * Spark的行号作业节点类
 * @author: huan
 * @Date: 2020-06-07
 * @Description:
 */
public class SparkRowNumWorkStage extends RowNumWorkStage {
    private static final Logger logger = Logger.getLogger(SparkRowNumWorkStage.class);

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        return null;
    }

    @Override
    public void initHandler(WorkStageHandler handler) {

    }
}
