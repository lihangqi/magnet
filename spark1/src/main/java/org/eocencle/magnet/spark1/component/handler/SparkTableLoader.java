package org.eocencle.magnet.spark1.component.handler;

import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.TableInfo;

/**
 * Spark表加载器抽象类
 * @author: huan
 * @Date: 2020-02-01
 * @Description:
 */
public abstract class SparkTableLoader implements WorkStageHandler {
    // 表信息
    protected TableInfo tableInfo;

    public SparkTableLoader(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
}
