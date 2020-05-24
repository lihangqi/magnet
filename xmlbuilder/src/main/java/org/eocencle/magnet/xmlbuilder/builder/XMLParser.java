package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;

/**
 * XML解析接口
 * @author: huan
 * @Date: 2020-01-14
 * @Description:
 */
public interface XMLParser {
    /**
     * 解析xml
     * @Author huan
     * @Date 2020-1-14
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    void parse(XNode node, XmlProjectConfig config);
}
