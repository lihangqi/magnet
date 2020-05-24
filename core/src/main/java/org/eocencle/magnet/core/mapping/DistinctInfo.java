package org.eocencle.magnet.core.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 去重信息类
 * @author: huan
 * @Date: 2020-03-15
 * @Description:
 */
public class DistinctInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用
    private String ref;
    // 计数字段
    private String cntField;
    // 去重字段
    List<DistinctField> distinctFields = new ArrayList<>();

    public static class DistinctField extends WorkStageInfo {
        // 字段
        private String field;
        // 是否忽略大小写
        private String ignoreCase;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getIgnoreCase() {
            return ignoreCase;
        }

        public void setIgnoreCase(String ignoreCase) {
            this.ignoreCase = ignoreCase;
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

    public String getCntField() {
        return cntField;
    }

    public void setCntField(String cntField) {
        this.cntField = cntField;
    }

    public List<DistinctField> getDistinctFields() {
        return distinctFields;
    }

    public void setDistinctFields(List<DistinctField> distinctFields) {
        this.distinctFields = distinctFields;
    }

    public void addDistinctFields(DistinctField distinctField) {
        this.distinctFields.add(distinctField);
    }
}
