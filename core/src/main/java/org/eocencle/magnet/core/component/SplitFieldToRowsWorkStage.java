package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.SplitFieldToRowsInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 列分隔转行作业节点抽象类
 * @author: huan
 * @Date: 2020-04-13
 * @Description:
 */
public abstract class SplitFieldToRowsWorkStage extends WorkStageComponent {
    // 列分隔转行信息类
    protected SplitFieldToRowsInfo splitFieldToRowsInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.splitFieldToRowsInfo = (SplitFieldToRowsInfo) info;
    }
}
