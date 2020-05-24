package org.eocencle.magnet.core.component.wrapper;

import org.eocencle.magnet.core.component.WorkStageComponent;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.context.Context;

import java.util.List;

/**
 * 执行容器包装类
 * @author: huan
 * @Date: 2020-02-09
 * @Description:
 */
public class RunningContainerWrapper extends WorkStageComponentWrapper {

    public RunningContainerWrapper(WorkStageComponent workStageComponent) {
        super(workStageComponent);
    }

    @Override
    public void before(WorkStageParameter parameter) {
        Context context = parameter.getContext();
        // 启动环境
        context.start();
    }

    @Override
    public void after(WorkStageParameter parameter, List<WorkStageResult> results) {
        Context context = parameter.getContext();
        // 停止环境
        context.stop();
    }
}
