package org.eocencle.magnet.core.mapping;

import org.eocencle.magnet.core.util.CoreTag;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组信息类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class GroupInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用
    private String ref;
    // 分组字段
    private List<GroupField> groupFields = new ArrayList<>();
    // 排序字段
    private List<OrderField> orderFields = new ArrayList<>();
    // 行号字段
    private String rownumField;
    // 流状态
    private String streamState;

    /**
     * 分组字段类
     * @author: huan
     * @Date: 2020-03-11
     * @Description:
     */
    public static class GroupField extends WorkStageInfo {
        // 字段名称
        private String fieldName;

        public GroupField(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }
    }

    /**
     * 排序字段类
     * @author: huan
     * @Date: 2020-03-11
     * @Description:
     */
    public static class OrderField extends WorkStageInfo {
        // 字段名称
        private String fieldName;
        // 顺序
        private String hasAsc;

        public OrderField(String fieldName) {
            this(fieldName, CoreTag.ORDERBY_ASC);
        }

        public OrderField(String fieldName, String hasAsc) {
            this.fieldName = fieldName;
            this.hasAsc = hasAsc;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getHasAsc() {
            return hasAsc;
        }

        public void setHasAsc(String hasAsc) {
            this.hasAsc = hasAsc;
        }
    }

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

    public void addGroupField(GroupField field) {
        this.groupFields.add(field);
    }

    public List<GroupField> getGroupFields() {
        return groupFields;
    }

    public List<String> getGroupFieldsStr() {
        List<String> result = new ArrayList<>();
        for (GroupField field: this.groupFields) {
            result.add(field.getFieldName());
        }
        return result;
    }

    public void addOrderField(OrderField field) {
        this.orderFields.add(field);
    }

    public List<OrderField> getOrderFields() {
        return orderFields;
    }

    public String getRownumField() {
        return rownumField;
    }

    public void setRownumField(String rownumField) {
        this.rownumField = rownumField;
    }

    public String getStreamState() {
        return streamState;
    }

    public void setStreamState(String streamState) {
        this.streamState = streamState;
    }
}
