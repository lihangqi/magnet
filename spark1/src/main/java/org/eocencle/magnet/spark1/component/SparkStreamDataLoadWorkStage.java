package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.spark1.util.SparkUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * spark流数据加载作业节点类
 * @author: huan
 * @Date: 2020-06-27
 * @Description:
 */
public class SparkStreamDataLoadWorkStage extends StreamDataLoadWorkStage {

    @Override
    public void initHandler(WorkStageHandler handler) {

    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        JavaRDD<Row> rdd = null;
        DataFrame df = null;
        String format = this.streamInfo.getFormat();
        SQLContext sqlContext = (SQLContext) parameter.getContext().getSQLContext();

        JavaRDD<String> receive = ((SparkStreamReceiveWorkStageResult) this.getParent().getStreamBatchResult()).getLines();

        if (CoreTag.FILE_FORMAT_TEXTFILE.equalsIgnoreCase(format)) {
            rdd = SparkUtil.createRDD(receive, this.streamInfo.getSeparator(), this.streamInfo.getFields());
            df = SparkUtil.createDataFrame(sqlContext, this.streamInfo.getFields(), rdd);
        } else if (CoreTag.FILE_FORMAT_JSONFILE.equalsIgnoreCase(format)) {
            df = createDataFrame(sqlContext, receive);
            rdd = df.toJavaRDD();
        } else {
            throw new UnsupportedException(format + " file format is not supported!");
        }

        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.streamInfo.getId());
        result.setAlias(this.streamInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);

        // 保存流数据源
        this.getParent().setStreamBatchResult(result);

        return list;
    }

    /**
     * JSON数据解析
     * @Author huan
     * @Date 2020-06-27
     * @Param [sqlContext, src]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     */
    private DataFrame createDataFrame(SQLContext sqlContext, JavaRDD<String> src) {
        DataFrame df = sqlContext.read().json(src);
        StructType schema = df.schema();
        StrictMap<StructField> fieldStrictMap = new StrictMap<>("JSON fields");
        for (StructField field: schema.fields()) {
            fieldStrictMap.put(field.name(), field);
        }

        List<StructField> structFields = new ArrayList<>();
        StrictMap<DataSourceField> targetFields = this.streamInfo.getFields();
        for (Map.Entry<String, DataSourceField> targetField: targetFields.entrySet()) {
            structFields.add(fieldStrictMap.get(targetField.getValue().getName()));
        }

        JavaRDD<Row> rdd = df.toJavaRDD().map((Row row) -> {
            Object[] fields = new Object[targetFields.size()];
            int i = 0;
            for (Map.Entry<String, DataSourceField> targetField : targetFields.entrySet()) {
                fields[i] = row.get(row.fieldIndex(targetField.getValue().getName()));
                i++;
            }
            return RowFactory.create(fields);
        });

        return sqlContext.createDataFrame(rdd, DataTypes.createStructType(structFields));
    }
}
