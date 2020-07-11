package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.FilterField;
import org.eocencle.magnet.core.mapping.FilterInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 过滤建构类
 * @author: huan
 * @Date: 2020-03-13
 * @Description:
 */
public class FilterBuilder implements XMLParser {
    // 单例实体
    private static FilterBuilder BUILDER = new FilterBuilder();

    private FilterBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-03-13
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.FilterBuilder
     * @Exception
     * @Description
     **/
    public static FilterBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        this.parseElements(node.evalNodes(XMLBuilderTag.XML_EL_FILTER), config);
    }

    /**
     * 解析元素
     * @Author huan
     * @Date 2020-03-13
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        FilterInfo filterInfo = null;
        List<XNode> conds = null;
        String tagName = null;
        FilterField filterField = null;
        for (XNode node: nodes) {
            filterInfo = new FilterInfo();
            filterInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            filterInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            filterInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));
            conds = node.evalNode(XMLBuilderTag.XML_EL_CONDITIONS).getChildren();
            for (XNode cond: conds) {
                filterField = new FilterField();
                tagName = cond.getName().toUpperCase();
                if (tagName.startsWith(CoreTag.FILTER_JOIN_AND)) {
                    filterField.setJoin(CoreTag.FILTER_JOIN_AND);
                    filterField.setType(tagName.substring(CoreTag.FILTER_JOIN_AND.length()));
                } else if (tagName.startsWith(CoreTag.FILTER_JOIN_OR)) {
                    filterField.setJoin(CoreTag.FILTER_JOIN_OR);
                    filterField.setType(tagName.substring(CoreTag.FILTER_JOIN_OR.length()));
                }
                filterField.setField(cond.getStringAttribute(XMLBuilderTag.XML_ATTR_FIELD));
                filterField.setValue(cond.getStringAttribute(XMLBuilderTag.XML_ATTR_VALUE));
                filterField.setStart(cond.getStringAttribute(XMLBuilderTag.XML_ATTR_START));
                filterField.setEnd(cond.getStringAttribute(XMLBuilderTag.XML_ATTR_END));

                filterInfo.addFilterFields(filterField);
            }

            config.putWorkFlowInfo(filterInfo.getId(), filterInfo);
        }
    }
}
