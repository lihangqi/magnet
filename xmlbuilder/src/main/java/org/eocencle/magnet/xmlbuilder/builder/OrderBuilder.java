package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.OrderInfo;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 排序建构类
 * @author: huan
 * @Date: 2020-03-16
 * @Description:
 */
public class OrderBuilder implements XMLParser {
    // 单例实体
    private static OrderBuilder BUILDER = new OrderBuilder();

    private OrderBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-01-18
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.GroupBuilder
     * @Exception
     * @Description
     **/
    public static OrderBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        this.parseElements(node.evalNodes(XMLBuilderTag.XML_EL_ORDER), config);
    }

    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        OrderInfo orderInfo = null;
        for (XNode node: nodes) {
            orderInfo = new OrderInfo();
            orderInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            orderInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            orderInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));
            orderInfo.setField(node.getStringAttribute(XMLBuilderTag.XML_ATTR_FIELD));

            config.putWorkFlowInfo(orderInfo.getId(), orderInfo);
        }
    }
}
