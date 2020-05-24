package org.eocencle.magnet.xmlbuilder.parsing;

/**
 * token处理接口
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public interface TokenHandler {
	/**
	 * token处理
	 * @Author huan
	 * @Date 2020-01-18
	 * @Param [content]
	 * @Return java.lang.String
	 * @Exception
	 * @Description
	 **/
	String handleToken(String content);
}
