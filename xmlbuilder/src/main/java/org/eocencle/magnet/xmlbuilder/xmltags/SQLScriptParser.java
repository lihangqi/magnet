package org.eocencle.magnet.xmlbuilder.xmltags;

import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.xmlbuilder.mapping.SQLSource;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL脚本解析类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class SQLScriptParser {
    // SQL节点
    private XNode context;

    public SQLScriptParser(XNode context) {
        this.context = context;
    }

    // 解析SQL脚本节点
    public SQLSource parseSQLScriptNode() {
        List<SQLNode> contents = parseDynamicTags(context);
        MixedSQLNode rootSQLNode = new MixedSQLNode(contents);
        SQLSource sqlSource = new DynamicSQLSource(rootSQLNode);
        return sqlSource;
    }

    // 解析动态标签
    private List<SQLNode> parseDynamicTags(XNode node) {
        List<SQLNode> contents = new ArrayList<SQLNode>();
        NodeList children = node.getNode().getChildNodes();
        XNode child = null;
        String nodeName = null;
        short nodeType;

        // 循环遍历SQL子节点
        for (int i = 0; i < children.getLength(); i++) {
            child = node.newXNode(children.item(i));
            nodeName = child.getNode().getNodeName();
            nodeType = child.getNode().getNodeType();

            // 判断是否是文本节点
            if (Node.CDATA_SECTION_NODE == nodeType || Node.TEXT_NODE == nodeType) {
                contents.add(new TextSQLNode(child.getStringBody(CoreTag.STRING_BLANK)));
            }

            // 判断是否是元素节点
            else if (Node.ELEMENT_NODE == nodeType) {
                NodeHandler handler = this.nodeHandlers.get(nodeName);
                if (null == handler) {
                    throw new RuntimeException("Unknown element <" + nodeName + "> in SQL.");
                }
                handler.handleNode(child, contents);
            }
        }
        return contents;
    }

    private StrictMap<NodeHandler> nodeHandlers = new StrictMap<NodeHandler>("SQL script tags") {
        private static final long serialVersionUID = 7123056019193266281L;

        {
            put(XMLBuilderTag.XML_EL_TRIM, new TrimHandler());
            put(XMLBuilderTag.XML_EL_WHERE, new WhereHandler());
            put(XMLBuilderTag.XML_EL_FOREACH, new ForEachHandler());
            put(XMLBuilderTag.XML_EL_IF, new IfHandler());
            put(XMLBuilderTag.XML_EL_CHOOSE, new ChooseHandler());
            put(XMLBuilderTag.XML_EL_WHEN, new IfHandler());
            put(XMLBuilderTag.XML_EL_OTHERWISE, new OtherwiseHandler());
        }
    };

    /**
     * 节点处理接口
     * @author: huan
     * @Date: 2020-01-18
     * @Description:
     */
    private interface NodeHandler {
        /**
         * 处理节点
         * @Author huan
         * @Date 2020-01-18
         * @Param [nodeToHandle, targetContents]
         * @Return void
         * @Exception
         * @Description
         **/
        void handleNode(XNode nodeToHandle, List<SQLNode> targetContents);
    }

    /**
     * trim操作类
     * @Author huan
     * @Date 2020-01-18
     * @Param
     * @Return
     * @Exception
     * @Description
     **/
    private class TrimHandler implements NodeHandler {
        public void handleNode(XNode nodeToHandle, List<SQLNode> targetContents) {
            List<SQLNode> contents = parseDynamicTags(nodeToHandle);
            MixedSQLNode mixedSqlNode = new MixedSQLNode(contents);
            String prefix = nodeToHandle.getStringAttribute(XMLBuilderTag.XML_ATTR_PREFIX);
            String prefixOverrides = nodeToHandle.getStringAttribute(XMLBuilderTag.XML_ATTR_PREFIXOVERRIDES);
            String suffix = nodeToHandle.getStringAttribute(XMLBuilderTag.XML_ATTR_SUFFIX);
            String suffixOverrides = nodeToHandle.getStringAttribute(XMLBuilderTag.XML_ATTR_SUFFIXOVERRIDES);
            TrimSQLNode trim = new TrimSQLNode(mixedSqlNode, prefix, prefixOverrides, suffix, suffixOverrides);
            targetContents.add(trim);
        }
    }

    /**
     * where操作类
     * @author: huan
     * @Date: 2020-01-18
     * @Description:
     */
    private class WhereHandler implements NodeHandler {
        public void handleNode(XNode nodeToHandle, List<SQLNode> targetContents) {
            List<SQLNode> contents = parseDynamicTags(nodeToHandle);
            MixedSQLNode mixedSQLNode = new MixedSQLNode(contents);
            WhereSQLNode where = new WhereSQLNode(mixedSQLNode);
            targetContents.add(where);
        }
    }

    /**
     * 循环操作类
     * @author: huan
     * @Date: 2020-01-18
     * @Description:
     */
    private class ForEachHandler implements NodeHandler {
        public void handleNode(XNode nodeToHandle, List<SQLNode> targetContents) {
            List<SQLNode> contents = parseDynamicTags(nodeToHandle);
            MixedSQLNode mixedSQLNode = new MixedSQLNode(contents);
            String collection = nodeToHandle.getStringAttribute(XMLBuilderTag.XML_ATTR_COLLECTION);
            String item = nodeToHandle.getStringAttribute(XMLBuilderTag.XML_ATTR_ITEM);
            String index = nodeToHandle.getStringAttribute(XMLBuilderTag.XML_ATTR_INDEX);
            String open = nodeToHandle.getStringAttribute(XMLBuilderTag.XML_ATTR_OPEN);
            String close = nodeToHandle.getStringAttribute(XMLBuilderTag.XML_ATTR_CLOSE);
            String separator = nodeToHandle.getStringAttribute(XMLBuilderTag.XML_ATTR_SEPARATOR);
            ForEachSQLNode forEachSQLNode = new ForEachSQLNode(mixedSQLNode, collection, index, item, open, close, separator);
            targetContents.add(forEachSQLNode);
        }
    }

    /**
     * 判断操作类
     * @author: huan
     * @Date: 2020-01-18
     * @Description:
     */
    private class IfHandler implements NodeHandler {
        public void handleNode(XNode nodeToHandle, List<SQLNode> targetContents) {
            List<SQLNode> contents = parseDynamicTags(nodeToHandle);
            MixedSQLNode mixedSQLNode = new MixedSQLNode(contents);
            String test = nodeToHandle.getStringAttribute(XMLBuilderTag.XML_ATTR_TEST);
            IfSQLNode ifSQLNode = new IfSQLNode(mixedSQLNode, test);
            targetContents.add(ifSQLNode);
        }
    }

    /**
     * 其他选项操作类
     * @author: huan
     * @Date: 2020-01-18
     * @Description:
     */
    private class OtherwiseHandler implements NodeHandler {
        public void handleNode(XNode nodeToHandle, List<SQLNode> targetContents) {
            List<SQLNode> contents = parseDynamicTags(nodeToHandle);
            MixedSQLNode mixedSQLNode = new MixedSQLNode(contents);
            targetContents.add(mixedSQLNode);
        }
    }

    /**
     * 多项选择操作类
     * @author: huan
     * @Date: 2020-01-18
     * @Description:
     */
    private class ChooseHandler implements NodeHandler {
        public void handleNode(XNode nodeToHandle, List<SQLNode> targetContents) {
            List<SQLNode> whenSQLNodes = new ArrayList<SQLNode>();
            List<SQLNode> otherwiseSQLNodes = new ArrayList<SQLNode>();
            handleWhenOtherwiseNodes(nodeToHandle, whenSQLNodes, otherwiseSQLNodes);
            SQLNode defaultSQLNode = getDefaultSqlNode(otherwiseSQLNodes);
            ChooseSQLNode chooseSQLNode = new ChooseSQLNode(whenSQLNodes, defaultSQLNode);
            targetContents.add(chooseSQLNode);
        }

        private void handleWhenOtherwiseNodes(XNode chooseSqlNode, List<SQLNode> ifSQLNodes, List<SQLNode> defaultSQLNodes) {
            List<XNode> children = chooseSqlNode.getChildren();
            for (XNode child : children) {
                String nodeName = child.getNode().getNodeName();
                NodeHandler handler = nodeHandlers.get(nodeName);
                if (handler instanceof IfHandler) {
                    handler.handleNode(child, ifSQLNodes);
                } else if (handler instanceof OtherwiseHandler) {
                    handler.handleNode(child, defaultSQLNodes);
                }
            }
        }

        private SQLNode getDefaultSqlNode(List<SQLNode> defaultSQLNodes) {
            SQLNode defaultSQLNode = null;
            if (defaultSQLNodes.size() == 1) {
                defaultSQLNode = defaultSQLNodes.get(0);
            } else if (defaultSQLNodes.size() > 1) {
                throw new RuntimeException("Too many default (otherwise) elements in choose statement.");
            }
            return defaultSQLNode;
        }
    }
}
