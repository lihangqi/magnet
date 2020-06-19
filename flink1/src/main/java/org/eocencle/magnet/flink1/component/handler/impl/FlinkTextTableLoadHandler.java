package org.eocencle.magnet.flink1.component.handler.impl;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.flink1.component.handler.FlinkTableLoadHandler;

/**
 * Flink文本表加载类
 * @author: huan
 * @Date: 2020-06-10
 * @Description:
 */
public class FlinkTextTableLoadHandler implements FlinkTableLoadHandler {
    @Override
    public DataSet<StrictMap> createDataSet(ExecutionEnvironment env, TableInfo tableInfo) {
        return null;
    }
}
