package org.eocencle.magnet.core.component.wrapper;

import org.eocencle.magnet.core.mapping.WorkStageInfo;

/**
 * 匹配的包装抽象类
 * @author: huan
 * @Date: 2020-04-19
 * @Description:
 */
public abstract class MatchWrapper {
    // 匹配值
    private String matcher;
    // 包装类
    private Class<? extends WorkStageComponentWrapper> wrapperCls;

    public MatchWrapper(String matcher, Class<? extends WorkStageComponentWrapper> wrapperCls) {
        this.matcher = matcher;
        this.wrapperCls = wrapperCls;
    }

    /**
     * 验证是否匹配标签
     * @Author huan
     * @Date 2020-05-02
     * @Param [info]
     * @Return boolean
     * @Exception
     * @Description
     */
    public abstract boolean check(WorkStageInfo info);

    public String getMatcher() {
        return matcher;
    }

    public void setMatcher(String matcher) {
        this.matcher = matcher;
    }

    public Class<? extends WorkStageComponentWrapper> getWrapperCls() {
        return wrapperCls;
    }

    public void setWrapperCls(Class<? extends WorkStageComponentWrapper> wrapperCls) {
        this.wrapperCls = wrapperCls;
    }
}
