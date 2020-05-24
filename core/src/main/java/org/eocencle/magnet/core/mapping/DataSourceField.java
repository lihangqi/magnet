package org.eocencle.magnet.core.mapping;

import org.eocencle.magnet.core.util.CoreTag;

/**
 * 数据源字段类
 * @author: huan
 * @Date: 2020-02-26
 * @Description:
 */
public class DataSourceField extends WorkStageInfo {
    // 字段名
    private String name;
    // 字段类型
    private String type;
    // 精度
    private String precision;
    // 格式
    private String format;

    public DataSourceField(String name) {
        this(name, CoreTag.TABLE_FIELD_TYPE_STRING);
    }

    public DataSourceField(String name, String type) {
        this(name, type, null);
    }

    public DataSourceField(String name, String type, String precision) {
        this(name, type, precision, "yyyy-MM-dd HH:mm:ss");
    }

    public DataSourceField(String name, String type, String precision, String format) {
        this.name = name;
        this.type = type;
        this.precision = precision;
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
