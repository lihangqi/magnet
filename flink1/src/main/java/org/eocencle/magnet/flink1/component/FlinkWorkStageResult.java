package org.eocencle.magnet.flink1.component;

import org.apache.flink.api.java.DataSet;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.util.StrictMap;

/**
 * Flink作业结果类
 * @author: huan
 * @Date: 2020-05-24
 * @Description:
 */
public class FlinkWorkStageResult extends WorkStageResult {
    // 表名
    private String tableName;
    // 注册后表名
    private String regTableName;
    // 数据集
    private DataSet<StrictMap> dataSet;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRegTableName() {
        return regTableName;
    }

    public void setRegTableName(String regTableName) {
        this.regTableName = regTableName;
    }

    public DataSet<StrictMap> getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet<StrictMap> dataSet) {
        this.dataSet = dataSet;
    }
}
