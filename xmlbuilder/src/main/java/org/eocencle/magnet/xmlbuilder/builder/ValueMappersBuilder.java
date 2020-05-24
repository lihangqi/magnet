package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.InfoParam;
import org.eocencle.magnet.core.mapping.ValueMappersInfo;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 *
 * @author: huan
 * @Date: 2020-04-08
 * @Description:
 */
public class ValueMappersBuilder implements XMLParser {
    // 单例实体
    private static ValueMappersBuilder BUILDER = new ValueMappersBuilder();

    private ValueMappersBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-04-08
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.ValueMappersBuilder
     * @Exception 
     * @Description
     **/
    public static ValueMappersBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        List<XNode> nodes = node.evalNodes(XMLBuilderTag.XML_EL_VALUEMAPPERS);
        this.parseElements(nodes, config);
    }

    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        ValueMappersInfo valueMappersInfo = null;
        ValueMappersInfo.ValueMapper valueMapper = null;
        InfoParam mapper = null;
        for (XNode node: nodes) {
            valueMappersInfo = new ValueMappersInfo();
            valueMappersInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            valueMappersInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            valueMappersInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));

            List<XNode> children = node.getChildren();
            for (XNode child: children) {
                valueMapper = new ValueMappersInfo.ValueMapper();
                valueMapper.setField(child.getStringAttribute(XMLBuilderTag.XML_ATTR_FIELD));
                valueMapper.setTagField(child.getStringAttribute(XMLBuilderTag.XML_ATTR_TAGFIELD));
                valueMapper.setNonMatch(child.getStringAttribute(XMLBuilderTag.XML_ATTR_NONMATCH));

                List<XNode> ms = child.getChildren();
                for (XNode m: ms) {
                    mapper = new InfoParam(m.getStringAttribute(XMLBuilderTag.XML_ATTR_SOURCE),
                            m.getStringAttribute(XMLBuilderTag.XML_ATTR_TARGET));

                    valueMapper.putValMapper(mapper);
                }

                valueMappersInfo.addValueMapper(valueMapper);
            }

            config.putWorkFlowInfo(valueMappersInfo.getId(), valueMappersInfo);
        }
    }
}
