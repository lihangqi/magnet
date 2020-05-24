package org.eocencle.magnet.flink1.context;

import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.component.wrapper.WrapperRegister;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.mapping.*;

/**
 * Flink1环境工厂类
 * @author: huan
 * @Date: 2020-05-24
 * @Description:
 */
public class Flink1ComponentFactory implements ComponentFactory {
    @Override
    public Context getRuntimeContext() {
        return null;
    }

    @Override
    public TableWorkStage createTableWorkStageComponent() {
        return null;
    }

    @Override
    public StreamWorkStage createStreamWorkStageComponent() {
        return null;
    }

    @Override
    public SQLWorkStage createSQLWorkStageComponent() {
        return null;
    }

    @Override
    public GroupWorkStage createGroupWorkStageComponent() {
        return null;
    }

    @Override
    public OutputWorkStage createOutputWorkStageComponent() {
        return null;
    }

    @Override
    public FilterWorkStage createFilterWorkStageComponent() {
        return null;
    }

    @Override
    public DistinctWorkStage createDistinctWorkStageComponent() {
        return null;
    }

    @Override
    public OrderWorkStage createOrderWorkStageComponent() {
        return null;
    }

    @Override
    public UnionWorkStage createUnionWorkStageComponent() {
        return null;
    }

    @Override
    public JoinWorkStage createJoinWorkStageComponent() {
        return null;
    }

    @Override
    public SchemaWorkStage createSchemaWorkStageComponent() {
        return null;
    }

    @Override
    public ValueMappersWorkStage createValueMappersWorkStageComponent() {
        return null;
    }

    @Override
    public SplitFieldToRowsWorkStage createSplitFieldToRowsWorkStageComponent() {
        return null;
    }

    @Override
    public StringCutsWorkStage createStringCutsWorkStageComponent() {
        return null;
    }

    @Override
    public AddFieldsWorkStage createAddFieldsWorkStageComponent() {
        return null;
    }

    @Override
    public AddSequenceWorkStage createAddSequenceWorkStageComponent() {
        return null;
    }

    @Override
    public WorkStageResult createWorkStageResult() {
        return null;
    }

    @Override
    public WorkStageHandler createTableWorkStageHandler(TableInfo tableInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createStreamWorkStageHandler(StreamInfo streamInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createSQLWorkStageHandler(SQLInfo sqlInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createGroupWorkStageHandler(GroupInfo groupInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createOutputWorkStageHandler(OutputInfo outputInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createFilterWorkStageHandler(FilterInfo filterInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createDistinctWorkStageHandler(DistinctInfo distinctInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createOrderWorkStageHandler(OrderInfo orderInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createUnionWorkStageHandler(UnionInfo unionInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createJoinWorkStageHandler(JoinInfo joinInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createSchemaWorkStageHandler(SchemaInfo schemaInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createValueMappersWorkStageHandler(ValueMappersInfo valueMappersInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createSplitFieldToRowsWorkStageHandler(SplitFieldToRowsInfo splitFieldToRowsInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createStringCutsWorkStageHandler(StringCutsInfo stringCutsInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createAddFieldsWorkStageHandler(AddFieldsInfo addFieldsInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createAddSequenceWorkStageHandler(AddSequenceInfo addSequenceInfo) {
        return null;
    }

    @Override
    public WrapperRegister createWrapperRegister() {
        return null;
    }
}
