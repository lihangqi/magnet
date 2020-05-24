package org.eocencle.magnet.xmlbuilder.mapping;

import org.eocencle.magnet.core.mapping.SQLInfo;

/**
 * SQL脚本信息类
 * @author: huan
 * @Date: 2020-04-17
 * @Description:
 */
public class SQLScriptInfo extends SQLInfo {
    // sql源
    private SQLSource sqlSource;

    public SQLScriptInfo(SQLSource sqlSource) {
        this.sqlSource = sqlSource;
    }

    public SQLSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SQLSource sqlSource) {
        this.sqlSource = sqlSource;
    }
}
