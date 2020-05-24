package org.eocencle.magnet.xmlbuilder.xmltags;

import java.util.List;

/**
 * 多项选择操作SQL节点类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class ChooseSQLNode implements SQLNode {
	private SQLNode defaultSQLNode;
	private List<SQLNode> ifSQLNodes;

	public ChooseSQLNode(List<SQLNode> ifSQLNodes, SQLNode defaultSQLNode) {
		this.ifSQLNodes = ifSQLNodes;
		this.defaultSQLNode = defaultSQLNode;
	}

	public boolean apply(DynamicContext context) {
		for (SQLNode sqlNode : this.ifSQLNodes) {
			if (sqlNode.apply(context)) {
				return true;
			}
		}
		if (this.ifSQLNodes != null) {
			this.defaultSQLNode.apply(context);
			return true;
		}
		return false;
	}
}
