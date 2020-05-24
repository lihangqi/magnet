package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.JoinInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 关联作业节点抽象类
 * @author: huan
 * @Date: 2020-04-04
 * @Description:
 */
public abstract class JoinWorkStage extends WorkStageComponent {
    // 关联信息类
    protected JoinInfo joinInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.joinInfo = (JoinInfo) info;
    }
}
