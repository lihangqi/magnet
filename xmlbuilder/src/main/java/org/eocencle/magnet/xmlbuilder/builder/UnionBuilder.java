package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.UnionInfo;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 合并建构类
 * @author: huan
 * @Date: 2020-04-03
 * @Description:
 */
public class UnionBuilder implements XMLParser {
    // 单例实体
    private static UnionBuilder BUILDER = new UnionBuilder();

    private UnionBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-04-03
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.UnionBuilder
     * @Exception
     * @Description
     **/
    public static UnionBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        List<XNode> nodes = node.evalNodes(XMLBuilderTag.XML_EL_UNION);
        this.parseElements(nodes, config);
    }

    /**
     * 解析元素
     * @Author huan
     * @Date 2020-04-03
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        UnionInfo unionInfo = null;
        for (XNode node: nodes) {
            unionInfo = new UnionInfo();
            unionInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            unionInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            unionInfo.setRefs(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REFS));

            config.putWorkFlowInfo(unionInfo.getId(), unionInfo);
        }
    }
}
