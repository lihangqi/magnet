package org.eocencle.magnet.spark1.component.wrapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eocencle.magnet.core.component.WorkStageComponent;
import org.eocencle.magnet.core.component.WorkStageComposite;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.component.wrapper.WorkStageComponentWrapper;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;

import java.util.List;

/**
 * SparkSQL表注册包装类
 * @author: huan
 * @Date: 2020-02-29
 * @Description:
 */
public class SparkSQLTableRegisterWrapper extends WorkStageComponentWrapper {
    private static final Logger logger = Logger.getLogger(SparkSQLTableRegisterWrapper.class);
    
    public SparkSQLTableRegisterWrapper(WorkStageComponent workStageComponent) {
        super(workStageComponent);
    }

    @Override
    public void before(WorkStageParameter parameter) {
        
    }

    /**
     * SparkSQL注册表
     * @Author huan
     * @Date 2020-02-29
     * @Param [parameter, results]
     * @Return void
     * @Exception
     * @Description 首先选择alias进行注册，如果没有再选择id进行注册，只会有一个注册成功
     **/
    @Override
    public void after(WorkStageParameter parameter, List<WorkStageResult> results) {
        if (null != results) {
            WorkStageComposite composite = this.getParent();
            SparkWorkStageResult sparkResult = null;
            for (WorkStageResult result: results) {
                sparkResult = (SparkWorkStageResult) result;

                boolean isRegisterAlias = false;
                // 注册alias名
                if (StringUtils.isNotBlank(sparkResult.getAlias())) {
                    String alias = sparkResult.getAlias();
                    String aliasName = composite.getMixedTableName(alias);
                    composite.putTableName(alias, aliasName);
                    sparkResult.getDf().registerTempTable(aliasName);
                    isRegisterAlias = true;
                    logger.info("Alias '" + alias + "' registered successfully!");
                }

                // 注册id名
                String id = sparkResult.getId();
                String idName = composite.getMixedTableName(id);
                if (!isRegisterAlias) {
                    composite.putTableName(id, idName);
                    sparkResult.getDf().registerTempTable(idName);
                    logger.info("ID '" + id + "' registered successfully!");
                }
            }
        }
    }
}
