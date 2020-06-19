package org.eocencle.magnet.jsonbuilder.util;

/**
 * Xml建构标记类
 * @author: huan
 * @Date: 2020-05-31
 * @Description:
 */
public class JSONBuilderTag {
    public static final String JSON_ATTR_PARAMETER          = "parameter";
    public static final String JSON_ATTR_DATASOURCE	        = "datasource";
    public static final String JSON_ATTR_WORKFLOW		    = "workflow";
    public static final String JSON_ATTR_KEY		        = "key";
    public static final String JSON_ATTR_VALUE		        = "value";
    public static final String JSON_ATTR_LIST		        = "list";
    public static final String JSON_ATTR_MAP		        = "map";
    public static final String JSON_ATTR_TYPE		        = "type";
    public static final String JSON_ATTR_ID		            = "id";
    public static final String JSON_ATTR_ALIAS		        = "alias";
    public static final String JSON_ATTR_STYLE		        = "style";
    public static final String JSON_ATTR_SRC		        = "src";
    public static final String JSON_ATTR_REF		        = "ref";
    public static final String JSON_ATTR_FORMAT		        = "format";
    public static final String JSON_ATTR_SEPARATOR		    = "separator";
    public static final String JSON_ATTR_TOPICS		        = "topics";
    public static final String JSON_ATTR_CONFIG		        = "config";
    public static final String JSON_ATTR_FIELDS		        = "fields";
    public static final String JSON_ATTR_NAME		        = "name";
    public static final String JSON_ATTR_PRECISION		    = "precision";
    public static final String JSON_ATTR_SQL		        = "sql";
    public static final String JSON_ATTR_TARGET		        = "target";
    public static final String JSON_ATTR_COMPRESS	        = "compress";
    public static final String JSON_ATTR_FIELD		        = "field";
    public static final String JSON_ATTR_ORDER		        = "order";
    public static final String JSON_ATTR_ROWNUM		        = "rownum";
    public static final String JSON_ATTR_STREAM_STATE       = "streamState";
    public static final String JSON_ATTR_CONDITIONS         = "conditions";
    public static final String JSON_ATTR_JOIN               = "join";
    public static final String JSON_ATTR_START		        = "start";
    public static final String JSON_ATTR_END		        = "end";
    public static final String JSON_ATTR_IGNORECASE         = "ignoreCase";
    public static final String JSON_ATTR_REFS		        = "refs";
    public static final String JSON_ATTR_VALUEMAPPERS       = "valueMappers";
    public static final String JSON_ATTR_TAGFIELD		    = "tagField";
    public static final String JSON_ATTR_NONMATCH		    = "nonMatch";
    public static final String JSON_ATTR_MAPPERS            = "mappers";
    public static final String JSON_ATTR_SOURCE             = "source";
    public static final String JSON_ATTR_ISREGEX            = "isRegex";
    public static final String JSON_ATTR_ROWNUMFIELD        = "rowNumField";
    public static final String JSON_ATTR_STRINGCUTS         = "stringCuts";
    public static final String JSON_ATTR_ADDFIELDS          = "addFields";
    public static final String JSON_ATTR_INIT               = "init";
    public static final String JSON_ATTR_STEP               = "step";

    public static final String DATASOURCE_TYPE_TABLE		= "table";
    public static final String DATASOURCE_TYPE_STREAM		= "stream";

    public static final String TABLE_STYLE_DEFAULT  = "default";
    public static final String TABLE_STYLE_FILE     = "file";
    public static final String TABLE_STYLE_HTTP     = "http";
    public static final String TABLE_STYLE_DATABASE = "database";
    public static final String TABLE_STYLE_KAFKA    = "kafka";

    public static final String WORKFLOW_TYPE_SQL		        = "sql";
    public static final String WORKFLOW_TYPE_BRANCH		        = "branch";
    public static final String WORKFLOW_TYPE_OUTPUT		        = "output";
    public static final String WORKFLOW_TYPE_GROUP		        = "group";
    public static final String WORKFLOW_TYPE_FILTER		        = "filter";
    public static final String WORKFLOW_TYPE_DISTINCT	        = "distinct";
    public static final String WORKFLOW_TYPE_ORDER		        = "order";
    public static final String WORKFLOW_TYPE_UNION		        = "union";
    public static final String WORKFLOW_TYPE_JOIN		        = "join";
    public static final String WORKFLOW_TYPE_SCHEMA		        = "schema";
    public static final String WORKFLOW_TYPE_VALUEMAPPERS	    = "valueMappers";
    public static final String WORKFLOW_TYPE_SPLITFIELDTOROWS	= "splitFieldToRows";
    public static final String WORKFLOW_TYPE_STRINGCUTS		    = "stringCuts";
    public static final String WORKFLOW_TYPE_ADDFIELDS		    = "addFields";
    public static final String WORKFLOW_TYPE_ADDSEQUENCE	    = "addSequence";
    public static final String WORKFLOW_TYPE_ROWNUM	            = "rowNum";

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

    // 压缩格式
    public static final String COMPRESS_NONE    = "None";
    public static final String COMPRESS_SNAPPY  = "Snappy";
    public static final String COMPRESS_DEFAULT = "Default";
    public static final String COMPRESS_GZIP    = "Gzip";
    public static final String COMPRESS_BZIP2   = "BZip2";
    public static final String COMPRESS_LZ4     = "Lz4";

    // 分隔符
    public static final String SPLIT_COMMA          = ",";
    public static final String SPLIT_BLANK          = " ";
    public static final String SPLIT_INVISIBLE1     = "\001";

    public static final String FILTER_JOIN_AND      = "AND";
    public static final String FILTER_JOIN_OR       = "OR";

    // 布尔值
    public static final String TRUE     = "true";
    public static final String FALSE    = "false";

    public static final String ROWNUM_DEFAULT_FIELD     = "rownum";
}
