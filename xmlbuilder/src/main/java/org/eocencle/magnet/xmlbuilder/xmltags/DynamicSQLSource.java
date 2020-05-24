package org.eocencle.magnet.xmlbuilder.xmltags;

import org.eocencle.magnet.xmlbuilder.mapping.BoundSQL;
import org.eocencle.magnet.xmlbuilder.mapping.SQLSource;

import java.util.Map;

/**
 * 动态SQL源类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class DynamicSQLSource implements SQLSource {
	// SQL根节点
	private SQLNode rootSQLNode;

	public DynamicSQLSource(SQLNode rootSQLNode) {
		this.rootSQLNode = rootSQLNode;
	}

	@Override
	public BoundSQL getBoundSQL(Map<String, Object> params) {
		DynamicContext context = new DynamicContext(params);
		this.rootSQLNode.apply(context);
		SQLSourceBuilder sqlSourceParser = new SQLSourceBuilder();
		Class<?> parameterType = params == null ? Object.class : params.getClass();
		SQLSource sqlSource = sqlSourceParser.parse(context.getSQL(), parameterType, context.getBindings());
		BoundSQL boundSQL = sqlSource.getBoundSQL(params);
		for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
			boundSQL.setAdditionalParameter(entry.getKey(), entry.getValue());
		}
		return boundSQL;
	}
}
