package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.AddSequenceInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 添加序列作业节点抽象类
 * @author: huan
 * @Date: 2020-04-30
 * @Description:
 */
public abstract class AddSequenceWorkStage extends WorkStageComponent {
    // 添加序列
    protected AddSequenceInfo addSequenceInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.addSequenceInfo = (AddSequenceInfo) info;
    }
}
