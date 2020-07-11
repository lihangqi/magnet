package org.eocencle.magnet.core.context;

import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.component.wrapper.WrapperRegister;
import org.eocencle.magnet.core.mapping.*;

/**
 * 环境抽象工厂接口
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public interface ComponentFactory {
    /**
     * 获取执行环境
     * @Author huan
     * @Date 2020-01-22
     * @Param []
     * @Return org.eocencle.magnet.context.Context
     * @Exception
     * @Description
     **/
    Context getRuntimeContext();

    /**
     * 创建表作业组件
     * @Author huan
     * @Date 2020-01-22
     * @Param []
     * @Return org.eocencle.magnet.component.TableWorkStage
     * @Exception
     * @Description
     **/
    TableWorkStage createTableWorkStageComponent();

    /**
     * 创建流作业组件
     * @Author huan
     * @Date 2020-01-22
     * @Param []
     * @Return org.eocencle.magnet.component.StreamWorkStage
     * @Exception
     * @Description
     **/
    StreamWorkStage createStreamWorkStageComponent();

    /**
     * 创建流数据加载作业组件
     * @Author huan
     * @Date 2020-06-27
     * @Param []
     * @Return org.eocencle.magnet.core.component.StreamDataLoadWorkStage
     * @Exception
     * @Description
     */
    StreamDataLoadWorkStage createStreamDataLoadWorkStageComponent();

    /**
     * 创建SQL作业组件
     * @Author huan
     * @Date 2020-01-22
     * @Param []
     * @Return org.eocencle.magnet.component.SQLWorkStage
     * @Exception
     * @Description
     **/
    SQLWorkStage createSQLWorkStageComponent();

    /**
     * 创建组作业组件
     * @Author huan
     * @Date 2020-01-22
     * @Param []
     * @Return org.eocencle.magnet.component.GroupWorkStage
     * @Exception
     * @Description
     **/
    GroupWorkStage createGroupWorkStageComponent();

    /**
     * 创建输出作业组件
     * @Author huan
     * @Date 2020-01-22
     * @Param []
     * @Return org.eocencle.magnet.component.OutputWorkStage
     * @Exception
     * @Description
     **/
    OutputWorkStage createOutputWorkStageComponent();

    /**
     * 创建过滤作业组件
     * @Author huan
     * @Date 2020-03-13
     * @Param []
     * @Return org.eocencle.magnet.component.FilterWorkStage
     * @Exception
     * @Description
     **/
    FilterWorkStage createFilterWorkStageComponent();

    /**
     * 创建去重作业组件
     * @Author huan
     * @Date 2020-03-15
     * @Param []
     * @Return org.eocencle.magnet.component.DistinctWorkStage
     * @Exception
     * @Description
     **/
    DistinctWorkStage createDistinctWorkStageComponent();

    /**
     * 创建排序作业组件
     * @Author huan
     * @Date 2020-03-16
     * @Param []
     * @Return org.eocencle.magnet.component.OrderWorkStage
     * @Exception
     * @Description
     **/
    OrderWorkStage createOrderWorkStageComponent();

    /**
     * 创建合并作业组件
     * @Author huan
     * @Date 2020-04-03
     * @Param []
     * @Return org.eocencle.magnet.component.UnionWorkStage
     * @Exception
     * @Description
     **/
    UnionWorkStage createUnionWorkStageComponent();

    /**
     * 创建关联作业组件
     * @Author huan
     * @Date 2020-04-04
     * @Param []
     * @Return org.eocencle.magnet.component.JoinWorkStage
     * @Exception
     * @Description
     **/
    JoinWorkStage createJoinWorkStageComponent();

    /**
     * 创建Schema作业组件
     * @Author huan
     * @Date 2020-04-06
     * @Param []
     * @Return org.eocencle.magnet.component.SchemaWorkStage
     * @Exception
     * @Description
     **/
    SchemaWorkStage createSchemaWorkStageComponent();

    /**
     * 创建值映射作业组件
     * @Author huan
     * @Date 2020-04-08
     * @Param []
     * @Return org.eocencle.magnet.component.ValueMappersWorkStage
     * @Exception
     * @Description
     **/
    ValueMappersWorkStage createValueMappersWorkStageComponent();

    /**
     * 创建值映射作业组件
     * @Author huan
     * @Date 2020-04-13
     * @Param []
     * @Return org.eocencle.magnet.component.SplitFieldToRowsWorkStage
     * @Exception
     * @Description
     **/
    SplitFieldToRowsWorkStage createSplitFieldToRowsWorkStageComponent();

    /**
     * 创建字符串切割作业组件
     * @Author huan
     * @Date 2020-04-24
     * @Param []
     * @Return org.eocencle.magnet.core.component.StringCutsWorkStage
     * @Exception
     * @Description
     **/
    StringCutsWorkStage createStringCutsWorkStageComponent();

    /**
     * 创建添加字段作业组件
     * @Author huan
     * @Date 2020-04-28
     * @Param []
     * @Return org.eocencle.magnet.core.component.AddFieldsWorkStage
     * @Exception
     * @Description
     **/
    AddFieldsWorkStage createAddFieldsWorkStageComponent();

    /**
     * 创建序列字段作业组件
     * @Author huan
     * @Date 2020-04-30
     * @Param []
     * @Return org.eocencle.magnet.core.component.AddSequenceWorkStage
     * @Exception
     * @Description
     **/
    AddSequenceWorkStage createAddSequenceWorkStageComponent();

    /**
     * 创建行号字段作业组件
     * @Author huan
     * @Date 2020-06-07
     * @Param []
     * @Return org.eocencle.magnet.core.component.RowNumWorkStage
     * @Exception
     * @Description
     */
    RowNumWorkStage createRowNumWorkStageComponent();

    /**
     * 创建查询作业组件
     * @author: huan
     * @Date: 2020-07-05
     * @Description:
     */
    QueryWorkStage createQueryWorkStageComponent();

    /**
     * 创建作业结果
     * @Author huan
     * @Date 2020-01-22
     * @Param []
     * @Return org.eocencle.magnet.component.WorkStageResult
     * @Exception
     * @Description
     **/
    WorkStageResult createWorkStageResult();

    /**
     * 创建表作业操作
     * @Author huan
     * @Date 2020-02-26
     * @Param [tableInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createTableWorkStageHandler(TableInfo tableInfo);

    /**
     * 创建表流作业操作
     * @Author huan
     * @Date 2020-02-26
     * @Param [streamInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createStreamWorkStageHandler(StreamInfo streamInfo);

    /**
     * 创建SQL作业操作
     * @Author huan
     * @Date 2020-02-26
     * @Param [sqlInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createSQLWorkStageHandler(SQLInfo sqlInfo);

    /**
     * 创建组作业操作
     * @Author huan
     * @Date 2020-02-26
     * @Param [groupInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createGroupWorkStageHandler(GroupInfo groupInfo);

    /**
     * 创建输出作业操作
     * @Author huan
     * @Date 2020-02-26
     * @Param [outputInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createOutputWorkStageHandler(OutputInfo outputInfo);

    /**
     * 创建过滤作业操作
     * @Author huan
     * @Date 2020-03-13
     * @Param [filterInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createFilterWorkStageHandler(FilterInfo filterInfo);

    /**
     * 创建去重作业操作
     * @Author huan
     * @Date 2020-04-10
     * @Param [distinctInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createDistinctWorkStageHandler(DistinctInfo distinctInfo);

    /**
     * 创建排序作业操作
     * @Author huan
     * @Date 2020-03-16
     * @Param [orderInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createOrderWorkStageHandler(OrderInfo orderInfo);

    /**
     * 创建合并作业操作
     * @Author huan
     * @Date 2020-04-10
     * @Param [unionInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createUnionWorkStageHandler(UnionInfo unionInfo);

    /**
     * 创建连接作业操作
     * @Author huan
     * @Date 2020-04-05
     * @Param [joinInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createJoinWorkStageHandler(JoinInfo joinInfo);

    /**
     * 创建Schema作业操作
     * @Author huan
     * @Date 2020-04-10
     * @Param [schemaInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createSchemaWorkStageHandler(SchemaInfo schemaInfo);

    /**
     * 创建值映射作业操作
     * @Author huan
     * @Date 2020-04-08
     * @Param [valueMappersInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createValueMappersWorkStageHandler(ValueMappersInfo valueMappersInfo);

    /**
     * 创建列分隔转行作业操作
     * @Author huan
     * @Date 2020-04-15
     * @Param [splitFieldToRowsInfo]
     * @Return org.eocencle.magnet.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createSplitFieldToRowsWorkStageHandler(SplitFieldToRowsInfo splitFieldToRowsInfo);

    /**
     * 创建字符串切割作业操作
     * @Author huan
     * @Date 2020-04-24
     * @Param [stringCutsInfo]
     * @Return org.eocencle.magnet.core.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createStringCutsWorkStageHandler(StringCutsInfo stringCutsInfo);

    /**
     * 创建添加字段作业操作
     * @Author huan
     * @Date 2020-04-28
     * @Param [addFieldsInfo]
     * @Return org.eocencle.magnet.core.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createAddFieldsWorkStageHandler(AddFieldsInfo addFieldsInfo);

    /**
     * 创建添加序列作业操作
     * @Author huan
     * @Date 2020-04-30
     * @Param [addSequenceInfo]
     * @Return org.eocencle.magnet.core.component.WorkStageHandler
     * @Exception
     * @Description
     **/
    WorkStageHandler createAddSequenceWorkStageHandler(AddSequenceInfo addSequenceInfo);

    /**
     * 创建行号作业操作
     * @Author huan
     * @Date 2020-06-07
     * @Param [rowNumInfo]
     * @Return org.eocencle.magnet.core.component.WorkStageHandler
     * @Exception
     * @Description
     */
    WorkStageHandler createRowNumWorkStageHandler(RowNumInfo rowNumInfo);

    /**
     * 创建查询作业操作
     * @Author huan
     * @Date 2020-07-05
     * @Param [queryInfo]
     * @Return org.eocencle.magnet.core.component.WorkStageHandler
     * @Exception
     * @Description
     */
    WorkStageHandler createQueryWorkStageHandler(QueryInfo queryInfo);

    /**
     * 创建包装注册器
     * @Author huan
     * @Date 2020-04-18
     * @Param []
     * @Return org.eocencle.magnet.core.component.wrapper.WrapperRegister
     * @Exception
     * @Description
     **/
    WrapperRegister createWrapperRegister();
}
