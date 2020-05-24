package org.eocencle.magnet.xmlbuilder.xmltags;

import org.eocencle.magnet.xmlbuilder.mapping.SQLSource;
import org.eocencle.magnet.xmlbuilder.parsing.GenericTokenParser;
import org.eocencle.magnet.xmlbuilder.parsing.TokenHandler;

import java.util.Map;

/**
 * SQL源建构类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class SQLSourceBuilder {

	public SQLSource parse(String originalSql, Class<?> parameterType, Map<String, Object> additionalParameters) {
		ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler(parameterType, additionalParameters);
		GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
		String json = parser.parse(originalSql);
		return new StaticSQLSource(json);
	}

	private static class ParameterMappingTokenHandler implements TokenHandler {

		private Class<?> parameterType;

		public ParameterMappingTokenHandler(Class<?> parameterType, Map<String, Object> additionalParameters) {
			this.parameterType = parameterType;
			//this.metaParameters = config.newMetaObject(additionalParameters);
		}

		public String handleToken(String content) {
			return "?";
		}
	}
}
