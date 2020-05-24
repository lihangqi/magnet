package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.component.WorkStageResult;

/**
 * Spark作业结果类
 * @author: huan
 * @Date: 2020-01-21
 * @Description:
 */
public class SparkWorkStageResult extends WorkStageResult {
    // spakr rdd
    private JavaRDD<Row> rdd;
    // spark df
    private DataFrame df;
    // 表名
    private String tableName;
    // 注册后表名
    private String regTableName;

    public JavaRDD<Row> getRdd() {
        return rdd;
    }

    public void setRdd(JavaRDD<Row> rdd) {
        this.rdd = rdd;
    }

    public DataFrame getDf() {
        return df;
    }

    public void setDf(DataFrame df) {
        this.df = df;
    }

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
}
