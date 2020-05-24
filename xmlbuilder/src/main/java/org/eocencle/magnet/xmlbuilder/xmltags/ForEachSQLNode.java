package org.eocencle.magnet.xmlbuilder.xmltags;

import org.eocencle.magnet.xmlbuilder.parsing.GenericTokenParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 循环操作SQL节点
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class ForEachSQLNode implements SQLNode {
	public static final String ITEM_PREFIX = "__frch_";
	private ExpressionEvaluator evaluator = new ExpressionEvaluator();
	private String collectionExpression;
	private SQLNode contents;
	private String open;
	private String close;
	private String separator;
	private String item;
	private String index;

	public ForEachSQLNode(SQLNode contents, String collectionExpression, String index,
                          String item, String open, String close, String separator) {
		this.collectionExpression = collectionExpression;
		this.contents = contents;
		this.open = open;
		this.close = close;
		this.separator = separator;
		this.index = index;
		this.item = item;
	}

	public boolean apply(DynamicContext context) {
		Map bindings = context.getBindings();
		Iterable iterable = this.evaluator.evaluateIterable(this.collectionExpression, bindings);
		if (!iterable.iterator().hasNext()) {
			return true;
		} else {
			boolean first = true;
			this.applyOpen(context);
			int i = 0;

			for (Iterator i$ = iterable.iterator(); i$.hasNext(); ++i) {
				Object o = i$.next();
				PrefixedContext prefixedContext;
				if (first) {
					prefixedContext = new PrefixedContext(context, "");
				} else if (this.separator != null) {
					prefixedContext = new PrefixedContext(context, this.separator);
				} else {
					prefixedContext = new PrefixedContext(context, "");
				}

				int uniqueNumber = prefixedContext.getUniqueNumber();
				if (o instanceof Entry) {
					Entry mapEntry = (Entry) o;
					this.applyIndex(prefixedContext, mapEntry.getKey(), uniqueNumber);
					this.applyItem(prefixedContext, mapEntry.getValue(), uniqueNumber);
				} else {
					this.applyIndex(prefixedContext, Integer.valueOf(i), uniqueNumber);
					this.applyItem(prefixedContext, o, uniqueNumber);
				}

				this.contents.apply(new FilteredDynamicContext(prefixedContext, this.index,
						this.item, uniqueNumber));
				if (first) {
					first = !((PrefixedContext) prefixedContext).isPrefixApplied();
				}
			}

			this.applyClose(context);
			return true;
		}
	}

	private void applyIndex(DynamicContext context, Object o, int i) {
		if (this.index != null) {
			context.bind(this.index, o);
			context.bind(itemizeItem(this.index, i), o);
		}

	}

	private void applyItem(DynamicContext context, Object o, int i) {
		if (this.item != null) {
			context.bind(this.item, o);
			context.bind(itemizeItem(this.item, i), o);
		}
	}

	private void applyOpen(DynamicContext context) {
		if (this.open != null) {
			context.appendSQL(this.open);
		}
	}

	private void applyClose(DynamicContext context) {
		if (this.close != null) {
			context.appendSQL(this.close);
		}
	}

	private static String itemizeItem(String item, int i) {
		return "__frch_" + item + "_" + i;
	}

	private class PrefixedContext extends DynamicContext {
		private DynamicContext delegate;
		private String prefix;
		private boolean prefixApplied;

		public PrefixedContext(DynamicContext delegate, String prefix) {
			super(null);
			this.delegate = delegate;
			this.prefix = prefix;
			this.prefixApplied = false;
		}

		public boolean isPrefixApplied() {
			return this.prefixApplied;
		}

		public Map<String, Object> getBindings() {
			return this.delegate.getBindings();
		}

		public void bind(String name, Object value) {
			this.delegate.bind(name, value);
		}

		public void appendSQL(String sql) {
			if (!this.prefixApplied && sql != null && sql.trim().length() > 0) {
				this.delegate.appendSQL(this.prefix);
				this.prefixApplied = true;
			}

			this.delegate.appendSQL(sql);
		}

		public String getSQL() {
			return this.delegate.getSQL();
		}

		public int getUniqueNumber() {
			return this.delegate.getUniqueNumber();
		}
	}

	private static class FilteredDynamicContext extends DynamicContext {
		private DynamicContext delegate;
		private int index;
		private String itemIndex;
		private String item;

		public FilteredDynamicContext(DynamicContext delegate, String itemIndex,
                                      String item, int i) {
			super(null);
			this.delegate = delegate;
			this.index = i;
			this.itemIndex = itemIndex;
			this.item = item;
		}

		public Map<String, Object> getBindings() {
			return this.delegate.getBindings();
		}

		public void bind(String name, Object value) {
			this.delegate.bind(name, value);
		}

		public String getSQL() {
			return this.delegate.getSQL();
		}

		public void appendSQL(String sql) {
			GenericTokenParser parser = new GenericTokenParser("#{", "}", (String content) -> {
				String newContent = content
						.replaceFirst("^\\s*" + ForEachSQLNode.FilteredDynamicContext.this.item + "(?![^.,:\\s])", ForEachSQLNode
								.itemizeItem(ForEachSQLNode.FilteredDynamicContext.this.item, ForEachSQLNode.FilteredDynamicContext.this.index));
				if (ForEachSQLNode.FilteredDynamicContext.this.itemIndex != null && newContent.equals(content)) {
					newContent = content.replaceFirst(
							"^\\s*" + ForEachSQLNode.FilteredDynamicContext.this.itemIndex + "(?![^.,:\\s])",
							ForEachSQLNode.itemizeItem(ForEachSQLNode.FilteredDynamicContext.this.itemIndex,
									ForEachSQLNode.FilteredDynamicContext.this.index));
				}

				return "#{" + newContent + "}";
			});
			this.delegate.appendSQL(parser.parse(sql));
		}

		public int getUniqueNumber() {
			return this.delegate.getUniqueNumber();
		}
	}
}
