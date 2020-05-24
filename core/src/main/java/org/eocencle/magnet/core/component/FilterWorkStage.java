package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.FilterInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 过滤作业节点抽象类
 * @author: huan
 * @Date: 2020-03-12
 * @Description:
 */
public abstract class FilterWorkStage extends WorkStageComponent {
    // 过滤信息
    protected FilterInfo filterInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.filterInfo = (FilterInfo) info;
    }
}
