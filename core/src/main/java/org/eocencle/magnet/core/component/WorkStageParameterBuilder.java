package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.context.Context;

/**
 * 工作节点参数建构类
 * @author: huan
 * @Date: 2020-01-19
 * @Description:
 */
public class WorkStageParameterBuilder {
    /**
     * 构造作业节点参数
     * @Author huan
     * @Date 2020-01-21
     * @Param [context]
     * @Return org.eocencle.magnet.client.component.WorkStageParameter
     * @Exception
     * @Description
     **/
    public static WorkStageParameter construct(Context context) {
        WorkStageParameter param = new WorkStageParameter(context);
        return param;
    }

}
