package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.RowNumInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 行号作业节点抽象类
 * @author: huan
 * @Date: 2020-06-07
 * @Description:
 */
public abstract class RowNumWorkStage extends WorkStageComponent {
    // 行号信息类
    protected RowNumInfo rowNumInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.rowNumInfo = (RowNumInfo) info;
    }
}
