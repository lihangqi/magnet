package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.QueryInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 查询作业节点抽象类
 * @author: huan
 * @Date: 2020-07-05
 * @Description:
 */
public abstract class QueryWorkStage extends WorkStageComponent {
    // 查询信息
    protected QueryInfo queryInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.queryInfo = (QueryInfo) info;
    }
}
