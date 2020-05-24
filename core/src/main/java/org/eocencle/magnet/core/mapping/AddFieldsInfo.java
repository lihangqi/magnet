package org.eocencle.magnet.core.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加字段信息类
 * @author: huan
 * @Date: 2020-04-28
 * @Description:
 */
public class AddFieldsInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用id
    private String ref;
    // 添加的字段
    private List<AddField> addFields = new ArrayList<>();

    public static class AddField extends WorkStageInfo {
        // 字段名
        private String name;
        // 字段类型
        private String type;
        // 精度
        private String precision;
        // 格式
        private String format;
        // 值
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrecision() {
            return precision;
        }

        public void setPrecision(String precision) {
            this.precision = precision;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
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

    public List<AddField> getAddFields() {
        return addFields;
    }

    public void setAddFields(List<AddField> addFields) {
        this.addFields = addFields;
    }

    public void addAddField(AddField addField) {
        this.addFields.add(addField);
    }
}
