package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;

/**
 * http请求获取数据源建构类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class HttpTableBuilder implements XMLParser {
    // 单例实体
    private static HttpTableBuilder BUILDER = new HttpTableBuilder();

    private HttpTableBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-01-18
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.HttpTableBuilder
     * @Exception
     * @Description
     **/
    public static HttpTableBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
//        String src = node.getStringAttribute(CoreTag.XML_ATTR_SRC).replaceAll(CoreTag.SRC_PREFIX_HDFS, CoreTag.STRING_BLANK);
//        String json = HttpUtil.getRequest(src);
//
//        DataSourceTable table = new DataSourceTable();
//        table.setId(this.node.getStringAttribute(XML_ATTR_ID));
//        table.setAlias(this.node.getStringAttribute(XML_ATTR_ALIAS));
//        table.setFormat(this.node.getStringAttribute(XML_ATTR_FORMAT, XML_ATTR_FORMAT_TEXTFILE));
//
//        JSONObject jsonObject = new JSONObject(json);
//        table.setSrc(jsonObject.getString(HTTP_SRC));
//        JSONArray jsonArray = jsonObject.getJSONArray(HTTP_FIELDS);
//        Iterator<Object> iterator = jsonArray.iterator();
//        String field = null;
//        String[] spl = null;
//        while (iterator.hasNext()) {
//            field = iterator.next().toString();
//            spl = field.split(SPLIT_COMMA);
//            table.addField(spl[0], spl[1]);
//        }
//
//        this.config.addDataSource(table.getId(), table);
    }
}
