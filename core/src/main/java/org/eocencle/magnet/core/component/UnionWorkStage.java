package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.UnionInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 合并作业节点抽象类
 * @author: huan
 * @Date: 2020-04-03
 * @Description:
 */
public abstract class UnionWorkStage extends WorkStageComponent {
    // 合并信息类
    protected UnionInfo unionInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.unionInfo = (UnionInfo) info;
    }
}
