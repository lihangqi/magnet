package org.eocencle.magnet.xmlbuilder.xmltags;

/**
 * SQL节点接口
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public interface SQLNode {
	/**
	 * 处理动态内容
	 * @Author huan
	 * @Date 2020-01-18
	 * @Param [context]
	 * @Return boolean
	 * @Exception
	 * @Description
	 **/
	boolean apply(DynamicContext context);
}
