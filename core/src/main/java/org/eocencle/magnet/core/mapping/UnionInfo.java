package org.eocencle.magnet.core.mapping;

/**
 * 合并信息类
 * @author: huan
 * @Date: 2020-04-03
 * @Description:
 */
public class UnionInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // 引用id
    private String refs;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRefs() {
        return refs;
    }

    public void setRefs(String refs) {
        this.refs = refs;
    }
}
