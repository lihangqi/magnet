package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.FilterField;
import org.eocencle.magnet.core.mapping.JoinInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 连接建构类
 * @author: huan
 * @Date: 2020-04-04
 * @Description:
 */
public class JoinBuilder implements XMLParser {
    // 单例实体
    private static JoinBuilder BUILDER = new JoinBuilder();

    private JoinBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-04-04
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.JoinBuilder
     * @Exception
     * @Description
     **/
    public static JoinBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        this.parseElements(node.evalNodes(XMLBuilderTag.XML_EL_JOIN), config);
    }

    /**
     * 解析元素
     * @Author huan
     * @Date 2020-04-04
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return;
        }

        JoinInfo joinInfo = null;
        List<XNode> ons = null;
        String tagName = null;
        FilterField onField = null;
        for (XNode node: nodes) {
            joinInfo = new JoinInfo();
            joinInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            joinInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            joinInfo.setType(node.getStringAttribute(XMLBuilderTag.XML_ATTR_TYPE, CoreTag.JOIN_TYPE_INNER));
            joinInfo.setLeftRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_LEFTREF));
            joinInfo.setRightRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_RIGHTREF));
            ons = node.evalNode(XMLBuilderTag.XML_EL_ON).getChildren();
            for (XNode on: ons) {
                onField = new FilterField();
                tagName = on.getName().toUpperCase();
                if (tagName.startsWith(CoreTag.FILTER_JOIN_AND)) {
                    onField.setJoin(CoreTag.FILTER_JOIN_AND);
                    onField.setType(tagName.substring(CoreTag.FILTER_JOIN_AND.length()));
                } else if (tagName.startsWith(CoreTag.FILTER_JOIN_OR)) {
                    onField.setJoin(CoreTag.FILTER_JOIN_OR);
                    onField.setType(tagName.substring(CoreTag.FILTER_JOIN_OR.length()));
                }
                onField.setField(on.getStringAttribute(XMLBuilderTag.XML_ATTR_FIELD));
                onField.setValue(on.getStringAttribute(XMLBuilderTag.XML_ATTR_VALUE));

                joinInfo.addFilterFields(onField);
            }

            config.putWorkFlowInfo(joinInfo.getId(), joinInfo);
        }
    }
}
