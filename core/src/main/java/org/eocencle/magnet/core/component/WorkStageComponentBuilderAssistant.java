package org.eocencle.magnet.core.component;

import org.eocencle.magnet.core.context.ComponentFactory;

/**
 * 作业节点建构辅助类
 * @author: huan
 * @Date: 2020-01-21
 * @Description:
 */
public class WorkStageComponentBuilderAssistant {
    // 执行环境工厂
    private static ComponentFactory FACTORY;

    public static ComponentFactory getFactory() {
        return FACTORY;
    }

    public static void setFactory(ComponentFactory factory) {
        FACTORY = factory;
    }
}
