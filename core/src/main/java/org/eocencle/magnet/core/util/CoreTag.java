package org.eocencle.magnet.core.util;

/**
 * Core标记类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class CoreTag {
    // 应用默认名称
    public static final String CONTEXT_APPNAME_DEFAULT = "Magnet";

    // 上下文环境
    public static final String CONTEXT_SPARK1   = "Spark1";
    public static final String CONTEXT_FLINK    = "Flink";

    // 上下文环境默认版本
    public static final String CONTEXT_VERSION_DEFALUT = "1.6.0";

    // 文件格式
    public static final String FILE_FORMAT_TEXTFILE     = "TextFile";
    public static final String FILE_FORMAT_MAPFILE      = "MapFile";
    public static final String FILE_FORMAT_RCFILE       = "RCFile";
    public static final String FILE_FORMAT_ORCFILE      = "ORCFile";
    public static final String FILE_FORMAT_PARQUETFILE  = "ParquetFile";
    public static final String FILE_FORMAT_AVROFILE     = "AvroFile";
    public static final String FILE_FORMAT_JSONFILE     = "JsonFile";

    // 表字段类型
    public static final String TABLE_FIELD_TYPE_STRING              = "String";
    public static final String TABLE_FIELD_TYPE_BOOLEAN             = "Boolean";
    public static final String TABLE_FIELD_TYPE_BOOL                = "Bool";
    public static final String TABLE_FIELD_TYPE_DATETIME            = "DateTime";
    public static final String TABLE_FIELD_TYPE_DOUBLE              = "Double";
    public static final String TABLE_FIELD_TYPE_FLOAT               = "Float";
    public static final String TABLE_FIELD_TYPE_BYTE                = "Byte";
    public static final String TABLE_FIELD_TYPE_INTEGER             = "Integer";
    public static final String TABLE_FIELD_TYPE_INT                 = "Int";
    public static final String TABLE_FIELD_TYPE_LONG                = "Long";
    public static final String TABLE_FIELD_TYPE_SHORT               = "Short";
    public static final String TABLE_FIELD_TYPE_DECIMAL             = "Decimal";
    public static final String TABLE_FIELD_TYPE_BINARY              = "Binary";
    public static final String TABLE_FIELD_TYPE_TIMESTAMP           = "Timestamp";
    public static final String TABLE_FIELD_TYPE_CALENDARINTERVAL    = "CalendarInterval";

    // 输出方式
    public static final String OUTPUT_STYLE_FILE    = "file";
    public static final String OUTPUT_STYLE_BRANCH  = "branch";
    public static final String OUTPUT_STYLE_EMAIL   = "email";
    public static final String OUTPUT_STYLE_CONSOLE = "console";
    public static final String OUTPUT_STYLE_SAMPLE  = "sample";

    // 输出类型
    public static final String OUTPUT_TYPE_CREATE   = "create";
    public static final String OUTPUT_TYPE_OVERRIDE = "override";
    public static final String OUTPUT_TYPE_APPEND   = "append";

    // 邮件配置
    public static final String OUTPUT_EMAIL_HOST        = "host";
    public static final String OUTPUT_EMAIL_ACCOUT      = "accout";
    public static final String OUTPUT_EMAIL_PERSONAL    = "personal";
    public static final String OUTPUT_EMAIL_PWD         = "pwd";
    public static final String OUTPUT_EMAIL_USERS       = "users";
    public static final String OUTPUT_EMAIL_SUBJECT     = "subject";

    // 排序类型
    public static final String ORDERBY_ASC  = "asc";
    public static final String ORDERBY_DESC = "desc";

    // rownum默认字段名
    public static final String ROWNUM_DEFAULT = "rownum";

    // 数据源前缀
    public static final String SRC_PREFIX_HDFS 	= "hdfs://";
    public static final String SRC_PREFIX_FILE 	= "file:///";
    public static final String SRC_PREFIX_HTTP 	= "http://";
    public static final String SRC_PREFIX_JDBC 	= "jdbc:";

    // 字符
    public static final String STRING_BLANK     = "";
    public static final String STRING_SPACE     = " ";
    public static final String STRING_UNDERLINE = "_";
    public static final String STRING_COMMA     = ",";
    public static final String STRING_STAR      = "*";

    // 分割符
    public static final String SPLIT_COMMA          = ",";
    public static final String SPLIT_BLANK          = " ";
    public static final String SPLIT_INVISIBLE1     = "\001";

    // SQL
    public static final String SQL_WHERE    = "WHERE";
    public static final String SQL_SET      = "SET";

    // 压缩格式
    public static final String COMPRESS_NONE    = "None";
    public static final String COMPRESS_SNAPPY  = "Snappy";
    public static final String COMPRESS_DEFAULT = "Default";
    public static final String COMPRESS_GZIP    = "Gzip";
    public static final String COMPRESS_BZIP2   = "BZip2";
    public static final String COMPRESS_LZ4     = "Lz4";

    // 系统信息
    public static final String MAGNET_NAME                  = "_AppName";
    public static final String MAGNET_CONTEXT               = "_Context";
    public static final String MAGNET_VERSION               = "_Version";
    public static final String MAGNET_FILE_PATH             = "_FilePath";
    public static final String MAGNET_FILE_NAME             = "_FileName";
    public static final String MAGNET_ENV_MODE              = "_EnvMode";
    public static final String MAGNET_PROCESS_MODE          = "_ProcessMode";
    public static final String MAGNET_DURATION              = "_Duration";
    public static final String MAGNET_SQL_ENGINE            = "_SQLEngine";
    public static final String MAGNET_CONFIG_READ_MODE      = "_ConfigReadMode";
    public static final String MAGNET_TASK_EXCEPTION_PRINT  = "_TaskExceptionPrint";
    public static final String MAGNET_TASK_EXCEPTION_BREAK  = "_TaskExceptionBreak";
    public static final String MAGNET_STREAM_CHECK_POINT    = "_StreamCheckPoint";
    public static final String MAGNET_STREAM_STATE          = "_StreamState";

    // 环境模式
    public static final String ENV_MODE_LOCAL       = "Local";
    public static final String ENV_MODE_CLUSTER     = "Cluster";

    // 处理模式
    public static final String PROCESS_MODE_BATCH   = "Batch";
    public static final String PROCESS_MODE_STREAM  = "Stream";

    // 流式处理默认持续时间
    public static final String STREAM_DEFAULT_DURATION = "60";

    // SQL处理引擎
    public static final String SQL_ENGINE_SPARK = "SaprkSQL";
    public static final String SQL_ENGINE_HIVE  = "HiveSQL";

    // 过滤条件
    public static final String FILTER_JOIN_AND                          = "AND";
    public static final String FILTER_JOIN_OR                           = "OR";
    public static final String FILTER_CONDITION_ISNULL                  = "ISNULL";
    public static final String FILTER_CONDITION_ISNOTNULL               = "ISNOTNULL";
    public static final String FILTER_CONDITION_EQUALTO                 = "EQUALTO";
    public static final String FILTER_CONDITION_NOTEQUALTO              = "NOTEQUALTO";
    public static final String FILTER_CONDITION_GREATERTHAN             = "GREATERTHAN";
    public static final String FILTER_CONDITION_GREATERTHANOREQUALTO    = "GREATERTHANOREQUALTO";
    public static final String FILTER_CONDITION_LESSTHAN                = "LESSTHAN";
    public static final String FILTER_CONDITION_LESSTHANOREQUALTO       = "LESSTHANOREQUALTO";
    public static final String FILTER_CONDITION_IN                      = "IN";
    public static final String FILTER_CONDITION_BETWEEN                 = "BETWEEN";
    public static final String FILTER_CONDITION_PREFIX                  = "PREFIX";
    public static final String FILTER_CONDITION_SUFFIX                  = "SUFFIX";
    public static final String FILTER_CONDITION_CONTAIN                 = "CONTAIN";

    // 表数据源类型
    public static final String TABLE_STYLE_DEFAULT  = "default";
    public static final String TABLE_STYLE_FILE     = "file";
    public static final String TABLE_STYLE_HTTP     = "http";
    public static final String TABLE_STYLE_DATABASE = "database";
    public static final String TABLE_STYLE_KAFKA    = "kafka";

    // 数据库连接
    public static final String DB_TYPE      = "db.type";
    public static final String DB_HOST      = "db.host";
    public static final String DB_PORT      = "db.port";
    public static final String DB_DATABASE  = "db.database";
    public static final String DB_TABLE     = "db.table";
    public static final String DB_USERNAME  = "db.username";
    public static final String DB_PASSWORD  = "db.password";

    // 数据库类型
    public static final String DB_TYPE_MYSQL        = "mysql";
    public static final String DB_TYPE_SQLSERVER    = "sqlserver";
    public static final String DB_TYPE_ORACLE       = "oracle";

    // 数据库默认端口
    public static final String DB_PORT_MYSQL       = "3306";
    public static final String DB_PORT_SQLSERVER   = "1433";
    public static final String DB_PORT_ORACLE      = "1521";

    // 连接类型
    public static final String JOIN_TYPE_INNER          = "inner";
    public static final String JOIN_TYPE_OUTER          = "outer";
    public static final String JOIN_TYPE_LEFTOUTER      = "left_outer";
    public static final String JOIN_TYPE_RIGHTOUTER     = "right_outer";
    public static final String JOIN_TYPE_LEFTSEMI       = "leftsemi";

    // 采样参数
    public static final String SAMPLE_WITHREPLACEMENT   = "withReplacement";
    public static final String SAMPLE_FRACTION          = "fraction";
    public static final String SAMPLE_SEED              = "seed";

    // 布尔值
    public static final String TRUE     = "true";
    public static final String FALSE    = "false";

    // 配置读取模式
    public static final String CONFIG_READ_MODE_XML = "xml";

    // 任务异常输出项
    public static final String TASK_EXCEPTION_PRINT_STACK           = "stack";
    public static final String TASK_EXCEPTION_PRINT_OFFSET          = "offset";
    public static final String TASK_EXCEPTION_PRINT_PREV_RESULT     = "prevResult";
    public static final String TASK_EXCEPTION_PRINT_STREAM_SOURCE   = "streamSource";

    // 流配置
    public static final String STREAM_CONFIG_KAFKA  = "KafkaConfig";
    public static final String STREAM_CONFIG_DB     = "DBConfig";

    // 流状态
    public static final String STREAM_STATE_NONE    = "none";
    public static final String STREAM_STATE_STATE   = "state";
    public static final String STREAM_STATE_WINDOW  = "window";
}
