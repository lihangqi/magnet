package org.eocencle.magnet.jsonbuilder.builder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.jsonbuilder.session.JsonProjectConfig;
import org.eocencle.magnet.jsonbuilder.util.JSONBuilderTag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 参数建构类
 * @author: huan
 * @Date: 2020-05-30
 * @Description:
 */
public class ParameterBuilder implements JSONParser {
    // 单例实体
    private static ParameterBuilder BUILDER = new ParameterBuilder();

    private ParameterBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-05-30
     * @Param []
     * @Return org.eocencle.magnet.jsonbuilder.builder.ParameterBuilder
     * @Exception
     * @Description
     */
    public static ParameterBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(Object parser, JsonProjectConfig config) {
        JSONObject jsonObejct = (JSONObject) parser;
        JSONArray paramArray = jsonObejct.getJSONArray(JSONBuilderTag.JSON_ATTR_PARAMETER);

        if (null != paramArray) {
            Iterator<Object> iterator = paramArray.iterator();
            JSONObject jsonObj = null;
            String key = null;
            while (iterator.hasNext()) {
                jsonObj = (JSONObject) iterator.next();
                key = jsonObj.getString(JSONBuilderTag.JSON_ATTR_KEY);
                if (jsonObj.containsKey(JSONBuilderTag.JSON_ATTR_VALUE)) {
                    config.putParameterInfo(key, jsonObj.getString(JSONBuilderTag.JSON_ATTR_VALUE));
                    continue;
                } else if (jsonObj.containsKey(JSONBuilderTag.JSON_ATTR_LIST)) {
                    config.putParameterInfo(key, this.parseList(jsonObj.getJSONArray(JSONBuilderTag.JSON_ATTR_LIST)));
                }
            }
        }
    }

    /**
     *
     * @Author huan
     * @Date 2020-06-14
     * @Param [parser]
     * @Return org.eocencle.magnet.core.util.StrictMap<java.lang.Object>
     * @Exception
     * @Description
     */
    public StrictMap<Object> parse(Object parser) {
        JSONObject jsonObejct = (JSONObject) parser;
        JSONArray paramArray = jsonObejct.getJSONArray(JSONBuilderTag.JSON_ATTR_PARAMETER);

        Iterator<Object> iterator = paramArray.iterator();
        JSONObject jsonObj = null;
        String key = null;
        StrictMap<Object> map = new StrictMap<>("Params");
        while (iterator.hasNext()) {
            jsonObj = (JSONObject) iterator.next();
            key = jsonObj.getString(JSONBuilderTag.JSON_ATTR_KEY);
            if (jsonObj.containsKey(JSONBuilderTag.JSON_ATTR_VALUE)) {
                map.put(key, jsonObj.getString(JSONBuilderTag.JSON_ATTR_VALUE));
                continue;
            } else if (jsonObj.containsKey(JSONBuilderTag.JSON_ATTR_LIST)) {
                map.put(key, this.parseList(jsonObj.getJSONArray(JSONBuilderTag.JSON_ATTR_LIST)));
            }
        }
        return map;
    }

    /**
     * 解析list参数
     * @Author huan
     * @Date 2020-05-31
     * @Param [jsonArray]
     * @Return java.util.List<java.lang.String>
     * @Exception
     * @Description
     */
    private List<String> parseList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        Iterator<Object> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().toString());
        }
        return list;
    }
}
