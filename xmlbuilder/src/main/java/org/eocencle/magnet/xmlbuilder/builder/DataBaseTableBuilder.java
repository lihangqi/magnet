package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.mapping.InfoParam;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

/**
 * 数据库数据源建构类
 * @author: huan
 * @Date: 2020-03-26
 * @Description:
 */
public class DataBaseTableBuilder implements XMLParser {
    // 单例实体
    private static DataBaseTableBuilder BUILDER = new DataBaseTableBuilder();

    private DataBaseTableBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-03-26
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.DataBaseTableBuilder
     * @Exception
     * @Description
     **/
    public static DataBaseTableBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        TableInfo table = new TableInfo();
        // 添加表信息
        table.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
        table.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
        table.setStyle(node.getStringAttribute(XMLBuilderTag.XML_ATTR_STYLE, CoreTag.TABLE_STYLE_DEFAULT));
        table.setSrc(node.getStringAttribute(XMLBuilderTag.XML_ATTR_SRC));

        // 添加流配置信息
        for (XNode childNode: node.evalNode(XMLBuilderTag.XML_EL_CONFIG).getChildren()) {
            // 获取参数
            table.putConfigParam(new InfoParam(childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY),
                    childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_VALUE)));
        }

        // 添加表字段信息
        for (XNode childNode: node.evalNode(XMLBuilderTag.XML_EL_FIELDS).getChildren()) {
            table.addField(new DataSourceField(childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_NAME),
                    childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_TYPE, CoreTag.TABLE_FIELD_TYPE_STRING),
                    childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_PRECISION, null),
                    childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_FORMAT, "yyyy-MM-dd HH:mm:ss")));
        }

        config.putDataSourceInfo(table.getId(), table);
    }
}
