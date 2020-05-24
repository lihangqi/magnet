package org.eocencle.magnet.xmlbuilder.xmltags;

import java.util.List;

/**
 * 混合SQL节点类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class MixedSQLNode implements SQLNode {
	private List<SQLNode> contents;

	public MixedSQLNode(List<SQLNode> contents) {
		this.contents = contents;
	}

	public boolean apply(DynamicContext context) {
		for (SQLNode sqlNode : contents) {
			sqlNode.apply(context);
		}
		return true;
	}
}
