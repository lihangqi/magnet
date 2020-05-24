package org.eocencle.magnet.xmlbuilder.xmltags;

import org.eocencle.magnet.xmlbuilder.mapping.BoundSQL;
import org.eocencle.magnet.xmlbuilder.mapping.SQLSource;

import java.util.Map;

/**
 * 静态SQL源类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class StaticSQLSource implements SQLSource {
	private String sql;

	public StaticSQLSource(String sql) {
		this.sql = sql;
	}

	public BoundSQL getBoundSQL(Map<String, Object> params) {
		return new BoundSQL(this.sql, params);
	}
}
