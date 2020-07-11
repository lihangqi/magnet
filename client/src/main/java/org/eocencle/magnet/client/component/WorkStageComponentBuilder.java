package org.eocencle.magnet.client.component;

import org.eocencle.magnet.core.component.WorkStageComponent;
import org.eocencle.magnet.core.component.WorkStageComponentBuilderAssistant;
import org.eocencle.magnet.core.component.WorkStageComposite;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.component.wrapper.WrapperManager;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.mapping.*;
import org.eocencle.magnet.core.session.ProjectConfig;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.xmlbuilder.mapping.SQLScriptInfo;

import java.util.Map;

/**
 * 作业节点建构类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class WorkStageComponentBuilder {
    /**
     * 构造作业节点组件
     * @Author huan
     * @Date 2020-01-21
     * @Param [config]
     * @Return org.eocencle.magnet.client.component.WorkStageComponent
     * @Exception
     * @Description
     **/
    public static WorkStageComponent construct(ProjectConfig config) {
        BranchInfo info = new BranchInfo();
        info.setId("main");
        info.setProjectConfig(config);
        return construct(config, info);
    }

    /**
     * 构造作业节点组件
     * @Author huan
     * @Date 2020-02-06
     * @Param [config, branch]
     * @Return org.eocencle.magnet.client.component.WorkStageComponent
     * @Exception
     * @Description
     **/
    public static WorkStageComponent construct(ProjectConfig config, BranchInfo branch) {
        // 执行容器
        WorkStageComposite mainComposite = new WorkStageComposite();
        // 初始化执行容器
        mainComposite.initData(branch);
        // 执行组件工厂
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();
        // 数据源
        DataSourceInfo dataSourceInfo = null;
        // 组件
        WorkStageComponent component = null;
        // 操作
        WorkStageHandler operate = null;
        // 流组件
        WorkStageComponent streamComponent = null;
        // 流数据加载组件
        WorkStageComponent streamDataLoadComponent = null;

        // 构造参数
        mainComposite.setParams((StrictMap<Object>) config.getParameterInfo().clone());

        // 构造数据源组件
        StrictMap<DataSourceInfo> datasource = config.getDataSourceInfo();
        for (Map.Entry<String, DataSourceInfo> entry: datasource.entrySet()) {
            dataSourceInfo = entry.getValue();
            if (dataSourceInfo instanceof TableInfo) {
                // 表数据
                component = factory.createTableWorkStageComponent();
                operate = factory.createTableWorkStageHandler((TableInfo) dataSourceInfo);
            } else if (dataSourceInfo instanceof StreamInfo) {
                // 流数据
                streamComponent = factory.createStreamWorkStageComponent();
                streamComponent.initData(dataSourceInfo);
                streamComponent.initHandler(factory.createStreamWorkStageHandler((StreamInfo) dataSourceInfo));

                streamDataLoadComponent = factory.createStreamDataLoadWorkStageComponent();
                continue;
            }

            mainComposite.add(WrapperManager.wrapper(component, dataSourceInfo, operate));
        }

        // 构造工作流组件
        StrictMap<WorkFlowInfo> workflow = config.getWorkFlowInfo();
        String confReadMode = config.getParameterInfo(CoreTag.MAGNET_CONFIG_READ_MODE).toString();
        if (CoreTag.PROCESS_MODE_BATCH.equalsIgnoreCase(config.getParameterInfo(CoreTag.MAGNET_PROCESS_MODE).toString())) {
            buildWorkflow(mainComposite, workflow, confReadMode);
        } else if (CoreTag.PROCESS_MODE_STREAM.equalsIgnoreCase(config.getParameterInfo(CoreTag.MAGNET_PROCESS_MODE).toString())) {
            mainComposite.add(streamComponent);
            streamComponent.add(WrapperManager.wrapper(streamDataLoadComponent, dataSourceInfo, null));
            buildWorkflow(streamComponent, workflow, confReadMode);
        }

        return mainComposite;
    }

    /**
     * 建构工作流
     * @Author huan
     * @Date 2020-03-18
     * @Param [container, workflow, confReadMode]
     * @Return void
     * @Exception
     * @Description
     **/
    private static void buildWorkflow(WorkStageComponent container, StrictMap<WorkFlowInfo> workflow, String confReadMode) {
        // 执行组件工厂
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();
        // 工作流信息
        WorkFlowInfo workFlowInfo = null;
        // 组件
        WorkStageComponent component = null;
        // 操作
        WorkStageHandler operate = null;
        // 分支配置
        ProjectConfig branchProjectConfig = null;

        for (Map.Entry<String, WorkFlowInfo> entry: workflow.entrySet()) {
            workFlowInfo = entry.getValue();
            if (workFlowInfo instanceof BranchInfo) {
                // 分支
                BranchInfo branchInfo = (BranchInfo) workFlowInfo;
                branchProjectConfig = branchInfo.getProjectConfig();
                branchProjectConfig.putParameterInfo(CoreTag.MAGNET_FILE_NAME, branchInfo.getFileName());
                branchProjectConfig.putParameterInfo(CoreTag.MAGNET_FILE_PATH, branchInfo.getSrc());
                component = construct(branchProjectConfig, branchInfo);
                operate = null;
            } else if (workFlowInfo instanceof SQLInfo) {
                // SQL解析
                component = factory.createSQLWorkStageComponent();
                if (CoreTag.CONFIG_READ_MODE_XML.equals(confReadMode)) {
                    operate = factory.createSQLWorkStageHandler((SQLScriptInfo) workFlowInfo);
                }
            } else if (workFlowInfo instanceof OutputInfo) {
                // 输出
                component = factory.createOutputWorkStageComponent();
                operate = factory.createOutputWorkStageHandler((OutputInfo) workFlowInfo);
            } else if (workFlowInfo instanceof GroupInfo) {
                // 分组
                component = factory.createGroupWorkStageComponent();
                operate = factory.createGroupWorkStageHandler((GroupInfo) workFlowInfo);
            } else if (workFlowInfo instanceof FilterInfo) {
                // 过滤
                component = factory.createFilterWorkStageComponent();
                operate = factory.createFilterWorkStageHandler((FilterInfo) workFlowInfo);
            } else if (workFlowInfo instanceof DistinctInfo) {
                // 去重
                component = factory.createDistinctWorkStageComponent();
                operate = factory.createDistinctWorkStageHandler((DistinctInfo) workFlowInfo);
            } else if (workFlowInfo instanceof OrderInfo) {
                // 排序
                component = factory.createOrderWorkStageComponent();
                operate = factory.createOrderWorkStageHandler((OrderInfo) workFlowInfo);
            } else if (workFlowInfo instanceof UnionInfo) {
                // 合并
                component = factory.createUnionWorkStageComponent();
                operate = factory.createUnionWorkStageHandler((UnionInfo) workFlowInfo);
            } else if (workFlowInfo instanceof JoinInfo) {
                // 关联
                component = factory.createJoinWorkStageComponent();
                operate = factory.createJoinWorkStageHandler((JoinInfo) workFlowInfo);
            } else if (workFlowInfo instanceof SchemaInfo) {
                // Schema
                component = factory.createSchemaWorkStageComponent();
                operate = factory.createSchemaWorkStageHandler((SchemaInfo) workFlowInfo);
            } else if (workFlowInfo instanceof ValueMappersInfo) {
                // ValueMappers
                component = factory.createValueMappersWorkStageComponent();
                operate = factory.createValueMappersWorkStageHandler((ValueMappersInfo) workFlowInfo);
            } else if (workFlowInfo instanceof SplitFieldToRowsInfo) {
                // 列分隔转行
                component = factory.createSplitFieldToRowsWorkStageComponent();
                operate = factory.createSplitFieldToRowsWorkStageHandler((SplitFieldToRowsInfo) workFlowInfo);
            } else if (workFlowInfo instanceof StringCutsInfo) {
                // 字符串切割
                component = factory.createStringCutsWorkStageComponent();
                operate = factory.createStringCutsWorkStageHandler((StringCutsInfo) workFlowInfo);
            } else if (workFlowInfo instanceof AddFieldsInfo) {
                // 添加字段
                component = factory.createAddFieldsWorkStageComponent();
                operate = factory.createAddFieldsWorkStageHandler((AddFieldsInfo) workFlowInfo);
            } else if (workFlowInfo instanceof AddSequenceInfo) {
                // 添加序列
                component = factory.createAddSequenceWorkStageComponent();
                operate = factory.createAddSequenceWorkStageHandler((AddSequenceInfo) workFlowInfo);
            } else if (workFlowInfo instanceof RowNumInfo) {
                // 添加行号
                component = factory.createRowNumWorkStageComponent();
                operate = factory.createRowNumWorkStageHandler((RowNumInfo) workFlowInfo);
            } else if (workFlowInfo instanceof QueryInfo) {
                // 添加查询
                component = factory.createQueryWorkStageComponent();
                operate = factory.createQueryWorkStageHandler((QueryInfo) workFlowInfo);
            } else {
                throw new UnsupportedException("Unsupported workflow type " + workFlowInfo.getClass().getName() + "!");
            }

            container.add(WrapperManager.wrapper(component, workFlowInfo, operate));
        }
    }
}
