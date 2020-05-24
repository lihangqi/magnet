package org.eocencle.magnet.xmlbuilder.xmltags;

import org.eocencle.magnet.core.util.CoreTag;

/**
 * where操作SQL节点
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class WhereSQLNode extends TrimSQLNode {

	public WhereSQLNode(SQLNode contents) {
		super(contents, CoreTag.SQL_WHERE, "AND |OR |AND\n|OR\n|AND\r|OR\r", null, null);
	}

}
