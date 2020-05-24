package org.eocencle.magnet.core.component.wrapper;

import org.apache.log4j.Logger;
import org.eocencle.magnet.core.component.WorkStageComponent;
import org.eocencle.magnet.core.component.WorkStageComposite;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.mapping.BranchInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;
import org.eocencle.magnet.core.util.StrictMap;

import java.util.List;

/**
 * 收集作业组件执行结果包装类
 * @author: huan
 * @Date: 2020-02-29
 * @Description:
 */
public class CollectResultWrapper extends WorkStageComponentWrapper {
    private static final Logger logger = Logger.getLogger(CollectResultWrapper.class);

    public CollectResultWrapper(WorkStageComponent workStageComponent) {
        super(workStageComponent);
    }

    @Override
    public void before(WorkStageParameter parameter) {

    }

    @Override
    public void after(WorkStageParameter parameter, List<WorkStageResult> results) {
        if (null != results) {
            WorkStageComposite composite = this.getParent();
            WorkStageInfo workStageInfo = this.getWorkStageInfo();
            if (workStageInfo instanceof BranchInfo) {
                List<BranchInfo.DataSet> dataSets = ((BranchInfo) workStageInfo).getDataSet();
                StrictMap<WorkStageResult> branchResultMap = new StrictMap<WorkStageResult>("Branch results");
                WorkStageResult branchResult = null;
                for (WorkStageResult result: results) {
                    branchResultMap.put(result.getId(), result);
                }

                for (BranchInfo.DataSet dataSet: dataSets) {
                    branchResult = branchResultMap.get(dataSet.getId());
                    composite.addResult(branchResult);
                    logger.info("Collect execution results of '" + dataSet.getId() + "'");
                    composite.changeLastResult(branchResult);
                }
            } else {
                composite.addResult(results.get(0));
                logger.info("Collect execution results of '" + results.get(0).getId() + "'");
                composite.changeLastResult(results.get(0));
            }
        }
    }
}
