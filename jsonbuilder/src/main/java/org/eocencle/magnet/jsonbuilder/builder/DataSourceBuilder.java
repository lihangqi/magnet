package org.eocencle.magnet.jsonbuilder.builder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.mapping.StreamInfo;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.jsonbuilder.session.JsonProjectConfig;
import org.eocencle.magnet.jsonbuilder.util.JSONBuilderTag;

import java.util.Iterator;

/**
 * 数据源建构类
 * @author: huan
 * @Date: 2020-05-31
 * @Description:
 */
public class DataSourceBuilder implements JSONParser {
    // 单例实体
    private static DataSourceBuilder BUILDER = new DataSourceBuilder();

    private DataSourceBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-05-30
     * @Param []
     * @Return org.eocencle.magnet.jsonbuilder.builder.DataSourceBuilder
     * @Exception
     * @Description
     */
    public static DataSourceBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(Object parser, JsonProjectConfig config) {
        JSONObject jsonObejct = (JSONObject) parser;
        JSONArray paramArray = jsonObejct.getJSONArray(JSONBuilderTag.JSON_ATTR_DATASOURCE);

        Iterator<Object> iterator = paramArray.iterator();
        JSONObject jsonObj = null;
        String type = null;
        TableInfo table = null;
        StreamInfo stream = null;
        while (iterator.hasNext()) {
            jsonObj = (JSONObject) iterator.next();
            type = jsonObj.getString(JSONBuilderTag.JSON_ATTR_TYPE);
            if (JSONBuilderTag.DATASOURCE_TYPE_TABLE.equalsIgnoreCase(type)) {
                table = new TableInfo();
                table.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
                table.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
                table.setStyle(jsonObj.getString(JSONBuilderTag.JSON_ATTR_STYLE));
                table.setSrc(jsonObj.getString(JSONBuilderTag.JSON_ATTR_SRC));
                table.setFormat(jsonObj.getString(JSONBuilderTag.JSON_ATTR_FORMAT));
                table.setSeparator(jsonObj.getString(JSONBuilderTag.JSON_ATTR_SEPARATOR));

                JSONArray fieldsArr = jsonObj.getJSONArray(JSONBuilderTag.JSON_ATTR_FIELDS);
                Iterator<Object> fieldsIt = fieldsArr.iterator();
                while (fieldsIt.hasNext()) {
                    JSONObject fieldObj = (JSONObject) fieldsIt.next();
                    table.addField(new DataSourceField(fieldObj.getString(JSONBuilderTag.JSON_ATTR_NAME),
                            fieldObj.getString(JSONBuilderTag.JSON_ATTR_TYPE),
                            fieldObj.getString(JSONBuilderTag.JSON_ATTR_PRECISION),
                            fieldObj.getString(JSONBuilderTag.JSON_ATTR_FORMAT)));
                }

                config.putDataSourceInfo(table.getId(), table);
            } else if (JSONBuilderTag.DATASOURCE_TYPE_STREAM.equalsIgnoreCase(type)) {
                stream = new StreamInfo();
                stream.setId(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ID));
                stream.setAlias(jsonObj.getString(JSONBuilderTag.JSON_ATTR_ALIAS));
                stream.setFormat(jsonObj.getString(JSONBuilderTag.JSON_ATTR_FORMAT));
                stream.setSeparator(jsonObj.getString(JSONBuilderTag.JSON_ATTR_SEPARATOR));

                JSONArray fieldsArr = jsonObj.getJSONArray(JSONBuilderTag.JSON_ATTR_FIELDS);
                Iterator<Object> fieldsIt = fieldsArr.iterator();
                while (fieldsIt.hasNext()) {
                    JSONObject fieldObj = (JSONObject) fieldsIt.next();
                    stream.addField(new DataSourceField(fieldObj.getString(JSONBuilderTag.JSON_ATTR_NAME),
                            fieldObj.getString(JSONBuilderTag.JSON_ATTR_TYPE),
                            fieldObj.getString(JSONBuilderTag.JSON_ATTR_PRECISION),
                            fieldObj.getString(JSONBuilderTag.JSON_ATTR_FORMAT)));
                }

                config.putDataSourceInfo(stream.getId(), stream);
            } else {
                throw new UnsupportedException(type + " data type is not supported!");
            }
        }
    }
}
