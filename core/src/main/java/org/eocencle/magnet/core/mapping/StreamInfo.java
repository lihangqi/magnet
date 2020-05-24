package org.eocencle.magnet.core.mapping;

import org.eocencle.magnet.core.util.StrictMap;

/**
 * 流信息类
 * @author: huan
 * @Date: 2020-02-02
 * @Description:
 */
public class StreamInfo extends DataSourceInfo {
    // 别名
    private String alias;
    // topic
    private String topics;
    // 文件格式
    private String format;
    // 分隔符
    private String separator;
    // 表字段
    private StrictMap<DataSourceField> fields = new StrictMap<>("Table Fields");

    public void addField(DataSourceField field) {
        this.fields.put(field.getName(), field);
    }
    // kafka配置
    private StrictMap<InfoParam> config = new StrictMap<>("Config params");

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public StrictMap<DataSourceField> getFields() {
        return fields;
    }

    public void setFields(StrictMap<DataSourceField> fields) {
        this.fields = fields;
    }

    public StrictMap<InfoParam> getConfig() {
        return config;
    }

    public void setConfig(StrictMap<InfoParam> config) {
        this.config = config;
    }
}
