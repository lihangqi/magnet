package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.sql.DataFrameWriter;
import org.apache.spark.sql.SaveMode;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.mapping.OutputInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkOutputer;

import java.util.Properties;

/**
 * Spark数据库输出器类
 * @author: huan
 * @Date: 2020-03-28
 * @Description:
 */
public class SparkDataBaseOutputer implements SparkOutputer {
    @Override
    public WorkStageResult output(OutputInfo outputInfo, SparkWorkStageResult result) {
        // 构造用户信息
        Properties properties = new Properties();
        properties.put("user", outputInfo.getOutputConfig(CoreTag.DB_USERNAME));
        properties.put("password", outputInfo.getOutputConfig(CoreTag.DB_PASSWORD));

        DataFrameWriter writer = result.getDf().write();

        if (CoreTag.OUTPUT_TYPE_CREATE.equalsIgnoreCase(outputInfo.getType())) {
            writer = writer.mode(SaveMode.ErrorIfExists);
        } else if (CoreTag.OUTPUT_TYPE_OVERRIDE.equalsIgnoreCase(outputInfo.getType())) {
            writer = writer.mode(SaveMode.Overwrite);
        } else if (CoreTag.OUTPUT_TYPE_APPEND.equalsIgnoreCase(outputInfo.getType())) {
            writer = writer.mode(SaveMode.Append);
        }

        writer.jdbc(this.getDBUrl(outputInfo), outputInfo.getTarget(), properties);
        return null;
    }

    /**
     * 获取数据库URL
     * @Author huan
     * @Date 2020-03-28
     * @Param [outputInfo]
     * @Return java.lang.String
     * @Exception
     * @Description
     **/
    private String getDBUrl(OutputInfo outputInfo) {
        String type = outputInfo.getOutputConfig(CoreTag.DB_TYPE);
        String host = outputInfo.getOutputConfig(CoreTag.DB_HOST);
        String port = outputInfo.getOutputConfig(CoreTag.DB_PORT);
        String dbName = outputInfo.getOutputConfig(CoreTag.DB_DATABASE);
        if (CoreTag.DB_TYPE_MYSQL.equalsIgnoreCase(type)) {
            if (StringUtils.isBlank(port)) {
                port = CoreTag.DB_PORT_MYSQL;
            }
            return "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        } else if (CoreTag.DB_TYPE_SQLSERVER.equalsIgnoreCase(type)) {
            if (StringUtils.isBlank(port)) {
                port = CoreTag.DB_PORT_SQLSERVER;
            }
            return "jdbc:sqlserver://" + host + ":" + port + ";DatabaseName=" + dbName;
        } else if (CoreTag.DB_TYPE_ORACLE.equalsIgnoreCase(type)) {
            if (StringUtils.isBlank(port)) {
                port = CoreTag.DB_PORT_ORACLE;
            }
            return "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
        }
        throw new UnsupportedException(type + " database type is not supported!");
    }
}
