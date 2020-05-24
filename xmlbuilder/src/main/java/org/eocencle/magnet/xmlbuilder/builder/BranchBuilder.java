package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.builder.ProjectConfigBuilder;
import org.eocencle.magnet.core.mapping.BranchInfo;
import org.eocencle.magnet.core.session.ProjectConfig;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * 分支建构类
 * @author: huan
 * @Date: 2020-01-29
 * @Description:
 */
public class BranchBuilder implements XMLParser {
    // 单例实体
    private static BranchBuilder BUILDER = new BranchBuilder();

    private BranchBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-01-18
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.BranchBuilder
     * @Exception
     * @Description
     **/
    public static BranchBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        try {
            this.parseElements(node.evalNodes(XMLBuilderTag.XML_EL_BRANCH), config);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析分支节点
     * @Author huan
     * @Date 2020-01-18
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseElements(List<XNode> nodes, XmlProjectConfig config) throws FileNotFoundException {
        if (null == nodes) {
            return ;
        }

        XNode valNode;
        BranchInfo branchInfo = null;
        ProjectConfigBuilder xmlBuilder = null;
        ProjectConfig pg = null;
        for (XNode node: nodes) {
            branchInfo = new BranchInfo();
            branchInfo.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            branchInfo.setSrc(node.getStringAttribute(XMLBuilderTag.XML_ATTR_SRC).replaceAll(CoreTag.SRC_PREFIX_FILE, CoreTag.STRING_BLANK));

            valNode = node.evalNode(XMLBuilderTag.XML_EL_DATASETS);
            for (XNode vNode: valNode.getChildren()) {
                branchInfo.addDataSet(new BranchInfo.DataSet(vNode.getStringAttribute(XMLBuilderTag.XML_ATTR_ID), vNode.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS)));
            }

            // 解析分支配置文件
            pg = new ProjectConfig();
            xmlBuilder = new XMLConfigurationBuilder(pg, branchInfo.getSrc());
            xmlBuilder.build();

            branchInfo.setProjectConfig(pg);
            config.putWorkFlowInfo(branchInfo.getId(), branchInfo);
        }
    }
}
