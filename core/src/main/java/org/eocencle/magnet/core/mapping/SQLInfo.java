package org.eocencle.magnet.core.mapping;

/**
 * SQL信息类
 * @author: huan
 * @Date: 2020-04-17
 * @Description:
 */
public class SQLInfo extends WorkFlowInfo {
    // 别名
    private String alias;
    // sql
    private String sql;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
