package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.AddSequenceInfo;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.List;

/**
 * 添加序列建构类
 * @author: huan
 * @Date: 2020-04-30
 * @Description:
 */
public class AddSequenceBuilder implements XMLParser {
    // 单例实体
    private static AddSequenceBuilder BUILDER = new AddSequenceBuilder();

    private AddSequenceBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-04-30
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.AddSequenceBuilder
     * @Exception
     * @Description
     **/
    public static AddSequenceBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        List<XNode> nodes = node.evalNodes(XMLBuilderTag.XML_EL_ADDSEQUENCE);
        this.parseElements(nodes, config);
    }

    /**
     * 解析元素
     * @Author huan
     * @Date 2020-04-30
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        AddSequenceInfo addSequenceInfo = null;
        for (XNode node: nodes) {
            addSequenceInfo = new AddSequenceInfo();
            addSequenceInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            addSequenceInfo.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            addSequenceInfo.setRef(node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF));
            addSequenceInfo.setTagField(node.getStringAttribute(XMLBuilderTag.XML_ATTR_TAGFIELD));
            addSequenceInfo.setInit(node.getStringAttribute(XMLBuilderTag.XML_ATTR_INIT, "1"));
            addSequenceInfo.setStep(node.getStringAttribute(XMLBuilderTag.XML_ATTR_STEP, "1"));

            config.putWorkFlowInfo(addSequenceInfo.getId(), addSequenceInfo);
        }
    }
}
