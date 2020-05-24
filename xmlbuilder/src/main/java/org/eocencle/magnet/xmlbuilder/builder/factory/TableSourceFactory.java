package org.eocencle.magnet.xmlbuilder.builder.factory;

import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.builder.*;

/**
 * 数据源工厂类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class TableSourceFactory {
    /**
     * 获取表源实例
     * @Author huan
     * @Date 2020-01-18
     * @Param [style]
     * @Return org.eocencle.magnet.builder.xml.XMLParser
     * @Exception
     * @Description
     **/
    public static XMLParser getTableSourceBuilder(String style) {
        if (CoreTag.TABLE_STYLE_DEFAULT.equalsIgnoreCase(style)) {
            return DefaultTableBuilder.getInstance();
        } else if (CoreTag.TABLE_STYLE_FILE.equalsIgnoreCase(style)) {
            return FileTableBuilder.getInstance();
        } else if (CoreTag.TABLE_STYLE_HTTP.equalsIgnoreCase(style)) {
            return HttpTableBuilder.getInstance();
        } else if (CoreTag.TABLE_STYLE_DATABASE.equalsIgnoreCase(style)) {
            return DataBaseTableBuilder.getInstance();
        }
        throw new UnsupportedException("Incorrect configuration of value " + style + " of data source src!");
    }

}
