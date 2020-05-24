package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.exception.UnsupportedException;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业组件抽象类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public abstract class WorkStageComponent implements PrepareDataWrapper, PrepareHandlerWrapper {
    // 组件集合
    protected List<WorkStageComponent> components = new ArrayList<>();
    // 父组件
    private WorkStageComposite parent;

    /**
     * 执行
     * @Author huan
     * @Date 2020-01-21
     * @Param [parameter]
     * @Return java.util.List<org.eocencle.magnet.client.component.WorkStageResult>
     * @Exception
     * @Description
     **/
    public abstract List<WorkStageResult> execute(WorkStageParameter parameter);

    /**
     * 添加子组件
     * @Author huan
     * @Date 2020-01-21
     * @Param [component]
     * @Return void
     * @Exception
     * @Description
     **/
    public void add(WorkStageComponent component) {
        throw new UnsupportedException("Combination not supported!");
    }

    /**
     * 获取父组件
     * @Author huan
     * @Date 2020-01-21
     * @Param []
     * @Return org.eocencle.magnet.client.component.WorkStageComposite
     * @Exception
     * @Description
     **/
    public WorkStageComposite getParent() {
        if (null == this.parent) {
            throw new UnsupportedException("Combination not supported!");
        }
        return this.parent;
    }

    public void setParent(WorkStageComposite parent) {
        this.parent = parent;
    }
}
