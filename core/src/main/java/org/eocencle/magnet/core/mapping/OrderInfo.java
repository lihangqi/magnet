package org.eocencle.magnet.core.mapping;

/**
 * 排序信息类
 * @author: huan
 * @Date: 2020-03-16
 * @Description:
 */
public class OrderInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用
    private String ref;
    // 排序字段
    private String field;

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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
