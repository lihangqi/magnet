package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.spark1.component.handler.SparkTableDataFrameLoader;

import java.util.Properties;

/**
 * Spark数据库表作业节点类
 * @author: huan
 * @Date: 2020-03-26
 * @Description:
 */
public class SparkDataBaseTableLoader extends SparkTableDataFrameLoader {

    public SparkDataBaseTableLoader(TableInfo tableInfo) {
        super(tableInfo);
    }

    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-03-26
     * @Param [df]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    @Override
    public JavaRDD<Row> createRDD(DataFrame df) {
        return df.toJavaRDD();
    }

    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-03-26
     * @Param [context, src]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    @Override
    public DataFrame createDataFrame(Context context, String src) {
        Properties properties = new Properties();
        properties.put("user", this.tableInfo.getConfigParam(CoreTag.DB_USERNAME));
        properties.put("password", this.tableInfo.getConfigParam(CoreTag.DB_PASSWORD));
        return ((SQLContext) context.getSQLContext()).read().jdbc(this.getDBUrl(), src, properties);
    }

    /**
     * 获取数据库URL
     * @Author huan
     * @Date 2020-03-26
     * @Param []
     * @Return java.lang.String
     * @Exception
     * @Description
     **/
    private String getDBUrl() {
        String dialect = this.tableInfo.getConfigParam(CoreTag.DB_DIALECT);
        String host = this.tableInfo.getConfigParam(CoreTag.DB_HOST);
        String port = this.tableInfo.getConfigParam(CoreTag.DB_PORT);
        String dbName = this.tableInfo.getConfigParam(CoreTag.DB_DATABASE);
        if (CoreTag.DB_TYPE_MYSQL.equalsIgnoreCase(dialect)) {
            if (StringUtils.isBlank(port)) {
                port = CoreTag.DB_PORT_MYSQL;
            }
            return "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        } else if (CoreTag.DB_TYPE_SQLSERVER.equalsIgnoreCase(dialect)) {
            if (StringUtils.isBlank(port)) {
                port = CoreTag.DB_PORT_SQLSERVER;
            }
            return "jdbc:sqlserver://" + host + ":" + port + ";DatabaseName=" + dbName;
        } else if (CoreTag.DB_TYPE_ORACLE.equalsIgnoreCase(dialect)) {
            if (StringUtils.isBlank(port)) {
                port = CoreTag.DB_PORT_ORACLE;
            }
            return "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
        }
        throw new UnsupportedException(dialect + " database dialect is not supported!");
    }
}
