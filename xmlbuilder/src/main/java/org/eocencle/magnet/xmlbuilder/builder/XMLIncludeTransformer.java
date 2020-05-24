package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * include标签转换
 * @author: huan
 * @Date: 2020-03-07
 * @Description:
 */
public class XMLIncludeTransformer {
	// 项目配置
	private XmlProjectConfig config;

	public XMLIncludeTransformer(XmlProjectConfig config) {
		this.config = config;
	}

	/**
	 * 替换include标签
	 * @Author huan
	 * @Date 2020-03-07
	 * @Param [source]
	 * @Return void
	 * @Exception
	 * @Description
	 **/
	public void applyIncludes(Node source) {
		if (XMLBuilderTag.XML_EL_INCLUDE.equals(source.getNodeName())) {
			Node toInclude = this.findSQLFragment(this.getStringAttribute(source, XMLBuilderTag.XML_ATTR_REF));
			this.applyIncludes(toInclude);
			if (toInclude.getOwnerDocument() != source.getOwnerDocument()) {
				toInclude = source.getOwnerDocument().importNode(toInclude, true);
			}
			source.getParentNode().replaceChild(toInclude, source);
			while (toInclude.hasChildNodes()) {
				toInclude.getParentNode().insertBefore(toInclude.getFirstChild(), toInclude);
			}
			toInclude.getParentNode().removeChild(toInclude);
		} else if (source.getNodeType() == Node.ELEMENT_NODE) {
			NodeList children = source.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				this.applyIncludes(children.item(i));
			}
		}
	}

	/**
	 * 查找SQL碎片
	 * @Author huan
	 * @Date 2020-03-07
	 * @Param [ref]
	 * @Return org.w3c.dom.Node
	 * @Exception
	 * @Description
	 **/
	private Node findSQLFragment(String ref) {
		XNode nodeToInclude = this.config.getFragmentInfo(ref);
		return nodeToInclude.getNode().cloneNode(true);
	}

	/**
	 * 获取节点属性
	 * @Author huan
	 * @Date 2020-03-07
	 * @Param [node, name]
	 * @Return java.lang.String
	 * @Exception
	 * @Description
	 **/
	private String getStringAttribute(Node node, String name) {
		return node.getAttributes().getNamedItem(name).getNodeValue();
	}
}
