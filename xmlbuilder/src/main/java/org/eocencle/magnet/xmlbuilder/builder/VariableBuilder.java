package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.InfoParam;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.ArrayList;
import java.util.List;

/**
 * 变量建构类
 * @author: huan
 * @Date: 2020-01-17
 * @Description:
 */
public class VariableBuilder implements XMLParser {
    // 单例实体
    private static VariableBuilder BUILDER = new VariableBuilder();

    private VariableBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-01-18
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.VariableBuilder
     * @Exception
     * @Description
     **/
    public static VariableBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        List<XNode> nodes = node.getChildren();
        List<XNode> child = null;
        for (XNode n: nodes) {
            // 获取参数
            child = n.getChildren();
            if (child.isEmpty()) {
                config.putParameterInfo(n.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY),
                        n.getStringAttribute(XMLBuilderTag.XML_ATTR_VALUE));
            } else if (XMLBuilderTag.XML_EL_LIST.equals(child.get(0).getName())) {
                config.putParameterInfo(n.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY),
                        this.parseList(child.get(0)));
            } else if (XMLBuilderTag.XML_EL_MAP.equals(child.get(0).getName())) {
                config.putParameterInfo(n.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY),
                        this.parseMap(child.get(0)));
            }
        }
    }

    /**
     * 解析参数元素
     * @Author huan
     * @Date 2020-05-06
     * @Param [nodes]
     * @Return org.eocencle.magnet.core.util.StrictMap<org.eocencle.magnet.core.mapping.InfoParam>
     * @Exception
     * @Description
     */
    public StrictMap<InfoParam> parseElements(List<XNode> nodes) {
        StrictMap<InfoParam> result = new StrictMap<>("params");
        List<XNode> child = null;
        for (XNode n: nodes) {
            // 获取参数
            child = n.getChildren();
            if (child.isEmpty()) {
                result.put(n.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY), this.parseParamSingle(n));
            } else if (XMLBuilderTag.XML_EL_LIST.equals(child.get(0).getName())) {
                result.put(n.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY), this.parseParamList(n));
            } else if (XMLBuilderTag.XML_EL_MAP.equals(child.get(0).getName())) {
                result.put(n.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY), this.parseParamMap(n));
            }
        }
        return result;
    }

    /**
     * 解析list变量
     * @Author huan
     * @Date 2020-03-07
     * @Param [node]
     * @Return java.util.List<java.lang.String>
     * @Exception
     * @Description
     **/
    private List<String> parseList(XNode node) {
        List<String> list = new ArrayList<>();
        for (XNode n: node.getChildren()) {
            list.add(n.getStringBody());
        }
        return list;
    }

    /**
     * 解析map变量
     * @Author huan
     * @Date 2020-03-07
     * @Param [node]
     * @Return org.eocencle.magnet.util.StrictMap<java.lang.String>
     * @Exception
     * @Description
     **/
    private StrictMap<String> parseMap(XNode node) {
        StrictMap<String> map = new StrictMap<String>("Custom map");
        for (XNode n: node.getChildren()) {
            map.put(n.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY), n.getStringAttribute(XMLBuilderTag.XML_ATTR_VALUE));
        }
        return map;
    }

    /**
     * 解析单一参数
     * @Author huan
     * @Date 2020-05-06
     * @Param [node]
     * @Return org.eocencle.magnet.core.mapping.InfoParam
     * @Exception
     * @Description
     */
    private InfoParam parseParamSingle(XNode node) {
        return new InfoParam(node.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY),
                node.getStringAttribute(XMLBuilderTag.XML_ATTR_VALUE));
    }

    /**
     * 解析列表参数
     * @Author huan
     * @Date 2020-05-06
     * @Param [node]
     * @Return org.eocencle.magnet.core.mapping.InfoParam
     * @Exception
     * @Description
     */
    private InfoParam parseParamList(XNode node) {
        List<String> list = new ArrayList<>();
        for (XNode n: node.evalNode(XMLBuilderTag.XML_EL_LIST).getChildren()) {
            list.add(n.getStringBody());
        }
        InfoParam infoParam = new InfoParam();
        infoParam.setKey(node.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY));
        infoParam.setList(list);
        return infoParam;
    }

    /**
     * 解析映射参数
     * @Author huan
     * @Date 2020-05-06
     * @Param [node]
     * @Return org.eocencle.magnet.core.mapping.InfoParam
     * @Exception
     * @Description
     */
    private InfoParam parseParamMap(XNode node) {
        StrictMap<String> map = new StrictMap<String>("Map params");
        for (XNode n: node.evalNode(XMLBuilderTag.XML_EL_MAP).getChildren()) {
            map.put(n.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY), n.getStringAttribute(XMLBuilderTag.XML_ATTR_VALUE));
        }
        InfoParam infoParam = new InfoParam();
        infoParam.setKey(node.getStringAttribute(XMLBuilderTag.XML_ATTR_KEY));
        infoParam.setMap(map);
        return infoParam;
    }
}
