package org.eocencle.magnet.spark1.component;

import org.apache.log4j.Logger;
import org.eocencle.magnet.core.component.SchemaWorkStage;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;

import java.util.List;

/**
 * Spark的Schema作业节点类
 * @author: huan
 * @Date: 2020-04-06
 * @Description:
 */
public class SparkSchemaWorkStage extends SchemaWorkStage {
    private static final Logger logger = Logger.getLogger(SparkSchemaWorkStage.class);

    @Override
    public void initHandler(WorkStageHandler handler) {

    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        // 获取引用结果
        SparkWorkStageResult ref =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.schemaInfo.getRef());

        // 输出schema
        logger.info(ref.getId() + "'s schema:");
        ref.getDf().printSchema();

        return null;
    }
}
