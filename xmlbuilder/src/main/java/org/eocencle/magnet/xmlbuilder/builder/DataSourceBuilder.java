package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.builder.factory.TableSourceFactory;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 数据源建构类
 * @author: huan
 * @Date: 2020-01-14
 * @Description:
 */
public class DataSourceBuilder implements XMLParser {
    // 单例实体
    private static DataSourceBuilder BUILDER = new DataSourceBuilder();

    private DataSourceBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-02-01
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.DataSourceBuilder
     * @Exception
     * @Description
     **/
    public static DataSourceBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        XNode datasourceNode = node.evalNode(XMLBuilderTag.XML_EL_DATASOURCE);
        this.parseTableElements(datasourceNode.evalNodes(XMLBuilderTag.XML_EL_TABLE), config);
        StreamBuilder.getInstance().parse(datasourceNode, config);
    }

    /**
     * 解析表元素
     * @Author huan
     * @Date 2020-02-16
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseTableElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        String style = null;
        for (XNode node: nodes) {
            style = node.getStringAttribute(XMLBuilderTag.XML_ATTR_STYLE, CoreTag.TABLE_STYLE_DEFAULT);
            TableSourceFactory.getTableSourceBuilder(style).parse(node, config);
        }
    }
}
