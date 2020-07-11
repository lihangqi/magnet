package org.eocencle.magnet.core.mapping;

import org.eocencle.magnet.core.util.StrictMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询信息类
 * @author: huan
 * @Date: 2020-07-02
 * @Description:
 */
public class QueryInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用
    private String ref;
    // 查询类型
    private String type;
    // 是否缓存
    private String cache;
    // 过滤字段
    private List<FilterField> filterFields = new ArrayList<>();

    public void addFilterField(FilterField filterField) {
        this.filterFields.add(filterField);
    }
    // 表字段
    private StrictMap<DataSourceField> fields = new StrictMap<>("Table Fields");

    public void addField(DataSourceField field) {
        this.fields.put(field.getName(), field);
    }
    // 查询配置
    private StrictMap<InfoParam> config = new StrictMap<>("Config params");

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public List<FilterField> getFilterFields() {
        return filterFields;
    }

    public void setFilterFields(List<FilterField> filterFields) {
        this.filterFields = filterFields;
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
