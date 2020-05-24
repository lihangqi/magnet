package org.eocencle.magnet.core.mapping;

/**
 * 添加序列信息类
 * @author: huan
 * @Date: 2020-04-30
 * @Description:
 */
public class AddSequenceInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用id
    private String ref;
    // 目标字段
    private String tagField;
    // 初始值
    private String init;
    // 步长
    private String step;

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

    public String getTagField() {
        return tagField;
    }

    public void setTagField(String tagField) {
        this.tagField = tagField;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
