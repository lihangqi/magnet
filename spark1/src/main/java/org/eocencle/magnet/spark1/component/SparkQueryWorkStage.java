package org.eocencle.magnet.spark1.component;

import org.eocencle.magnet.core.component.QueryWorkStage;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkQueryHandler;

import java.util.List;

/**
 * Spark查询作业节点类
 * @author: huan
 * @Date: 2020-07-05
 * @Description:
 */
public class SparkQueryWorkStage extends QueryWorkStage {
    // 查询处理器
    private SparkQueryHandler queryHandler;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.queryHandler = (SparkQueryHandler) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        return null;
    }
}
