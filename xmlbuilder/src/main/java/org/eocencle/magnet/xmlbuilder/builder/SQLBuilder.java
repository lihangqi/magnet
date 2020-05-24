package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.xmlbuilder.mapping.SQLScriptInfo;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;
import org.eocencle.magnet.xmlbuilder.xmltags.SQLScriptParser;

import java.util.List;

/**
 * 工作流SQL建构类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class SQLBuilder implements XMLParser {
    // 单例实体
    private static SQLBuilder BUILDER = new SQLBuilder();

    private SQLBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-01-18
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.SQLBuilder
     * @Exception
     * @Description
     **/
    public static SQLBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        this.parseElements(node.evalNodes(XMLBuilderTag.XML_EL_SQL), config);
    }

    /**
     * 解析SQL节点
     * @Author huan
     * @Date 2020-01-18
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) {
        if (null == nodes) {
            return ;
        }

        SQLScriptInfo script = null;
        SQLScriptParser parser = null;
        XMLIncludeTransformer includeParser = new XMLIncludeTransformer(config);
        for (XNode node: nodes) {
            parser = new SQLScriptParser(node);

            includeParser.applyIncludes(node.getNode());

            script = new SQLScriptInfo(parser.parseSQLScriptNode());
            script.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            script.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));

            config.putWorkFlowInfo(script.getId(), script);
        }
    }
}
