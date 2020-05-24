package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.BranchInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

import java.util.List;

/**
 * 分支作业组件类
 * @author: huan
 * @Date: 2020-02-02
 * @Description:
 */
public class BranchWorkStage extends WorkStageComponent {
    // 分支信息
    private BranchInfo branchInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.branchInfo = (BranchInfo) info;
    }

    @Override
    public void initHandler(WorkStageHandler handler) {

    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        return null;
    }
}
