package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.DistinctInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 去重作业节点抽象类
 * @author: huan
 * @Date: 2020-03-15
 * @Description:
 */
public abstract class DistinctWorkStage extends WorkStageComponent {
    // 去重信息类
    protected DistinctInfo distinctInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.distinctInfo = (DistinctInfo) info;
    }
}
