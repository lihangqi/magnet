package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.StreamInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 流数据加载作业节点抽象类
 * @author: huan
 * @Date: 2020-06-27
 * @Description:
 */
public abstract class StreamDataLoadWorkStage extends WorkStageComponent {
    // 流信息
    protected StreamInfo streamInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.streamInfo = (StreamInfo) info;
    }
}
