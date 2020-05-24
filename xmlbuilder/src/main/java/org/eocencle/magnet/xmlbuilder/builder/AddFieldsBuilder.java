package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.AddFieldsInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 添加字段建构类
 * @author: huan
 * @Date: 2020-04-28
 * @Description:
 */
public class AddFieldsBuilder implements XMLParser {
    // 单例实体
    private static AddFieldsBuilder BUILDER = new AddFieldsBuilder();

    private AddFieldsBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-04-28
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.AddFieldsBuilder
     * @Exception
     * @Description
     **/
    public static AddFieldsBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        List<XNode> nodes = node.evalNodes(XMLBuilderTag.XML_EL_ADDFIELDS);
        this.parseElements(nodes, config);
    }

    /**
     * 解析元素
     * @Author huan
     * @Date 2020-04-28
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        AddFieldsInfo addFieldsInfo = null;
        AddFieldsInfo.AddField addField = null;
        for (XNode node: nodes) {
            addFieldsInfo = new AddFieldsInfo();
            addFieldsInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            addFieldsInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            addFieldsInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));

            List<XNode> children = node.getChildren();
            for (XNode child: children) {
                addField = new AddFieldsInfo.AddField();
                addField.setName(child.getStringAttribute(XMLBuilderTag.XML_ATTR_NAME));
                addField.setType(child.getStringAttribute(XMLBuilderTag.XML_ATTR_TYPE, CoreTag.TABLE_FIELD_TYPE_STRING));
                addField.setPrecision(child.getStringAttribute(XMLBuilderTag.XML_ATTR_PRECISION, null));
                addField.setFormat(child.getStringAttribute(XMLBuilderTag.XML_ATTR_FORMAT, "yyyy-MM-dd HH:mm:ss"));
                addField.setValue(child.getStringAttribute(XMLBuilderTag.XML_ATTR_VALUE));

                addFieldsInfo.addAddField(addField);
            }

            config.putWorkFlowInfo(addFieldsInfo.getId(), addFieldsInfo);
        }
    }
}
