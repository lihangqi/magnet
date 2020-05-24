package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.context.Context;

/**
 * 工作节点参数类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class WorkStageParameter {
    // 执行环境
    private Context context;

    public WorkStageParameter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
