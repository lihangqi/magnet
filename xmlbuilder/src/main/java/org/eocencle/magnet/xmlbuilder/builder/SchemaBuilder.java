package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.SchemaInfo;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * Schema建构类
 * @author: huan
 * @Date: 2020-04-06
 * @Description:
 */
public class SchemaBuilder implements XMLParser {
    // 单例实体
    private static SchemaBuilder BUILDER = new SchemaBuilder();

    private SchemaBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-04-06
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.SchemaBuilder
     * @Exception
     * @Description
     **/
    public static SchemaBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        this.parseElements(node.evalNodes(XMLBuilderTag.XML_EL_SCHEMA), config);
    }

    /**
     * 解析元素
     * @Author huan
     * @Date 2020-04-06
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        SchemaInfo schemaInfo = null;
        for (XNode node: nodes) {
            schemaInfo = new SchemaInfo();
            schemaInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            schemaInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));

            config.putWorkFlowInfo(schemaInfo.getId(), schemaInfo);
        }
    }
}
