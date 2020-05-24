package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.GroupInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 分组建构类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class GroupBuilder implements XMLParser {
    // 单例实体
    private static GroupBuilder BUILDER = new GroupBuilder();

    private GroupBuilder() {

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
    public static GroupBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        this.parseElements(node.evalNodes(XMLBuilderTag.XML_EL_GROUP), config);
    }

    /**
     * 解析分组节点
     * @Author huan
     * @Date 2020-01-18
     * @Param [nodes]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        GroupInfo groupInfo = null;
        String[] fields = null, key = null, orders = null;
        String field = null;
        for (XNode node: nodes) {
            groupInfo = new GroupInfo();
            groupInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            groupInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            groupInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));
            field = node.getStringAttribute(XMLBuilderTag.XML_ATTR_FIELD);
            fields = field.trim().split(CoreTag.SPLIT_COMMA);
            for (String val: fields) {
                groupInfo.addGroupField(new GroupInfo.GroupField(val.trim()));
            }

            orders = node.getStringAttribute(XMLBuilderTag.XML_ATTR_ORDER).split(CoreTag.SPLIT_COMMA);
            for (String order: orders) {
                key = order.trim().split(CoreTag.SPLIT_BLANK);
                if (2 == key.length) {
                    groupInfo.addOrderField(new GroupInfo.OrderField(key[0].trim(), key[1].trim()));
                } else {
                    groupInfo.addOrderField(new GroupInfo.OrderField(key[0].trim()));
                }
            }

            groupInfo.setRownumField(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ROWNUM));
            groupInfo.setStreamState(node.getStringAttribute(XMLBuilderTag.XML_ATTR_STREAM_STATE));

            config.putWorkFlowInfo(groupInfo.getId(), groupInfo);
        }
    }
}
