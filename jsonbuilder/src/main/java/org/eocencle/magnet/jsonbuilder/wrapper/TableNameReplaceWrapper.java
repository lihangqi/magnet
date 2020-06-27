package org.eocencle.magnet.jsonbuilder.wrapper;

import org.eocencle.magnet.core.component.WorkStageComponent;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.component.wrapper.WorkStageComponentWrapper;
import org.eocencle.magnet.core.mapping.SQLInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;

import java.util.List;
import java.util.Map;

/**
 * SQL表名替换包装类
 * @author: huan
 * @Date: 2020-06-23
 * @Description:
 */
public class TableNameReplaceWrapper extends WorkStageComponentWrapper {
    // 表名替换正则
    private String replaceReg = "^#placeholder#[^\\w]|[^\\w]#placeholder#[^\\w]|[^\\w]#placeholder#$|^#placeholder#$";

    public TableNameReplaceWrapper(WorkStageComponent workStageComponent) {
        super(workStageComponent);
    }

    @Override
    public void before(WorkStageParameter parameter) {
        SQLInfo info = (SQLInfo) this.getWorkStageInfo();

        // 替换表名
        info.setSql(this.replaceTempTableName(info.getSql()));
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
