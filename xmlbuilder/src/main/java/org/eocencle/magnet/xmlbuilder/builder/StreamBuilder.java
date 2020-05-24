package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.mapping.InfoParam;
import org.eocencle.magnet.core.mapping.StreamInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 流信息建构类
 * @author: huan
 * @Date: 2020-02-16
 * @Description:
 */
public class StreamBuilder implements XMLParser {
    // 单例实体
    private static StreamBuilder BUILDER = new StreamBuilder();

    private StreamBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-02-16
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.StreamBuilder
     * @Exception
     * @Description
     **/
    public static StreamBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        List<XNode> nodes = node.evalNodes(XMLBuilderTag.XML_EL_STREAM);
        for (XNode n: nodes) {
            this.parseStreamElement(n, config);
        }
    }

    /**
     * 解析流元素
     * @Author huan
     * @Date 2020-05-06
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     */
    private void parseStreamElement(XNode node, XmlProjectConfig config) {
        StreamInfo stream = new StreamInfo();
        // 添加流信息
        stream.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
        stream.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
        stream.setTopics(node.getStringAttribute(XMLBuilderTag.XML_ATTR_TOPICS));
        stream.setFormat(node.getStringAttribute(XMLBuilderTag.XML_ATTR_FORMAT, CoreTag.FILE_FORMAT_TEXTFILE));
        stream.setSeparator(node.getStringAttribute(XMLBuilderTag.XML_ATTR_SEPARATOR, CoreTag.SPLIT_INVISIBLE1));

        // 添加流配置信息
        StrictMap<InfoParam> infoParamMap = VariableBuilder.getInstance()
                .parseElements(node.evalNode(XMLBuilderTag.XML_EL_CONFIG).getChildren());
        stream.setConfig(infoParamMap);

        // 添加流字段信息
        for (XNode childNode: node.evalNode(XMLBuilderTag.XML_EL_FIELDS).getChildren()) {
            stream.addField(new DataSourceField(childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_NAME),
                    childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_TYPE, CoreTag.TABLE_FIELD_TYPE_STRING),
                    childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_PRECISION, null),
                    childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_FORMAT, "yyyy-MM-dd HH:mm:ss")));
        }

        config.putDataSourceInfo(stream.getId(), stream);
    }
}
