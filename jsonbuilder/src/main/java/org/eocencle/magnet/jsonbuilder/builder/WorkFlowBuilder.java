package org.eocencle.magnet.jsonbuilder.builder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.mapping.*;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.jsonbuilder.session.JsonProjectConfig;
import org.eocencle.magnet.jsonbuilder.util.JSONBuilderTag;

import java.util.Iterator;

/**
 * 工作流建构类
 * @author: huan
 * @Date: 2020-05-31
 * @Description:
 */
public class WorkFlowBuilder implements JSONParser {
    // 单例实体
    private static WorkFlowBuilder BUILDER = new WorkFlowBuilder();

    private WorkFlowBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-05-31
     * @Param []
     * @Return org.eocencle.magnet.jsonbuilder.builder.WorkFlowBuilder
     * @Exception
     * @Description
     */
    public static WorkFlowBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(Object parser, JsonProjectConfig config) {
        JSONObject jsonObejct = (JSONObject) parser;
        JSONArray paramArray = jsonObejct.getJSONArray(JSONBuilderTag.JSON_ATTR_WORKFLOW);

        Iterator<Object> iterator = paramArray.iterator();
        JSONObject jsonObj = null;
        String type = null;
        while (iterator.hasNext()) {
            jsonObj = (JSONObject) iterator.next();
            type = jsonObj.getString(JSONBuilderTag.JSON_ATTR_TYPE);
            if (JSONBuilderTag.WORKFLOW_TYPE_SQL.equalsIgnoreCase(type)) {
                this.parseSQLElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_OUTPUT.equalsIgnoreCase(type)) {
                this.parseOutputElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_GROUP.equalsIgnoreCase(type)) {
                this.parseGroupElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_FILTER.equalsIgnoreCase(type)) {
                this.parseFilterElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_DISTINCT.equalsIgnoreCase(type)) {
                this.parseDistinctElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_ORDER.equalsIgnoreCase(type)) {
                this.parseOrderElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_UNION.equalsIgnoreCase(type)) {
                this.parseUnionElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_JOIN.equalsIgnoreCase(type)) {
                this.parseJoinElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_SCHEMA.equalsIgnoreCase(type)) {
                this.parseSchemaElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_VALUEMAPPERS.equalsIgnoreCase(type)) {
                this.parseValueMappersElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_SPLITFIELDTOROWS.equalsIgnoreCase(type)) {
                this.parseSplitFieldToRowsElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_STRINGCUTS.equalsIgnoreCase(type)) {
                this.parseStringCutsElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_ADDFIELDS.equalsIgnoreCase(type)) {
                this.parseAddFieldsElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_ADDSEQUENCE.equalsIgnoreCase(type)) {
                this.parseAddSequenceElements(jsonObj, config);
            } else if (JSONBuilderTag.WORKFLOW_TYPE_ROWNUM.equalsIgnoreCase(type)) {
                this.parseRowNumElements(jsonObj, config);
            } else {
                throw new UnsupportedException(type + " component is not supported");
            }
        }

    }
    
    /**
     * 解析SQL元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception 
     * @Description 
     */
    private void parseSQLElements(JSONObject jsonObj, JsonProjectConfig config) {
        SQLInfo sqlInfo = new SQLInfo();
        sqlInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        sqlInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        sqlInfo.setSql(jsonObj.getString(JSONBuilderTag.JSON_ATTR_SQL));
        config.putWorkFlowInfo(sqlInfo.getId(), sqlInfo);
    }

    /**
     * 解析输出元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseOutputElements(JSONObject jsonObj, JsonProjectConfig config) {
        OutputInfo outputInfo = new OutputInfo();
        outputInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        outputInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));
        String style = jsonObj.getString(JSONBuilderTag.JSON_ATTR_STYLE);
        if (null == style) {
            outputInfo.setStyle(JSONBuilderTag.OUTPUT_STYLE_FILE);
        } else {
            outputInfo.setStyle(style);
        }
        outputInfo.setTarget(jsonObj.getString(JSONBuilderTag.JSON_ATTR_TARGET));
        String type = jsonObj.getString(JSONBuilderTag.JSON_ATTR_TYPE);
        if (null == type) {
            outputInfo.setType(JSONBuilderTag.OUTPUT_TYPE_CREATE);
        } else {
            outputInfo.setType(type);
        }
        String compress = jsonObj.getString(JSONBuilderTag.JSON_ATTR_COMPRESS);
        if (null == compress) {
            outputInfo.setCompress(JSONBuilderTag.COMPRESS_NONE);
        } else {
            outputInfo.setCompress(compress);
        }
        String separator = jsonObj.getString(JSONBuilderTag.JSON_ATTR_SEPARATOR);
        if (null == separator) {
            outputInfo.setSeparator(JSONBuilderTag.SPLIT_INVISIBLE1);
        } else {
            outputInfo.setSeparator(separator);
        }

        config.putWorkFlowInfo(outputInfo.getId(), outputInfo);
    }

    /**
     * 解析分组元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseGroupElements(JSONObject jsonObj, JsonProjectConfig config) {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        groupInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        groupInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));
        String field = jsonObj.getString(JSONBuilderTag.JSON_ATTR_FIELD);
        String[] fields = field.trim().split(JSONBuilderTag.SPLIT_COMMA);
        for (String val: fields) {
            groupInfo.addGroupField(new GroupInfo.GroupField(val.trim()));
        }

        String[] orders = jsonObj.getString(JSONBuilderTag.JSON_ATTR_ORDER).split(CoreTag.SPLIT_COMMA);
        String[] key = null;
        for (String order: orders) {
            key = order.trim().split(CoreTag.SPLIT_BLANK);
            if (2 == key.length) {
                groupInfo.addOrderField(new GroupInfo.OrderField(key[0].trim(), key[1].trim()));
            } else {
                groupInfo.addOrderField(new GroupInfo.OrderField(key[0].trim()));
            }
        }

        groupInfo.setRownumField(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ROWNUM));
        groupInfo.setStreamState(jsonObj.getString(JSONBuilderTag.JSON_ATTR_STREAM_STATE));

        config.putWorkFlowInfo(groupInfo.getId(), groupInfo);
    }

    /**
     * 解析过滤元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseFilterElements(JSONObject jsonObj, JsonProjectConfig config) {
        FilterInfo filterInfo = new FilterInfo();
        filterInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        filterInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        filterInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));
        JSONArray conditions = jsonObj.getJSONArray(JSONBuilderTag.JSON_ATTR_CONDITIONS);

        if (null != conditions) {
            Iterator<Object> iterator = conditions.iterator();
            JSONObject cond = null;
            FilterInfo.FilterField filterField = null;
            String join = null;
            while (iterator.hasNext()) {
                filterField = new FilterInfo.FilterField();
                cond = (JSONObject) iterator.next();
                join = cond.getString(JSONBuilderTag.JSON_ATTR_JOIN);
                if (null == join) {
                    filterField.setJoin(JSONBuilderTag.FILTER_JOIN_AND);
                } else {
                    filterField.setJoin(join);
                }
                filterField.setType(cond.getString(JSONBuilderTag.JSON_ATTR_TYPE));
                filterField.setField(cond.getString(JSONBuilderTag.JSON_ATTR_FIELD));
                filterField.setValue(cond.getString(JSONBuilderTag.JSON_ATTR_VALUE));
                filterField.setStart(cond.getString(JSONBuilderTag.JSON_ATTR_START));
                filterField.setEnd(cond.getString(JSONBuilderTag.JSON_ATTR_END));

                filterInfo.addFilterFields(filterField);
            }
        }

        config.putWorkFlowInfo(filterInfo.getId(), filterInfo);
    }

    /**
     * 解析去重元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseDistinctElements(JSONObject jsonObj, JsonProjectConfig config) {
        DistinctInfo distinctInfo = new DistinctInfo();
        distinctInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        distinctInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        distinctInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));
        JSONArray distincts = jsonObj.getJSONArray(JSONBuilderTag.JSON_ATTR_CONDITIONS);

        if (null != distincts) {
            Iterator<Object> iterator = distincts.iterator();
            JSONObject cond = null;
            DistinctInfo.DistinctField distinctField = null;
            String join = null;
            while (iterator.hasNext()) {
                distinctField = new DistinctInfo.DistinctField();
                cond = (JSONObject) iterator.next();
                distinctField.setField(cond.getString(JSONBuilderTag.JSON_ATTR_FIELD));
                join = cond.getString(JSONBuilderTag.JSON_ATTR_IGNORECASE);
                if (null == join) {
                    distinctField.setIgnoreCase(JSONBuilderTag.FALSE);
                } else {
                    distinctField.setIgnoreCase(join);
                }

                distinctInfo.addDistinctFields(distinctField);
            }
        }

        config.putWorkFlowInfo(distinctInfo.getId(), distinctInfo);
    }

    /**
     * 解析排序元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseOrderElements(JSONObject jsonObj, JsonProjectConfig config) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        orderInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        orderInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));
        orderInfo.setField(jsonObj.getString(JSONBuilderTag.JSON_ATTR_FIELD));

        config.putWorkFlowInfo(orderInfo.getId(), orderInfo);
    }

    /**
     * 解析合并元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseUnionElements(JSONObject jsonObj, JsonProjectConfig config) {
        UnionInfo unionInfo = new UnionInfo();
        unionInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        unionInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        unionInfo.setRefs(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REFS));

        config.putWorkFlowInfo(unionInfo.getId(), unionInfo);
    }

    /**
     * 解析关联元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseJoinElements(JSONObject jsonObj, JsonProjectConfig config) {

    }

    /**
     * 解析Schema元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseSchemaElements(JSONObject jsonObj, JsonProjectConfig config) {
        SchemaInfo schemaInfo = new SchemaInfo();
        schemaInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        schemaInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));

        config.putWorkFlowInfo(schemaInfo.getId(), schemaInfo);
    }

    /**
     * 解析值映射元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseValueMappersElements(JSONObject jsonObj, JsonProjectConfig config) {
        ValueMappersInfo valueMappersInfo = new ValueMappersInfo();
        valueMappersInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        valueMappersInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        valueMappersInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));
        JSONArray valueMappers = jsonObj.getJSONArray(JSONBuilderTag.JSON_ATTR_VALUEMAPPERS);

        if (null != valueMappers) {
            Iterator<Object> mvIt = valueMappers.iterator();
            JSONObject vmJSON = null;
            ValueMappersInfo.ValueMapper valueMapper = null;
            JSONArray mappers = null;
            Iterator<Object> mapperIt = null;
            JSONObject mapperJSON = null;
            InfoParam mapper = null;
            while (mvIt.hasNext()) {
                vmJSON = (JSONObject) mvIt.next();
                valueMapper = new ValueMappersInfo.ValueMapper();
                valueMapper.setField(vmJSON.getString(JSONBuilderTag.JSON_ATTR_FIELD));
                valueMapper.setTagField(vmJSON.getString(JSONBuilderTag.JSON_ATTR_TAGFIELD));
                valueMapper.setNonMatch(vmJSON.getString(JSONBuilderTag.JSON_ATTR_NONMATCH));

                mappers = vmJSON.getJSONArray(JSONBuilderTag.JSON_ATTR_MAPPERS);
                if (null != mappers) {
                    mapperIt = mappers.iterator();
                    while (mapperIt.hasNext()) {
                        mapperJSON = (JSONObject) mapperIt.next();
                        mapper = new InfoParam(mapperJSON.getString(JSONBuilderTag.JSON_ATTR_SOURCE), 
                                mapperJSON.getString(JSONBuilderTag.JSON_ATTR_TARGET));
                        valueMapper.putValMapper(mapper);
                    }
                }

                valueMappersInfo.addValueMapper(valueMapper);
            }
        }

        config.putWorkFlowInfo(valueMappersInfo.getId(), valueMappersInfo);
    }

    /**
     * 解析列分隔转行元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseSplitFieldToRowsElements(JSONObject jsonObj, JsonProjectConfig config) {
        SplitFieldToRowsInfo splitFieldToRowsInfo = new SplitFieldToRowsInfo();
        splitFieldToRowsInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        splitFieldToRowsInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        splitFieldToRowsInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));
        splitFieldToRowsInfo.setField(jsonObj.getString(JSONBuilderTag.JSON_ATTR_FIELD));
        splitFieldToRowsInfo.setSeparator(jsonObj.getString(JSONBuilderTag.JSON_ATTR_SEPARATOR));
        String isRegex = jsonObj.getString(JSONBuilderTag.JSON_ATTR_ISREGEX);
        if (null == isRegex) {
            splitFieldToRowsInfo.setIsRegex(JSONBuilderTag.FALSE);
        } else {
            splitFieldToRowsInfo.setIsRegex(isRegex);
        }
        splitFieldToRowsInfo.setTagField(jsonObj.getString(JSONBuilderTag.JSON_ATTR_TAGFIELD));
        splitFieldToRowsInfo.setRowNumField(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ROWNUMFIELD));

        config.putWorkFlowInfo(splitFieldToRowsInfo.getId(), splitFieldToRowsInfo);
    }

    /**
     * 解析字符串切割元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseStringCutsElements(JSONObject jsonObj, JsonProjectConfig config) {
        StringCutsInfo stringCutsInfo = new StringCutsInfo();
        stringCutsInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        stringCutsInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        stringCutsInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));
        JSONArray stringCuts = jsonObj.getJSONArray(JSONBuilderTag.JSON_ATTR_STRINGCUTS);

        if (null != stringCuts) {
            Iterator<Object> iterator = stringCuts.iterator();
            JSONObject cutObj = null;
            StringCutsInfo.StringCut stringCut = null;
            while (iterator.hasNext()) {
                cutObj = (JSONObject) iterator.next();
                stringCut = new StringCutsInfo.StringCut();
                stringCut.setField(cutObj.getString(JSONBuilderTag.JSON_ATTR_FIELD));
                stringCut.setTagField(cutObj.getString(JSONBuilderTag.JSON_ATTR_TAGFIELD));
                stringCut.setStart(cutObj.getString(JSONBuilderTag.JSON_ATTR_START));
                stringCut.setEnd(cutObj.getString(JSONBuilderTag.JSON_ATTR_END));

                stringCutsInfo.addStringCut(stringCut);
            }
        }

        config.putWorkFlowInfo(stringCutsInfo.getId(), stringCutsInfo);
    }

    /**
     * 解析添加字段元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseAddFieldsElements(JSONObject jsonObj, JsonProjectConfig config) {
        AddFieldsInfo addFieldsInfo = new AddFieldsInfo();
        addFieldsInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        addFieldsInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        addFieldsInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));
        JSONArray addFields = jsonObj.getJSONArray(JSONBuilderTag.JSON_ATTR_ADDFIELDS);

        if (null != addFields) {
            Iterator<Object> iterator = addFields.iterator();
            JSONObject addFieldObj = null;
            AddFieldsInfo.AddField addField = null;
            while (iterator.hasNext()) {
                addFieldObj = (JSONObject) iterator.next();
                addField = new AddFieldsInfo.AddField();
                addField.setName(addFieldObj.getString(JSONBuilderTag.JSON_ATTR_NAME));
                String type = addFieldObj.getString(JSONBuilderTag.JSON_ATTR_TYPE);
                if (null == type) {
                    addField.setType(CoreTag.TABLE_FIELD_TYPE_STRING);
                } else {
                    addField.setType(type);
                }
                addField.setPrecision(addFieldObj.getString(JSONBuilderTag.JSON_ATTR_PRECISION));
                String format = addFieldObj.getString(JSONBuilderTag.JSON_ATTR_FORMAT);
                if (null == format) {
                    addField.setFormat("yyyy-MM-dd HH:mm:ss");
                } else {
                    addField.setFormat(format);
                }
                addField.setValue(addFieldObj.getString(JSONBuilderTag.JSON_ATTR_VALUE));

                addFieldsInfo.addAddField(addField);
            }
        }

        config.putWorkFlowInfo(addFieldsInfo.getId(), addFieldsInfo);
    }

    /**
     * 解析添加序列元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseAddSequenceElements(JSONObject jsonObj, JsonProjectConfig config) {
        AddSequenceInfo addSequenceInfo = new AddSequenceInfo();
        addSequenceInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        addSequenceInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        addSequenceInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));
        addSequenceInfo.setTagField(jsonObj.getString(JSONBuilderTag.JSON_ATTR_TAGFIELD));
        String init = jsonObj.getString(JSONBuilderTag.JSON_ATTR_INIT);
        if (null == init) {
            addSequenceInfo.setInit("1");
        } else {
            addSequenceInfo.setInit(init);
        }
        String step = jsonObj.getString(JSONBuilderTag.JSON_ATTR_STEP);
        if (null == step) {
            addSequenceInfo.setStep("1");
        } else {
            addSequenceInfo.setStep(step);
        }

        config.putWorkFlowInfo(addSequenceInfo.getId(), addSequenceInfo);
    }

    /**
     * 解析行号元素
     * @Author huan
     * @Date 2020-06-13
     * @Param [jsonObj, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseRowNumElements(JSONObject jsonObj, JsonProjectConfig config) {
        RowNumInfo rowNumInfo = new RowNumInfo();
        rowNumInfo.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
        rowNumInfo.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
        rowNumInfo.setRef(jsonObj.getString(JSONBuilderTag.JSON_ATTR_REF));
        String field = jsonObj.getString(JSONBuilderTag.JSON_ATTR_FIELD);
        if (null == field) {
            rowNumInfo.setField(JSONBuilderTag.ROWNUM_DEFAULT_FIELD);
        } else {
            rowNumInfo.setField(field);
        }

        config.putWorkFlowInfo(rowNumInfo.getId(), rowNumInfo);
    }
}
