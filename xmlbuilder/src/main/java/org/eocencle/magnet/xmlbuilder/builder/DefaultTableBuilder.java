package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

/**
 * 默认数据源建构类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class DefaultTableBuilder implements XMLParser {
    // 单例实体
    private static DefaultTableBuilder BUILDER = new DefaultTableBuilder();

    private DefaultTableBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-01-18
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.DefaultTableBuilder
     * @Exception
     * @Description
     **/
    public static DefaultTableBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        TableInfo table = new TableInfo();
        // 添加表信息
        table.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
        table.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
        table.setStyle(CoreTag.TABLE_STYLE_DEFAULT);
        table.setSrc(node.getStringAttribute(XMLBuilderTag.XML_ATTR_SRC));
        table.setFormat(node.getStringAttribute(XMLBuilderTag.XML_ATTR_FORMAT, CoreTag.FILE_FORMAT_TEXTFILE));
        table.setSeparator(node.getStringAttribute(XMLBuilderTag.XML_ATTR_SEPARATOR, CoreTag.SPLIT_INVISIBLE1));

        // 添加表字段信息
        XNode fieldsNode = node.evalNode(XMLBuilderTag.XML_EL_FIELDS);
        if (null != fieldsNode) {
            for (XNode childNode: fieldsNode.getChildren()) {
                table.addField(new DataSourceField(childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_NAME),
                        childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_TYPE, CoreTag.TABLE_FIELD_TYPE_STRING),
                        childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_PRECISION, null),
                        childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_FORMAT, "yyyy-MM-dd HH:mm:ss")));
            }
        }

        config.putDataSourceInfo(table.getId(), table);
    }
}
