package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 脚本建构类
 * @author: huan
 * @Date: 2020-03-07
 * @Description:
 */
public class ScriptBuilder implements XMLParser {
    // 单例实体
    private static ScriptBuilder BUILDER = new ScriptBuilder();

    private ScriptBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-03-07
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.ScriptBuilder
     * @Exception
     * @Description
     **/
    public static ScriptBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        if (null == node) {
            return ;
        }
        this.parseElements(node.evalNodes(XMLBuilderTag.XML_EL_SCRIPT), config);
    }

    /**
     * 解析元素
     * @Author huan
     * @Date 2020-03-07
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        for (XNode node: nodes) {
            config.putFragmentInfo(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID), node);
        }
    }
}
