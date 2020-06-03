package org.eocencle.magnet.jsonbuilder.builder;

import com.alibaba.fastjson.JSONArray;
import org.eocencle.magnet.jsonbuilder.session.JsonProjectConfig;

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
        JSONArray jsonArray = (JSONArray) parser;
    }
}
