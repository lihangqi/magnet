package org.eocencle.magnet.xmlbuilder.xmltags;

import java.util.*;

/**
 * trim操作SQL节点类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class TrimSQLNode implements SQLNode {
	private SQLNode contents;
	private String prefix;
	private String suffix;
	private List<String> prefixesToOverride = new ArrayList<String>();
	private List<String> suffixesToOverride = new ArrayList<String>();

	public TrimSQLNode(SQLNode contents, String prefix, String prefixesToOverride, String suffix, String suffixesToOverride) {
		this.contents = contents;
		this.prefix = prefix;
		this.prefixesToOverride = parseOverrides(prefixesToOverride);
		this.suffix = suffix;
		this.suffixesToOverride = parseOverrides(suffixesToOverride);
	}

	public boolean apply(DynamicContext context) {
		FilteredDynamicContext filteredDynamicContext = new FilteredDynamicContext(context);
		boolean result = contents.apply(filteredDynamicContext);
		filteredDynamicContext.applyAll();
		return result;
	}

	private List<String> parseOverrides(String overrides) {
		if (overrides != null) {
			final StringTokenizer parser = new StringTokenizer(overrides, "|", false);
			return new ArrayList<String>() {
				private static final long serialVersionUID = -2504816393625384165L;

					{
					while (parser.hasMoreTokens()) {
						add(parser.nextToken().toUpperCase(Locale.ENGLISH));
					}
				}
			};
		}
		return Collections.emptyList();
	}

	private class FilteredDynamicContext extends DynamicContext {
		private DynamicContext delegate;
		private boolean prefixApplied;
		private boolean suffixApplied;
		private StringBuilder jsonBuffer;

		public FilteredDynamicContext(DynamicContext delegate) {
			super(null);
			this.delegate = delegate;
			this.prefixApplied = false;
			this.suffixApplied = false;
			this.jsonBuffer = new StringBuilder();
		}

		public void applyAll() {
			jsonBuffer = new StringBuilder(jsonBuffer.toString().trim());
			String trimmedUppercaseJson = jsonBuffer.toString().toUpperCase(Locale.ENGLISH);
			if (trimmedUppercaseJson.length() > 0) {
				applyPrefix(jsonBuffer, trimmedUppercaseJson);
				applySuffix(jsonBuffer, trimmedUppercaseJson);
			}
			delegate.appendSQL(jsonBuffer.toString());
		}

		@Override
		public Map<String, Object> getBindings() {
			return delegate.getBindings();
		}

		@Override
		public void bind(String name, Object value) {
			delegate.bind(name, value);
		}

		@Override
		public int getUniqueNumber() {
			return delegate.getUniqueNumber();
		}

		@Override
		public void appendSQL(String sql) {
			jsonBuffer.append(sql);
		}

		@Override
		public String getSQL() {
			return delegate.getSQL();
		}

		private void applyPrefix(StringBuilder json, String trimmedUppercaseJson) {
			if (!prefixApplied) {
				prefixApplied = true;
				for (String toRemove : prefixesToOverride) {
					if (trimmedUppercaseJson.startsWith(toRemove)) {
						json.delete(0, toRemove.trim().length());
						break;
					}
				}
				if (prefix != null) {
					json.insert(0, " ");
					json.insert(0, prefix);
				}
			}
		}

		private void applySuffix(StringBuilder json, String trimmedUppercaseJson) {
			if (!suffixApplied) {
				suffixApplied = true;
				for (String toRemove : suffixesToOverride) {
					if (trimmedUppercaseJson.endsWith(toRemove) || trimmedUppercaseJson.endsWith(toRemove.trim())) {
						int start = json.length() - toRemove.trim().length();
						int end = json.length();
						json.delete(start, end);
						break;
					}
				}
				if (suffix != null) {
					json.append(" ");
					json.append(suffix);
				}
			}
		}

	}
}
