package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.SQLInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * SQL作业节点抽象类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public abstract class SQLWorkStage extends WorkStageComponent {
    // SQL信息
    protected SQLInfo sqlInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.sqlInfo = (SQLInfo) info;
    }
}
