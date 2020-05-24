package org.eocencle.magnet.core.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串切割信息类
 * @author: huan
 * @Date: 2020-04-24
 * @Description:
 */
public class StringCutsInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用id
    private String ref;
    // 字符串切割列表
    private List<StringCut> stringCuts = new ArrayList<>();

    public static class StringCut extends WorkStageInfo {
        // 字段名
        private String field;
        // 目标字段名
        private String tagField;
        // 起始值
        private String start;
        // 终止值
        private String end;

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

    public List<StringCut> getStringCuts() {
        return stringCuts;
    }

    public void setStringCuts(List<StringCut> stringCuts) {
        this.stringCuts = stringCuts;
    }

    public void addStringCut(StringCut stringCut) {
        this.stringCuts.add(stringCut);
    }
}