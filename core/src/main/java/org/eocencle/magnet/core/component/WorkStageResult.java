package org.eocencle.magnet.core.component;

/**
 * 作业结果抽象类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public abstract class WorkStageResult {
    // id
    private String id;
    // 别名
    private String alias;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
