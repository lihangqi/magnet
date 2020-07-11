package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.mapping.FilterField;
import org.eocencle.magnet.core.mapping.InfoParam;
import org.eocencle.magnet.core.mapping.QueryInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 查询建构类
 * @author: huan
 * @Date: 2020-07-03
 * @Description:
 */
public class QueryBuilder implements XMLParser {
    // 单例实体
    private static QueryBuilder BUILDER = new QueryBuilder();

    private QueryBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-07-03
     * @Param []
     * @Return org.eocencle.magnet.xmlbuilder.builder.QueryBuilder
     * @Exception
     * @Description
     */
    public static QueryBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        this.parseElements(node.evalNodes(XMLBuilderTag.XML_EL_QUERY), config);
    }

    /**
     * 解析元素
     * @Author huan
     * @Date 2020-07-03
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        QueryInfo queryInfo = null;
        FilterField filterField = null;
        String tagName = null;
        for (XNode node: nodes) {
            queryInfo = new QueryInfo();
            queryInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            queryInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            queryInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));
            queryInfo.setType(node.getStringAttribute(XMLBuilderTag.XML_ATTR_TYPE, CoreTag.TABLE_STYLE_DATABASE));
            queryInfo.setCache(node.getStringAttribute(XMLBuilderTag.XML_ATTR_CACHE, CoreTag.FALSE));

            // 添加配置信息
            StrictMap<InfoParam> infoParamMap = VariableBuilder.getInstance()
                    .parseElements(node.evalNode(XMLBuilderTag.XML_EL_CONFIG).getChildren());
            queryInfo.setConfig(infoParamMap);

            // 添加过滤字段信息
            for (XNode condition: node.evalNode(XMLBuilderTag.XML_EL_CONDITIONS).getChildren()) {
                filterField = new FilterField();
                tagName = condition.getName().toUpperCase();
                if (tagName.startsWith(CoreTag.FILTER_JOIN_AND)) {
                    filterField.setJoin(CoreTag.FILTER_JOIN_AND);
                    filterField.setType(tagName.substring(CoreTag.FILTER_JOIN_AND.length()));
                } else if (tagName.startsWith(CoreTag.FILTER_JOIN_OR)) {
                    filterField.setJoin(CoreTag.FILTER_JOIN_OR);
                    filterField.setType(tagName.substring(CoreTag.FILTER_JOIN_OR.length()));
                }
                filterField.setField(condition.getStringAttribute(XMLBuilderTag.XML_ATTR_FIELD));
                filterField.setValue(condition.getStringAttribute(XMLBuilderTag.XML_ATTR_VALUE));
                filterField.setStart(condition.getStringAttribute(XMLBuilderTag.XML_ATTR_START));
                filterField.setEnd(condition.getStringAttribute(XMLBuilderTag.XML_ATTR_END));

                queryInfo.addFilterField(filterField);
            }

            // 添加表字段信息
            for (XNode childNode: node.evalNode(XMLBuilderTag.XML_EL_FIELDS).getChildren()) {
                queryInfo.addField(new DataSourceField(childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_NAME),
                        childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_TYPE, CoreTag.TABLE_FIELD_TYPE_STRING),
                        childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_PRECISION, null),
                        childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_FORMAT, "yyyy-MM-dd HH:mm:ss")));
            }

            config.putWorkFlowInfo(queryInfo.getId(), queryInfo);
        }
    }
}
