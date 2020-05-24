package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.InfoParam;
import org.eocencle.magnet.core.mapping.OutputInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 输出建构类
 * @author: huan
 * @Date: 2020-01-19
 * @Description:
 */
public class OutputBuilder implements XMLParser {
    // 单例实体
    private static OutputBuilder BUILDER = new OutputBuilder();

    private OutputBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-01-19
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.OutputBuilder
     * @Exception
     * @Description
     **/
    public static OutputBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        this.parseElements(node.evalNodes(XMLBuilderTag.XML_EL_OUTPUT), config);
    }

    /**
     * 解析输出节点
     * @Author huan
     * @Date 2020-01-19
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        OutputInfo outputInfo = null;
        for (XNode node: nodes) {
            outputInfo = new OutputInfo();
            outputInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            outputInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));
            outputInfo.setStyle(node.getStringAttribute(XMLBuilderTag.XML_ATTR_STYLE, CoreTag.OUTPUT_STYLE_FILE));
            outputInfo.setTarget(node.getStringAttribute(XMLBuilderTag.XML_ATTR_TARGET));
            outputInfo.setType(node.getStringAttribute(XMLBuilderTag.XML_ATTR_TYPE, CoreTag.OUTPUT_TYPE_CREATE));
            outputInfo.setCompress(node.getStringAttribute(XMLBuilderTag.XML_ATTR_COMPRESS, CoreTag.COMPRESS_NONE));
            outputInfo.setSeparator(node.getStringAttribute(XMLBuilderTag.XML_ATTR_SEPARATOR, CoreTag.SPLIT_INVISIBLE1));

            XNode configNode = node.evalNode(XMLBuilderTag.XML_EL_CONFIG);
            if (null != configNode) {
                List<XNode> params = configNode.getChildren();
                for (XNode cnode: params) {
                    outputInfo.putOutputConfig(new InfoParam(cnode.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY),
                            cnode.getStringAttribute(XMLBuilderTag.XML_ATTR_VALUE)));
                }
            }

            config.putWorkFlowInfo(outputInfo.getId(), outputInfo);
        }
    }
}
