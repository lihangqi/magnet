package org.eocencle.magnet.core.mapping;

import org.eocencle.magnet.core.util.StrictMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 值映射信息类
 * @author: huan
 * @Date: 2020-04-08
 * @Description:
 */
public class ValueMappersInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用id
    private String ref;
    // 值映射列表
    private List<ValueMapper> valueMappers = new ArrayList<>();

    public static class ValueMapper extends WorkStageInfo {
        // 字段名
        private String field;
        // 目标字段名
        private String tagField;
        // 未匹配上的默认值
        private String nonMatch;
        // 值映射
        private StrictMap<InfoParam> valMappers = new StrictMap<InfoParam>("Value mappers");

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getTagField() {
            return tagField;
        }

        public void setTagField(String tagField) {
            this.tagField = tagField;
        }

        public String getNonMatch() {
            return nonMatch;
        }

        public void setNonMatch(String nonMatch) {
            this.nonMatch = nonMatch;
        }

        public StrictMap<InfoParam> getValMappers() {
            return valMappers;
        }

        public void setValMappers(StrictMap<InfoParam> valMappers) {
            this.valMappers = valMappers;
        }

        public void putValMapper(InfoParam valMapper) {
            this.valMappers.put(valMapper.getKey(), valMapper);
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

    public List<ValueMapper> getValueMappers() {
        return valueMappers;
    }

    public void setValueMappers(List<ValueMapper> valueMappers) {
        this.valueMappers = valueMappers;
    }

    public void addValueMapper(ValueMapper valueMapper) {
        this.valueMappers.add(valueMapper);
    }
}
