package org.eocencle.magnet.spark1.context;

import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.component.wrapper.WrapperRegister;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.mapping.*;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.spark1.component.*;
import org.eocencle.magnet.spark1.component.handler.impl.SparkTextTableLoader;
import org.eocencle.magnet.spark1.component.handler.impl.*;
import org.eocencle.magnet.spark1.component.wrapper.SparkWrapperRegister;

/**
 * Spark环境工厂类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class Spark1ComponentFactory implements ComponentFactory {

    private static Spark1ComponentFactory FACTORY = new Spark1ComponentFactory();

    private Spark1ComponentFactory() {

    }

    /**
     * 单例获取工厂实例
     * @Author huan
     * @Date 2020-01-22
     * @Param []
     * @Return org.eocencle.magnet.context.factory.Spark1ComponentFactory
     * @Exception
     * @Description
     **/
    public static Spark1ComponentFactory getFactoryInstance() {
        return FACTORY;
    }

    @Override
    public Context getRuntimeContext() {
        return new Spark1Context();
    }

    @Override
    public TableWorkStage createTableWorkStageComponent() {
        return new SparkTableWorkStage();
    }

    @Override
    public StreamWorkStage createStreamWorkStageComponent() {
        return new SparkStreamWorkStage();
    }

    @Override
    public SQLWorkStage createSQLWorkStageComponent() {
        return new SparkSQLWorkStage();
    }

    @Override
    public GroupWorkStage createGroupWorkStageComponent() {
        return new SparkGroupWorkStage();
    }

    @Override
    public OutputWorkStage createOutputWorkStageComponent() {
        return new SparkOutputWorkStage();
    }

    @Override
    public FilterWorkStage createFilterWorkStageComponent() {
        return new SparkFilterWorkStage();
    }

    @Override
    public DistinctWorkStage createDistinctWorkStageComponent() {
        return new SparkDistinctWorkStage();
    }

    @Override
    public OrderWorkStage createOrderWorkStageComponent() {
        return new SparkOrderWorkStage();
    }

    @Override
    public UnionWorkStage createUnionWorkStageComponent() {
        return new SparkUnionWorkStage();
    }

    @Override
    public JoinWorkStage createJoinWorkStageComponent() {
        return new SparkJoinWorkStage();
    }

    @Override
    public SchemaWorkStage createSchemaWorkStageComponent() {
        return new SparkSchemaWorkStage();
    }

    @Override
    public ValueMappersWorkStage createValueMappersWorkStageComponent() {
        return new SparkValueMappersWorkStage();
    }

    @Override
    public SplitFieldToRowsWorkStage createSplitFieldToRowsWorkStageComponent() {
        return new SparkSplitFieldToRowsWorkStage();
    }

    @Override
    public StringCutsWorkStage createStringCutsWorkStageComponent() {
        return new SparkStringCutsWorkStage();
    }

    @Override
    public AddFieldsWorkStage createAddFieldsWorkStageComponent() {
        return new SparkAddFieldsWorkStage();
    }

    @Override
    public AddSequenceWorkStage createAddSequenceWorkStageComponent() {
        return new SparkAddSequenceWorkStage();
    }

    @Override
    public RowNumWorkStage createRowNumWorkStageComponent() {
        return new SparkRowNumWorkStage();
    }

    @Override
    public WorkStageResult createWorkStageResult() {
        return new SparkWorkStageResult();
    }

    @Override
    public WorkStageHandler createTableWorkStageHandler(TableInfo tableInfo) {
        if (CoreTag.TABLE_STYLE_DATABASE.equalsIgnoreCase(tableInfo.getStyle())) {
            return new SparkDataBaseTableLoader(tableInfo);
        } else {
            if (CoreTag.FILE_FORMAT_TEXTFILE.equalsIgnoreCase(tableInfo.getFormat())) {
                return new SparkTextTableLoader(tableInfo);
            } else if (CoreTag.FILE_FORMAT_AVROFILE.equalsIgnoreCase(tableInfo.getFormat())) {
                return new SparkAvroTableLoader(tableInfo);
            } else if (CoreTag.FILE_FORMAT_RCFILE.equalsIgnoreCase(tableInfo.getFormat())) {
                return new SparkRCTableLoader(tableInfo);
            } else if (CoreTag.FILE_FORMAT_ORCFILE.equalsIgnoreCase(tableInfo.getFormat())) {
                return new SparkORCTableLoader(tableInfo);
            } else if (CoreTag.FILE_FORMAT_PARQUETFILE.equalsIgnoreCase(tableInfo.getFormat())) {
                return new SparkParquetTableLoader(tableInfo);
            } else if (CoreTag.FILE_FORMAT_JSONFILE.equalsIgnoreCase(tableInfo.getFormat())) {
                return new SparkJsonTableLoader(tableInfo);
            }
        }
        throw new UnsupportedException("Table does not support " + tableInfo.getStyle() + " style!");
    }

    @Override
    public WorkStageHandler createStreamWorkStageHandler(StreamInfo streamInfo) {
        StrictMap<InfoParam> config = streamInfo.getConfig();
        if (config.containsKey(CoreTag.STREAM_CONFIG_DB)) {
            StrictMap<String> dbConfig = config.get(CoreTag.STREAM_CONFIG_DB).getMap();
            String dbDialect = dbConfig.get(CoreTag.DB_DIALECT);
            if (dbDialect.equalsIgnoreCase(CoreTag.DB_TYPE_MYSQL)) {

                KafkaOffsetSaveToMysql offsetSaveToMysql = new KafkaOffsetSaveToMysql();
                     offsetSaveToMysql.init(dbConfig.get(
                             CoreTag.DB_HOST),
                             dbConfig.get(CoreTag.DB_PORT),
                             dbConfig.get(CoreTag.DB_DATABASE),
                             dbConfig.get(CoreTag.DB_TABLE),
                             dbConfig.get(CoreTag.DB_USERNAME),
                             dbConfig.get(CoreTag.DB_PASSWORD));
                    return offsetSaveToMysql;
            } else {
                throw new UnsupportedException(dbDialect + " is not supported for offset storage!");
            }
        } else {

           return new KafkaOffsetDefaultManager();
        }
    }

    @Override
    public WorkStageHandler createSQLWorkStageHandler(SQLInfo sqlInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createGroupWorkStageHandler(GroupInfo groupInfo) {
        if (CoreTag.STREAM_STATE_NONE.equalsIgnoreCase(groupInfo.getStreamState())) {
            return new SparkDefaultGrouper();
        } else if (CoreTag.STREAM_STATE_STATE.equalsIgnoreCase(groupInfo.getStreamState())) {
            return new SparkStreamStateGrouper();
        } else if (CoreTag.STREAM_STATE_WINDOW.equalsIgnoreCase(groupInfo.getStreamState())) {
            return new SparkStreamWindowGrouper();
        }
        throw new UnsupportedException("Group does not support " + groupInfo.getStreamState() + " style!");
    }

    @Override
    public WorkStageHandler createOutputWorkStageHandler(OutputInfo outputInfo) {
        if (CoreTag.OUTPUT_STYLE_FILE.equalsIgnoreCase(outputInfo.getStyle())) {
            return new SparkFileOutputer();
        } else if (CoreTag.OUTPUT_STYLE_BRANCH.equalsIgnoreCase(outputInfo.getStyle())) {
            return new SparkBranchOutputer();
        } else if (CoreTag.OUTPUT_STYLE_EMAIL.equalsIgnoreCase(outputInfo.getStyle())) {
            return new SparkEmailOutputer();
        } else if (CoreTag.OUTPUT_STYLE_CONSOLE.equalsIgnoreCase(outputInfo.getStyle())) {
            return new SparkConsoleOutputer();
        } else if (CoreTag.TABLE_STYLE_DATABASE.equalsIgnoreCase(outputInfo.getStyle())) {
            return new SparkDataBaseOutputer();
        } else if (CoreTag.TABLE_STYLE_KAFKA.equalsIgnoreCase(outputInfo.getStyle())) {
            return new SparkKafkaOutputer();
        } else if (CoreTag.OUTPUT_STYLE_SAMPLE.equalsIgnoreCase(outputInfo.getStyle())) {
            return new SparkSampleOutputer();
        }
        throw new UnsupportedException("Output does not support " + outputInfo.getStyle() + " style!");
    }

    @Override
    public WorkStageHandler createFilterWorkStageHandler(FilterInfo filterInfo) {
        return new SparkDefaultFilterCondition();
    }

    @Override
    public WorkStageHandler createDistinctWorkStageHandler(DistinctInfo distinctInfo) {
        return new SparkDistinctDefaultHandler();
    }

    @Override
    public WorkStageHandler createOrderWorkStageHandler(OrderInfo orderInfo) {
        return new SparkDefaultOrder();
    }

    @Override
    public WorkStageHandler createUnionWorkStageHandler(UnionInfo unionInfo) {
        return null;
    }

    @Override
    public WorkStageHandler createJoinWorkStageHandler(JoinInfo joinInfo) {
        return new SparkDefaultOnCondition();
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
        return new SparkDefaultSplitFieldToRowsHandler();
    }

    @Override
    public WorkStageHandler createStringCutsWorkStageHandler(StringCutsInfo stringCutsInfo) {
        return new SparkStringCutsDefaultHandler();
    }

    @Override
    public WorkStageHandler createAddFieldsWorkStageHandler(AddFieldsInfo addFieldsInfo) {
        return new SparkAddFieldsDefaultHandler();
    }

    @Override
    public WorkStageHandler createAddSequenceWorkStageHandler(AddSequenceInfo addSequenceInfo) {
        return new SparkAddSequenceDefaultHandler();
    }

    @Override
    public WorkStageHandler createRowNumWorkStageHandler(RowNumInfo rowNumInfo) {
        return new SparkDefaultRowNumHandler();
    }

    @Override
    public WrapperRegister createWrapperRegister() {
        return new SparkWrapperRegister();
    }
}
