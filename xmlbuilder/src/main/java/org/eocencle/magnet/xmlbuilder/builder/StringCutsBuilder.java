package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.StringCutsInfo;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 字符串切割建构类
 * @author: huan
 * @Date: 2020-04-24
 * @Description:
 */
public class StringCutsBuilder implements XMLParser {
    // 单例实体
    private static StringCutsBuilder BUILDER = new StringCutsBuilder();

    private StringCutsBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-04-24
     * @Param []
     * @Return org.eocencle.magnet.xmlbuilder.builder.StringCutsBuilder
     * @Exception
     * @Description
     **/
    public static StringCutsBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        List<XNode> nodes = node.evalNodes(XMLBuilderTag.XML_EL_STRINGCUTS);
        this.parseElements(nodes, config);
    }

    /**
     * 解析元素
     * @Author huan
     * @Date 2020-04-24
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        StringCutsInfo stringCutsInfo = null;
        StringCutsInfo.StringCut stringCut = null;
        for (XNode node: nodes) {
            stringCutsInfo = new StringCutsInfo();
            stringCutsInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            stringCutsInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            stringCutsInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));

            List<XNode> children = node.getChildren();
            for (XNode child: children) {
                stringCut = new StringCutsInfo.StringCut();
                stringCut.setField(child.getStringAttribute(XMLBuilderTag.XML_ATTR_FIELD));
                stringCut.setTagField(child.getStringAttribute(XMLBuilderTag.XML_ATTR_TAGFIELD));
                stringCut.setStart(child.getStringAttribute(XMLBuilderTag.XML_ATTR_START));
                stringCut.setEnd(child.getStringAttribute(XMLBuilderTag.XML_ATTR_END));

                stringCutsInfo.addStringCut(stringCut);
            }

            config.putWorkFlowInfo(stringCutsInfo.getId(), stringCutsInfo);
        }
    }
}