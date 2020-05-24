package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.DistinctInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 去重建构类
 * @author: huan
 * @Date: 2020-03-15
 * @Description:
 */
public class DistinctBuilder implements XMLParser {
    // 单例实体
    private static DistinctBuilder BUILDER = new DistinctBuilder();

    private DistinctBuilder() {

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
    public static DistinctBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        this.parseElements(node.evalNodes(XMLBuilderTag.XML_EL_DISTINCT), config);
    }

    /**
     * 解析去重节点
     * @Author huan
     * @Date 2020-03-15
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        DistinctInfo distinctInfo = null;
        DistinctInfo.DistinctField distinctField = null;
        for (XNode node: nodes) {
            distinctInfo = new DistinctInfo();
            distinctInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            distinctInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            distinctInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));
            distinctInfo.setCntField(node.getStringAttribute(XMLBuilderTag.XML_ATTR_CNTFIELD));

            List<XNode> children = node.getChildren();
            if (null != children) {
                for (XNode child: children) {
                    distinctField = new DistinctInfo.DistinctField();
                    distinctField.setField(child.getStringAttribute(XMLBuilderTag.XML_ATTR_FIELD));
                    distinctField.setIgnoreCase(child.getStringAttribute(XMLBuilderTag.XML_ATTR_IGNORECASE, CoreTag.FALSE));

                    distinctInfo.addDistinctFields(distinctField);
                }
            }

            config.putWorkFlowInfo(distinctInfo.getId(), distinctInfo);
        }
    }
}
