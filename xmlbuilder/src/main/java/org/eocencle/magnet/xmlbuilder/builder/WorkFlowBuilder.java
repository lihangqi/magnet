package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

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

    private WorkFlowBuilder() {

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
        this.parseSQLElements(workflowNode, config);
        this.parseBranchElements(workflowNode, config);
        this.parseOutputElements(workflowNode, config);
        this.parseGroupElements(workflowNode, config);
        this.parseFilterElements(workflowNode, config);
        this.parseDistinctElements(workflowNode, config);
        this.parseOrderElements(workflowNode, config);
        this.parseUnionElements(workflowNode, config);
        this.parseJoinElements(workflowNode, config);
        this.parseSchemaElements(workflowNode, config);
        this.parseValueMappersElements(workflowNode, config);
        this.parseSplitFieldToRowsElements(workflowNode, config);
        this.parseStringCutsElements(workflowNode, config);
        this.parseAddFieldsElements(workflowNode, config);
        this.parseAddSequenceElements(workflowNode, config);
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

    /**
     * 解析SQL元素
     * @Author huan
     * @Date 2020-02-01
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseSQLElements(XNode node, XmlProjectConfig config) {
        SQLBuilder builder = SQLBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析分支元素
     * @Author huan
     * @Date 2020-02-01
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseBranchElements(XNode node, XmlProjectConfig config) {
        BranchBuilder builder = BranchBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析输出元素
     * @Author huan
     * @Date 2020-02-01
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseOutputElements(XNode node, XmlProjectConfig config) {
        OutputBuilder builder = OutputBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析分组元素
     * @Author huan
     * @Date 2020-03-10
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseGroupElements(XNode node, XmlProjectConfig config) {
        GroupBuilder builder = GroupBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析过滤元素
     * @Author huan
     * @Date 2020-03-13
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseFilterElements(XNode node, XmlProjectConfig config) {
        FilterBuilder builder = FilterBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析去重元素
     * @Author huan
     * @Date 2020-03-15
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseDistinctElements(XNode node, XmlProjectConfig config) {
        DistinctBuilder builder = DistinctBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析排序元素
     * @Author huan
     * @Date 2020-03-16
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseOrderElements(XNode node, XmlProjectConfig config) {
        OrderBuilder builder = OrderBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析合并元素
     * @Author huan
     * @Date 2020-04-03
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseUnionElements(XNode node, XmlProjectConfig config) {
        UnionBuilder builder = UnionBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析关联元素
     * @Author huan
     * @Date 2020-04-04
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseJoinElements(XNode node, XmlProjectConfig config) {
        JoinBuilder builder = JoinBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析Schema元素
     * @Author huan
     * @Date 2020-04-06
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseSchemaElements(XNode node, XmlProjectConfig config) {
        SchemaBuilder builder = SchemaBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析值映射元素
     * @Author huan
     * @Date 2020-04-08
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseValueMappersElements(XNode node, XmlProjectConfig config) {
        ValueMappersBuilder builder = ValueMappersBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析列分隔转行元素
     * @Author huan
     * @Date 2020-04-13
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseSplitFieldToRowsElements(XNode node, XmlProjectConfig config) {
        SplitFieldToRowsBuilder builder = SplitFieldToRowsBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析字符串切割元素
     * @Author huan
     * @Date 2020-04-24
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseStringCutsElements(XNode node, XmlProjectConfig config) {
        StringCutsBuilder builder = StringCutsBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析添加字段元素
     * @Author huan
     * @Date 2020-04-28
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseAddFieldsElements(XNode node, XmlProjectConfig config) {
        AddFieldsBuilder builder = AddFieldsBuilder.getInstance();
        builder.parse(node, config);
    }

    /**
     * 解析添加序列元素
     * @Author huan
     * @Date 2020-04-30
     * @Param [node, config]
     * @Return void
     * @Exception
     * @Description
     **/
    private void parseAddSequenceElements(XNode node, XmlProjectConfig config) {
        AddSequenceBuilder builder = AddSequenceBuilder.getInstance();
        builder.parse(node, config);
    }
}
