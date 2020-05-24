package org.eocencle.magnet.xmlbuilder.xmltags;

import ognl.OgnlContext;
import ognl.OgnlException;
import ognl.OgnlRuntime;
import ognl.PropertyAccessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态SQL上下文类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class DynamicContext {
	public static final String PARAMETER_OBJECT_KEY = "_parameter";

	static {
		OgnlRuntime.setPropertyAccessor(ContextMap.class, new ContextAccessor());
	}

	private ContextMap bindings = null;
	private final StringBuilder sqlBuilder = new StringBuilder();
	private int uniqueNumber = 0;

	public DynamicContext(Object parameterObject) {
		/*if (parameterObject != null && !(parameterObject instanceof Map)) {
			MetaObject metaObject = config.newMetaObject(parameterObject);
			this.bindings = new ContextMap(metaObject);
		} else {
			this.bindings = new ContextMap(null);
		}*/
		this.bindings = new ContextMap();
		bindings.put(PARAMETER_OBJECT_KEY, parameterObject);
	}

	public Map<String, Object> getBindings() {
		return this.bindings;
	}

	public void bind(String name, Object value) {
		this.bindings.put(name, value);
	}

	public void appendSQL(String sql) {
		this.sqlBuilder.append(sql);
	}

	public String getSQL() {
		return this.sqlBuilder.toString().trim();
	}

	public int getUniqueNumber() {
		return uniqueNumber++;
	}

	static class ContextMap extends HashMap<String, Object> {
		private static final long serialVersionUID = 2977601501966151582L;

		/*private MetaObject parameterMetaObject;
		public ContextMap(MetaObject parameterMetaObject) {
			this.parameterMetaObject = parameterMetaObject;
		}*/

		@Override
		public Object get(Object key) {
			String strKey = (String) key;
			if (super.containsKey(strKey)) {
				return super.get(strKey);
			}
	
			/*if (parameterMetaObject != null) {
				Object object = parameterMetaObject.getValue(strKey);
				if (object != null) {
					super.put(strKey, object);
				}
	
				return object;
			}*/
	
			return null;
		}
	}

	static class ContextAccessor implements PropertyAccessor {

		public Object getProperty(Map context, Object target, Object name) throws OgnlException {
			Map map = (Map) target;
	
			Object result = map.get(name);
			if (result != null) {
				return result;
			}
	
			Object parameterObject = map.get(PARAMETER_OBJECT_KEY);
			if (parameterObject instanceof Map) {
				return ((Map)parameterObject).get(name);
			}
	
			return null;
		}

		public void setProperty(Map context, Object target, Object name, Object value)
			throws OgnlException {
			Map map = (Map) target;
			map.put(name, value);
		}

		public String getSourceAccessor(OgnlContext arg0, Object arg1, Object arg2) {
			return null;
		}

		public String getSourceSetter(OgnlContext arg0, Object arg1, Object arg2) {
			return null;
		}
	}
}
