package org.eocencle.magnet.core.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 关联信息类
 * @author: huan
 * @Date: 2020-04-04
 * @Description:
 */
public class JoinInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 关联类型
    private String type;
    // 关联左引用
    private String leftRef;
    // 关联右引用
    private String rightRef;
    // 过滤字段
    private List<FilterInfo.FilterField> filterFields = new ArrayList<>();

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLeftRef() {
        return leftRef;
    }

    public void setLeftRef(String leftRef) {
        this.leftRef = leftRef;
    }

    public String getRightRef() {
        return rightRef;
    }

    public void setRightRef(String rightRef) {
        this.rightRef = rightRef;
    }

    public List<FilterInfo.FilterField> getFilterFields() {
        return filterFields;
    }

    public void setFilterFields(List<FilterInfo.FilterField> filterFields) {
        this.filterFields = filterFields;
    }

    public void addFilterFields(FilterInfo.FilterField filterField) {
        this.filterFields.add(filterField);
    }
}
