package org.eocencle.magnet.xmlbuilder.xmltags;

import org.eocencle.magnet.xmlbuilder.parsing.GenericTokenParser;
import org.eocencle.magnet.xmlbuilder.parsing.TokenHandler;

/**
 * 文本SQL节点类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class TextSQLNode implements SQLNode {
	private String text;

	public TextSQLNode(String text) {
		this.text = text;
	}

	public boolean apply(DynamicContext context) {
		GenericTokenParser parser = new GenericTokenParser("${", "}", new BindingTokenParser(context));
		context.appendSQL(parser.parse(text));
		return true;
	}

	private static class BindingTokenParser implements TokenHandler {

		private DynamicContext context;

		public BindingTokenParser(DynamicContext context) {
			this.context = context;
		}

		public String handleToken(String content) {
			Object parameter = context.getBindings().get("_parameter");
			if (parameter == null) {
				context.getBindings().put("value", null);
			} else if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
				context.getBindings().put("value", parameter);
			}
			Object value = OgnlCache.getValue(content, context.getBindings());
			return (value == null ? "" : String.valueOf(value));
		}
	}
}
