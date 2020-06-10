package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.RowNumInfo;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 行号建构类
 * @author: huan
 * @Date: 2020-06-07
 * @Description:
 */
public class RowNumBuilder implements XMLParser {
    // 单例实体
    private static RowNumBuilder BUILDER = new RowNumBuilder();

    private RowNumBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-06-07
     * @Param []
     * @Return org.eocencle.magnet.xmlbuilder.builder.RowNumBuilder
     * @Exception
     * @Description
     */
    public static RowNumBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        this.parseElements(node.evalNodes(XMLBuilderTag.XML_ATTR_ROWNUM), config);
    }

    /**
     * 解析元素
     * @Author huan
     * @Date 2020-06-07
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        RowNumInfo rowNumInfo = null;
        for (XNode node: nodes) {
            rowNumInfo = new RowNumInfo();
            rowNumInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            rowNumInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            rowNumInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));
            rowNumInfo.setField(node.getStringAttribute(XMLBuilderTag.XML_ATTR_FIELD, XMLBuilderTag.ROWNUM_DEFAULT_FIELD));

            config.putWorkFlowInfo(rowNumInfo.getId(), rowNumInfo);
        }
    }
}
