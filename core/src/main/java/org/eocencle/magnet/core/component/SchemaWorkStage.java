package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.SchemaInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * Schema作业节点抽象类
 * @author: huan
 * @Date: 2020-04-06
 * @Description:
 */
public abstract class SchemaWorkStage extends WorkStageComponent {
    // Schema信息
    protected SchemaInfo schemaInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.schemaInfo = (SchemaInfo) info;
    }
}
