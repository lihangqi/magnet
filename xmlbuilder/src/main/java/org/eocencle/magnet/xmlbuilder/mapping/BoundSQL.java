package org.eocencle.magnet.xmlbuilder.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 边界SQL
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class BoundSQL {
	private String sql;
	private Object parameterObject;
	private Map<String, Object> additionalParameters;
	private Map<String, Object> metaParameters;

	public BoundSQL(String sql) {
		this(sql, null);
	}

	public BoundSQL(String sql, Object parameterObject) {
		this.sql = sql;
		this.parameterObject = parameterObject;
		this.additionalParameters = new HashMap<String, Object>();
		this.metaParameters = new HashMap<String, Object>();
	}

	public String getSql() {
		return this.sql;
	}

	public Object getParameterObject() {
		return this.parameterObject;
	}

	public boolean hasAdditionalParameter(String name) {
		//return this.metaParameters.hasGetter(name);
		return false;
	}

	public void setAdditionalParameter(String name, Object value) {
		this.metaParameters.put(name, value);
	}

	public Object getAdditionalParameter(String name) {
		return this.metaParameters.get(name);
	}
}
