package org.eocencle.magnet.client.component;

import org.eocencle.magnet.core.component.WorkStageComponent;
import org.eocencle.magnet.core.component.WorkStageComponentBuilderAssistant;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.wrapper.RunningContainerWrapper;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.session.ProjectConfig;
import org.eocencle.magnet.core.session.RuntimeEnvironmentConfig;

/**
 * 工作组件外观类
 * @author: huan
 * @Date: 2020-02-09
 * @Description:
 */
public class WorkStageComponentFacade {
    // 项目配置
    private ProjectConfig config;

    public WorkStageComponentFacade(ProjectConfig config) {
        this.config = config;
    }

    /**
     * 构建并执行组件
     * @Author huan
     * @Date 2020-02-09
     * @Param []
     * @Return void
     * @Exception
     * @Description
     **/
    public void execute() {
        RuntimeEnvironmentConfig reConfig = new RuntimeEnvironmentConfig(this.config);
        // 构建环境
        ComponentFactory factory = RuntimeEnvironmentBuilder.construct(reConfig);
        // 设置构建助理
        WorkStageComponentBuilderAssistant.setFactory(factory);
        // 注册包装
        factory.createWrapperRegister().register();
        // 构建主要执行组件
        WorkStageComponent composite = WorkStageComponentBuilder.construct(this.config);
        // 包装执行环境
        WorkStageComponent component = new RunningContainerWrapper(composite);
        // 获取执行上下文
        Context context = factory.getRuntimeContext();
        // 初始化上下文
        context.init(this.config.getParameterInfo());
        // 构建工作流参数
        WorkStageParameter parameter = new WorkStageParameter(context);
        // 执行组件
        component.execute(parameter);
    }

}
