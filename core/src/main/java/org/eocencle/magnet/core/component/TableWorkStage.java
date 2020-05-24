package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 表作业节点抽象类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public abstract class TableWorkStage extends WorkStageComponent {
    // 表信息
    protected TableInfo tableInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.tableInfo = (TableInfo) info;
    }
}
