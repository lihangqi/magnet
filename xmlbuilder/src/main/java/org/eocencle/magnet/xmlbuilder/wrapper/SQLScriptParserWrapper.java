package org.eocencle.magnet.xmlbuilder.wrapper;

import org.eocencle.magnet.core.component.WorkStageComponent;
import org.eocencle.magnet.core.component.WorkStageComposite;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.component.wrapper.WorkStageComponentWrapper;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.xmlbuilder.mapping.SQLScriptInfo;

import java.util.List;
import java.util.Map;

/**
 * SQL脚本解析包装类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class SQLScriptParserWrapper extends WorkStageComponentWrapper {

    // 表名替换正则
    private String replaceReg = "^#placeholder#[^\\w]|[^\\w]#placeholder#[^\\w]|[^\\w]#placeholder#$|^#placeholder#$";

    public SQLScriptParserWrapper(WorkStageComponent workStageComponent) {
        super(workStageComponent);
    }

    @Override
    public void before(WorkStageParameter parameter) {
        WorkStageComposite composite = this.getParent();
        SQLScriptInfo info = (SQLScriptInfo) this.getWorkStageInfo();

        String sql = info.getSqlSource().getBoundSQL(composite.getParams()).getSql();
        // 替换表名
        String actualSQL = this.replaceTempTableName(sql);
        info.setSql(actualSQL);
    }

    @Override
    public void after(WorkStageParameter parameter, List<WorkStageResult> results) {

    }

    /**
     * 替换真实表名
     * @Author huan
     * @Date 2020-02-13
     * @Param [sql]
     * @Return java.lang.String
     * @Exception
     * @Description
     **/
    private String replaceTempTableName(String sql) {
        StrictMap<String> tableNames = this.getParent().getTableNameMap();
        String actualSQL = sql;
        for (Map.Entry<String, String> tableName: tableNames.entrySet()) {
            actualSQL = actualSQL.replaceAll(this.replaceReg.replaceAll("#placeholder#", tableName.getKey()),
                    CoreTag.SPLIT_BLANK + tableName.getValue() + CoreTag.SPLIT_BLANK);
        }
        return actualSQL;
    }

}
