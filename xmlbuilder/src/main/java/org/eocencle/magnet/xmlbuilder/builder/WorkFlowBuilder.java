package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作流建构类
 * @author: huan
 * @Date: 2020-01-14
 * @Description:
 */
public class WorkFlowBuilder implements XMLParser {
    // 单例实体
    private static WorkFlowBuilder BUILDER = new WorkFlowBuilder();

    private List<XMLParser> builders = new ArrayList<>();

    private WorkFlowBuilder() {
        this.builders.add(SQLBuilder.getInstance());
        this.builders.add(BranchBuilder.getInstance());
        this.builders.add(OutputBuilder.getInstance());
        this.builders.add(GroupBuilder.getInstance());
        this.builders.add(FilterBuilder.getInstance());
        this.builders.add(DistinctBuilder.getInstance());
        this.builders.add(OrderBuilder.getInstance());
        this.builders.add(UnionBuilder.getInstance());
        this.builders.add(JoinBuilder.getInstance());
        this.builders.add(SchemaBuilder.getInstance());
        this.builders.add(ValueMappersBuilder.getInstance());
        this.builders.add(SplitFieldToRowsBuilder.getInstance());
        this.builders.add(StringCutsBuilder.getInstance());
        this.builders.add(AddFieldsBuilder.getInstance());
        this.builders.add(AddSequenceBuilder.getInstance());
        this.builders.add(RowNumBuilder.getInstance());
        this.builders.add(QueryBuilder.getInstance());
    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-02-01
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.WorkFlowBuilder
     * @Exception
     * @Description
     **/
    public static WorkFlowBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        XNode workflowNode = node.evalNode(XMLBuilderTag.XML_EL_WORKFLOW);
        this.getElementsOrder(workflowNode.getChildren(), config);
        for (XMLParser builder: this.builders) {
            builder.parse(workflowNode, config);
        }
    }

    /**
     * 获取工作流元素顺序
     * @Author huan
     * @Date 2020-02-01
     * @Param [nodes, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void getElementsOrder(List<XNode> nodes, XmlProjectConfig config) {
        for (XNode node: nodes) {
            config.putWorkFlowInfo(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID), null);
        }
    }

}
