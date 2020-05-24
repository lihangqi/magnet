package org.eocencle.magnet.xmlbuilder.xmltags;

/**
 * 判断操作SQL节点类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class IfSQLNode implements SQLNode {
	private ExpressionEvaluator evaluator;
	private String test;
	private SQLNode contents;

	public IfSQLNode(SQLNode contents, String test) {
		this.test = test;
		this.contents = contents;
		this.evaluator = new ExpressionEvaluator();
	}

	public boolean apply(DynamicContext context) {
		if (this.evaluator.evaluateBoolean(this.test, context.getBindings())) {
			this.contents.apply(context);
			return true;
		} else {
			return false;
		}
	}
}
