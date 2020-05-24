package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.AddFieldsInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 添加字段作业节点抽象类
 * @author: huan
 * @Date: 2020-04-28
 * @Description:
 */
public abstract class AddFieldsWorkStage extends WorkStageComponent {
    // 添加字段
    protected AddFieldsInfo addFieldsInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.addFieldsInfo = (AddFieldsInfo) info;
    }
}
