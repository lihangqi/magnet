package org.eocencle.magnet.core.mapping;

import java.util.List;
import java.util.ArrayList;

/**
 * 过滤信息类
 * @author: huan
 * @Date: 2020-03-11
 * @Description:
 */
public class FilterInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用
    private String ref;
    // 过滤字段
    private List<FilterField> filterFields = new ArrayList<>();

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

    public List<FilterField> getFilterFields() {
        return filterFields;
    }

    public void setFilterFields(List<FilterField> filterFields) {
        this.filterFields = filterFields;
    }

    public void addFilterFields(FilterField filterField) {
        this.filterFields.add(filterField);
    }
}
