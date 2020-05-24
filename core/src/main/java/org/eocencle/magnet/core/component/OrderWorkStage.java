package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.OrderInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 排序作业节点抽象类
 * @author: huan
 * @Date: 2020-03-16
 * @Description:
 */
public abstract class OrderWorkStage extends WorkStageComponent {
    // 排序信息类
    protected OrderInfo orderInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.orderInfo = (OrderInfo) info;
    }
}
