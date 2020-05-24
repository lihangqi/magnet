package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.OutputInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 输出作业节点抽象类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public abstract class OutputWorkStage extends WorkStageComponent {
    // 输出信息
    protected OutputInfo outputInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.outputInfo = (OutputInfo) info;
    }
}
