package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.mapping.StringCutsInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 字符串切割作业节点抽象类
 * @author: huan
 * @Date: 2020-04-24
 * @Description:
 */
public abstract class StringCutsWorkStage extends WorkStageComponent {
    // 字符串切割信息类
    protected StringCutsInfo stringCutsInfo;

    @Override
    public void initData(WorkStageInfo info) {
        this.stringCutsInfo = (StringCutsInfo) info;
    }
}