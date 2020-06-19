package org.eocencle.magnet.flink1.component.handler;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.core.util.StrictMap;

/**
 * Flink表加载器抽象类
 * @author: huan
 * @Date: 2020-06-10
 * @Description:
 */
public interface FlinkTableLoadHandler extends WorkStageHandler {
    /**
     * 创建表DataSet
     * @Author huan
     * @Date 2020-06-10
     * @Param [env, tableInfo]
     * @Return org.apache.flink.api.java.DataSet<org.eocencle.magnet.core.util.StrictMap>
     * @Exception
     * @Description
     */
    DataSet<StrictMap> createDataSet(ExecutionEnvironment env, TableInfo tableInfo);
}
