package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.SplitFieldToRowsInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 列分隔转行建构类
 * @author: huan
 * @Date: 2020-04-13
 * @Description:
 */
public class SplitFieldToRowsBuilder implements XMLParser {
    // 单例实体
    private static SplitFieldToRowsBuilder BUILDER = new SplitFieldToRowsBuilder();

    private SplitFieldToRowsBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-04-13
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.SplitFieldToRowsBuilder
     * @Exception
     * @Description
     **/
    public static SplitFieldToRowsBuilder getInstance() {
        return BUILDER;
    }
    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        List<XNode> nodes = node.evalNodes(XMLBuilderTag.XML_EL_SPLITFIELDTOROWS);
        this.parseElements(nodes, config);
    }

    /**
     * 解析元素
     * @Author huan
     * @Date 2020-04-13
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        SplitFieldToRowsInfo splitFieldToRowsInfo = null;
        for (XNode node: nodes) {
            splitFieldToRowsInfo = new SplitFieldToRowsInfo();
            splitFieldToRowsInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            splitFieldToRowsInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            splitFieldToRowsInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));
            splitFieldToRowsInfo.setField(node.getStringAttribute(XMLBuilderTag.XML_ATTR_FIELD));
            splitFieldToRowsInfo.setSeparator(node.getStringAttribute(XMLBuilderTag.XML_ATTR_SEPARATOR));
            splitFieldToRowsInfo.setIsRegex(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ISREGEX, CoreTag.FALSE));
            splitFieldToRowsInfo.setTagField(node.getStringAttribute(XMLBuilderTag.XML_ATTR_TAGFIELD));
            splitFieldToRowsInfo.setRowNumField(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ROWNUMFIELD));

            config.putWorkFlowInfo(splitFieldToRowsInfo.getId(), splitFieldToRowsInfo);
        }
    }
}
