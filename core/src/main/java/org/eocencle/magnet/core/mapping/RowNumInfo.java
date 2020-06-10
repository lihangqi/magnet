package org.eocencle.magnet.core.mapping;

/**
 * 行号信息类
 * @author: huan
 * @Date: 2020-06-07
 * @Description:
 */
public class RowNumInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用id
    private String ref;
    // 行号字段
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
