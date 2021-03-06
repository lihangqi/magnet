package org.eocencle.magnet.core.mapping;

/**
 * 过滤字段类
 * @author: huan
 * @Date: 2020-07-03
 * @Description:
 */
public class FilterField extends WorkStageInfo {
    // 连接
    private String join;
    // 类型
    private String type;
    // 字段名
    protected String field;
    // 值
    protected String value;
    // 起始值
    protected String start;
    // 终止值
    protected String end;

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
