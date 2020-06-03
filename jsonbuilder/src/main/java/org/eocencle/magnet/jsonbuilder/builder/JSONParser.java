package org.eocencle.magnet.jsonbuilder.builder;

import org.eocencle.magnet.jsonbuilder.session.JsonProjectConfig;

/**
 * JSON解析接口
 * @author: huan
 * @Date: 2020-05-30
 * @Description:
 */
public interface JSONParser {
    /**
     * 解析json
     * @Author huan
     * @Date 2020-05-30
     * @Param [parser, config]
     * @Return void
     * @Exception
     * @Description
     */
    void parse(Object parser, JsonProjectConfig config);
}
