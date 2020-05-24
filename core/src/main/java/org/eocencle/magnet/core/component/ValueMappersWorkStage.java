package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.ValueMappersInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 值映射作业节点抽象类
 * @author: huan
 * @Date: 2020-04-08
 * @Description:
 */
public abstract class ValueMappersWorkStage extends WorkStageComponent {
    // 值映射信息类
    protected ValueMappersInfo valueMappersInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.valueMappersInfo = (ValueMappersInfo) info;
    }
}
