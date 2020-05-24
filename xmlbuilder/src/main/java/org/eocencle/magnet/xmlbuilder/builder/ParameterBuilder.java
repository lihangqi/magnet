package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

/**
 * 参数建构类
 * @author: huan
 * @Date: 2020-01-14
 * @Description:
 */
public class ParameterBuilder implements XMLParser {
    // 单例实体
    private static ParameterBuilder BUILDER = new ParameterBuilder();

    private ParameterBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-01-18
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.ParameterBuilder
     * @Exception
     * @Description
     **/
    public static ParameterBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        // 获取变量参数标签
        XNode currNode = node.evalNode(XMLBuilderTag.XML_EL_PARAMETER);
        if (null == currNode) return ;
        VariableBuilder.getInstance().parse(currNode, config);
    }
}
