package org.eocencle.magnet.spark1.component;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.mapping.InfoParam;
import org.eocencle.magnet.core.mapping.ValueMappersInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.spark1.util.SparkUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spark值映射作业节点类
 * @author: huan
 * @Date: 2020-04-08
 * @Description:
 */
public class SparkValueMappersWorkStage extends ValueMappersWorkStage implements Serializable {
    // 值映射参数
    private StrictMap<StrictMap<InfoParam>> valMaps = new StrictMap<>("Value mappers");
    private StrictMap<StrictMap<InfoParam>> valTagMaps = new StrictMap<>("Value tagField mappers");
    private StrictMap<String> valFieldNonMatchMaps = new StrictMap<>("Value field not match value mappers");
    private StrictMap<String> valTagFieldNonMatchMaps = new StrictMap<>("Value tag field not match value mappers");
    private StrictMap<String> tagField2FieldMaps = new StrictMap<>("Value tagField to field mappers");
    private StrictMap<DataSourceField> dataSourceFieldStrictMap = new StrictMap<>("date source fields Maps");

    @Override
    public void initData(WorkStageInfo info) {
        super.initData(info);
        List<ValueMappersInfo.ValueMapper> valueMappers = this.valueMappersInfo.getValueMappers();
        for (ValueMappersInfo.ValueMapper mapper: valueMappers) {
            this.valMaps.put(mapper.getField(), mapper.getValMappers());
            if (mapper.getTagField() != null) {
                this.valTagMaps.put(mapper.getTagField(), mapper.getValMappers());
                this.valTagFieldNonMatchMaps.put(mapper.getTagField(), mapper.getNonMatch());
                this.tagField2FieldMaps.put(mapper.getTagField(),mapper.getField());
            }else{
                this.valFieldNonMatchMaps.put(mapper.getField(),mapper.getNonMatch());
            }
        }
    }

    @Override
    public void initHandler(WorkStageHandler handler) {
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        SparkWorkStageResult prevResult =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.valueMappersInfo.getRef());

        HashMap<Integer,String> oldRddIndexWithFieldMap = new HashMap<>();
        StructType schema = prevResult.getDf().schema();
        StructField[] fields = schema.fields();
        for (int i = 0; i < fields.length; i ++) {
            dataSourceFieldStrictMap.put(fields[i].name(),new DataSourceField(fields[i].name(),fields[i].dataType().typeName()));
            for (Map.Entry<String, String> stringStringEntry : this.valFieldNonMatchMaps.entrySet()) {
                String field = stringStringEntry.getKey();
                if (fields[i].name().equals(field)) {
                    oldRddIndexWithFieldMap.put(i,field);
                }
            }
        }
        for (Map.Entry<String, String> stringStringEntry : this.valTagFieldNonMatchMaps.entrySet()) {
            String key = stringStringEntry.getKey();
            //这里先写成string类型，后期可能会加入类型标示
            dataSourceFieldStrictMap.put(key,new DataSourceField(key,"string"));
        }

        JavaRDD<Row> rdd = prevResult.getRdd().map((Row row) -> {
            Object[] rowList = new Object[dataSourceFieldStrictMap.size()];

            for (int i = 0; i < row.length(); i++) {
                rowList[i] = row.get(i);
            }
            int i = row.length();
            for (Map.Entry<String, StrictMap<InfoParam>> stringStrictMapEntry : this.valTagMaps.entrySet()) {
                String key = stringStrictMapEntry.getKey(); //name
                StrictMap<InfoParam> value = stringStrictMapEntry.getValue(); //a->a1 b->b1
                //新字段
                if (valTagFieldNonMatchMaps.containsKey(key)){
                    Object o = row.getAs(tagField2FieldMaps.get(key));
                    if (value.containsKey(o)) {
                        //如果有，则需要加入row后面
                        rowList[i] = this.valTagMaps.get(key).get(o).getValue();
                    } else {
                        //默认值
                        rowList[i] = this.valTagFieldNonMatchMaps.getOrDefault(key,"");
                    }
                    i++;
                }
            }
            for (int j = 0; j < row.length(); j++) {
                if (oldRddIndexWithFieldMap.containsKey(j)) {
                    for (Map.Entry<String, StrictMap<InfoParam>> stringStrictMapEntry : this.valMaps.entrySet()) {
                        String fieldName = oldRddIndexWithFieldMap.get(j);
                        String key = stringStrictMapEntry.getKey(); //name
                        StrictMap<InfoParam> value = stringStrictMapEntry.getValue(); //a->a1 b->b1
                        if (key.equals(fieldName)) {
                            Object o = rowList[j];
                            System.out.println(o+"----");
                            if (value.containsKey(o) && value.get(o).getKey().equals(o)) {
                                //如果有，则需要加入row后面
                                rowList[j] = this.valMaps.get(key).get(o).getValue();
                            } else {
                                //默认值
                                rowList[j] = this.valTagFieldNonMatchMaps.getOrDefault(key,"");
                            }
                        }
                    }
                }
            }
            return RowFactory.create(rowList);
        });

        DataFrame dataFrame = SparkUtil.createDataFrame((SQLContext) parameter.getContext().getSQLContext(),
            dataSourceFieldStrictMap, rdd);

        JavaRDD<Row> javaRDD = dataFrame.toJavaRDD();

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.valueMappersInfo.getId());
        result.setAlias(this.valueMappersInfo.getAlias());
        result.setRdd(javaRDD);
        result.setDf(dataFrame);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}
