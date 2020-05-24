package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

/**
 * 碎片建构类
 * @author: huan
 * @Date: 2020-03-07
 * @Description:
 */
public class FragmentBuilder implements XMLParser {
    // 单例实体
    private static FragmentBuilder BUILDER = new FragmentBuilder();

    private FragmentBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-03-07
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.FragmentBuilder
     * @Exception
     * @Description
     **/
    public static FragmentBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        ScriptBuilder builder = ScriptBuilder.getInstance();
        builder.parse(node.evalNode(XMLBuilderTag.XML_EL_FRAGMENT), config);
    }
}
