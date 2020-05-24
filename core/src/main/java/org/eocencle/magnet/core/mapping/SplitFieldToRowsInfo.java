package org.eocencle.magnet.core.mapping;

/**
 * 列分隔转行信息类
 * @author: huan
 * @Date: 2020-04-13
 * @Description:
 */
public class SplitFieldToRowsInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用id
    private String ref;
    // 字段名
    private String field;
    // 分隔符
    private String separator;
    // 分隔符是否是正则表达式
    private String isRegex;
    // 目标字段名
    private String tagField;
    // 行号字段
    private String rowNumField;

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

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getIsRegex() {
        return isRegex;
    }

    public void setIsRegex(String isRegex) {
        this.isRegex = isRegex;
    }

    public String getTagField() {
        return tagField;
    }

    public void setTagField(String tagField) {
        this.tagField = tagField;
    }

    public String getRowNumField() {
        return rowNumField;
    }

    public void setRowNumField(String rowNumField) {
        this.rowNumField = rowNumField;
    }
}
