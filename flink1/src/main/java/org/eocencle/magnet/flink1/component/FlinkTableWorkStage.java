package org.eocencle.magnet.flink1.component;

import org.eocencle.magnet.core.component.TableWorkStage;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.flink1.component.handler.FlinkTableLoadHandler;

import java.util.List;

/**
 * 表作业节点类
 * @author: huan
 * @Date: 2020-06-10
 * @Description:
 */
public class FlinkTableWorkStage extends TableWorkStage {
    // 表加载器
    private FlinkTableLoadHandler tableLoadHandler;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.tableLoadHandler = (FlinkTableLoadHandler) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        return null;
    }
}
