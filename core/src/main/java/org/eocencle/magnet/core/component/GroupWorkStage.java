package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.GroupInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 组作业节点抽象类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public abstract class GroupWorkStage extends WorkStageComponent {
    // 分组信息
    protected GroupInfo groupInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.groupInfo = (GroupInfo) info;
    }
}
