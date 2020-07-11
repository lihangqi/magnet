package org.eocencle.magnet.flink1.context;

import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.component.wrapper.WrapperRegister;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.mapping.*;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.flink1.component.FlinkTableWorkStage;
import org.eocencle.magnet.flink1.component.FlinkWorkStageResult;
import org.eocencle.magnet.flink1.component.handler.impl.FlinkTextTableLoadHandler;
import org.eocencle.magnet.flink1.component.wrapper.FlinkWrapperRegister;

/**
 * Flink1环境工厂类
 * @author: huan
 * @Date: 2020-05-24
 * @Description:
 */
public class Flink1ComponentFactory implements ComponentFactory {

    private static Flink1ComponentFactory FACTORY = new Flink1ComponentFactory();

    private Flink1ComponentFactory() {

    }

    /**
     * 单例获取工厂实例
     * @Author huan
     * @Date 2020-06-09
     * @Param []
     * @Return org.eocencle.magnet.flink1.context.Flink1ComponentFactory
     * @Exception
     * @Description
     */
    public static Flink1ComponentFactory getFactoryInstance() {
        return FACTORY;
    }

    @Override
    public Context getRuntimeContext() {
        return new Flink1Context();
    }

    @Override
    public TableWorkStage createTableWorkStageComponent() {
        return new FlinkTableWorkStage();
    }

    @Override
    public StreamWorkStage createStreamWorkStageComponent() {
        return null;
    }

    @Override
    public StreamDataLoadWorkStage createStreamDataLoadWorkStageComponent() {
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
    public RowNumWorkStage createRowNumWorkStageComponent() {
        return null;
    }

    @Override
    public QueryWorkStage createQueryWorkStageComponent() {
        return null;
    }

    @Override
    public WorkStageResult createWorkStageResult() {
        return new FlinkWorkStageResult();
    }

    @Override
    public WorkStageHandler createTableWorkStageHandler(TableInfo tableInfo) {
        if (CoreTag.FILE_FORMAT_TEXTFILE.equalsIgnoreCase(tableInfo.getFormat())) {
            return new FlinkTextTableLoadHandler();
        }
        throw new UnsupportedException("Table does not support " + tableInfo.getStyle() + " style!");
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
    public WorkStageHandler createRowNumWorkStageHandler(RowNumInfo rowNumInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createQueryWorkStageHandler(QueryInfo queryInfo) {
        return null;
    }

    @Override
    public WrapperRegister createWrapperRegister() {
        return new FlinkWrapperRegister();
    }
}
