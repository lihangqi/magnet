package org.eocencle.magnet.core.component.wrapper;

import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

import java.util.List;

/**
 * 作业组件包装抽象类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public abstract class WorkStageComponentWrapper extends WorkStageComponent {
    // 作业节点
    private WorkStageComponent workStageComponent;
    // 作业信息
    private WorkStageInfo workStageInfo;
    // 作业操作
    private WorkStageHandler workStageHandler;
    // 任务异常
    private static Exception taskException;

    public WorkStageComponentWrapper(WorkStageComponent workStageComponent) {
        this.workStageComponent = workStageComponent;
    }

    @Override
    public void initData(WorkStageInfo info) {
        this.workStageInfo = info;
    }

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.workStageHandler = handler;
    }

    @Override
    public final List<WorkStageResult> execute(WorkStageParameter parameter) {
        List<WorkStageResult> results = null;
        this.before(parameter);
        try {
            results = this.workStageComponent.execute(parameter);
        } catch (Exception e) {
            // 捕获任务异常并退出
            taskException = e;
        }
        this.after(parameter, results);
        return results;
    }

    @Override
    public void setParent(WorkStageComposite parent) {
        super.setParent(parent);
        this.workStageComponent.setParent(parent);
    }

    /**
     * 前置处理
     * @Author huan
     * @Date 2020-02-24
     * @Param [parameter]
     * @Return void
     * @Exception
     * @Description
     **/
    public abstract void before(WorkStageParameter parameter);

    /**
     * 后置处理
     * @Author huan
     * @Date 2020-02-24
     * @Param [parameter, results]
     * @Return void
     * @Exception
     * @Description
     **/
    public abstract void after(WorkStageParameter parameter, List<WorkStageResult> results);

    public WorkStageComponent getWorkStageComponent() {
        return workStageComponent;
    }

    public WorkStageInfo getWorkStageInfo() {
        return workStageInfo;
    }

    public WorkStageHandler getWorkStageHandler() {
        return workStageHandler;
    }

    public static Exception getTaskException() {
        return taskException;
    }
}
