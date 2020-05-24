package org.eocencle.magnet.xmlbuilder.mapping;

import java.util.Map;

/**
 * SQL源接口
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public interface SQLSource {
	/**
	 * SQL源
	 * @Author huan
	 * @Date 2020-01-18
	 * @Param [params]
	 * @Return org.eocencle.magnet.mapping.BoundSQL
	 * @Exception
	 * @Description
	 **/
	BoundSQL getBoundSQL(Map<String, Object> params);

}
