package org.eocencle.magnet.core.mapping;

import org.eocencle.magnet.core.util.StrictMap;

/**
 * 表信息类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class TableInfo extends DataSourceInfo {
    // 别名
    private String alias;
    // 表类型
    private String style;
    // 数据源地址
    private String src;
    // 文件格式
    private String format;
    // 分隔符
    private String separator;
    // 表字段
    private StrictMap<DataSourceField> fields = new StrictMap<>("Table Fields");
    // 数据源配置
    private StrictMap<InfoParam> configParams = new StrictMap<InfoParam>("config params");

    public void addField(DataSourceField field) {
        this.fields.put(field.getName(), field);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
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

    public StrictMap<InfoParam> getConfigParams() {
        return configParams;
    }

    public void setConfigParams(StrictMap<InfoParam> configParams) {
        this.configParams = configParams;
    }

    public void putConfigParam(InfoParam param) {
        this.configParams.put(param.getKey(), param);
    }

    public String getConfigParam(String key) {
        return this.configParams.get(key).getValue();
    }
}
