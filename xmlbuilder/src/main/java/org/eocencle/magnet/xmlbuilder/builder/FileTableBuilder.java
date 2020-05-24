package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.parsing.XPathParser;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 文件引用数据源建构类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class FileTableBuilder implements XMLParser {
    // 单例实体
    private static FileTableBuilder BUILDER = new FileTableBuilder();

    private FileTableBuilder() {

    }

    /**
     * 获取单例实体
     * @Author huan
     * @Date 2020-01-18
     * @Param []
     * @Return org.eocencle.magnet.builder.xml.FileTableBuilder
     * @Exception
     * @Description
     **/
    public static FileTableBuilder getInstance() {
        return BUILDER;
    }

    @Override
    public void parse(XNode node, XmlProjectConfig config) {
        try {
            String src = node.getStringAttribute(XMLBuilderTag.XML_ATTR_REF).replace(CoreTag.SRC_PREFIX_FILE, CoreTag.STRING_BLANK);
            XPathParser parser = new XPathParser(new FileInputStream(new File(src)));
            TableInfo table = this.getFileTable(parser.evalNode(XMLBuilderTag.XML_EL_TABLE));
            table.setId(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ID));
            table.setAlias(node.getStringAttribute(XMLBuilderTag.XML_ATTR_ALIAS));
            config.putDataSourceInfo(table.getId(), table);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析文件节点
     * @Author huan
     * @Date 2020-01-18
     * @Param [fileNode]
     * @Return org.eocencle.magnet.mapping.TableInfo
     * @Exception
     * @Description
     **/
    private TableInfo getFileTable(XNode fileNode) {
        TableInfo table = new TableInfo();
        table.setStyle(CoreTag.OUTPUT_STYLE_FILE);
        table.setSrc(fileNode.getStringAttribute(XMLBuilderTag.XML_ATTR_SRC));
        table.setFormat(fileNode.getStringAttribute(XMLBuilderTag.XML_ATTR_FORMAT, CoreTag.FILE_FORMAT_TEXTFILE));
        table.setSeparator(fileNode.getStringAttribute(XMLBuilderTag.XML_ATTR_SEPARATOR, CoreTag.SPLIT_INVISIBLE1));
        for (XNode childNode: fileNode.evalNode(XMLBuilderTag.XML_EL_FIELDS).getChildren()) {
            table.addField(new DataSourceField(childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_NAME),
                    childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_TYPE, CoreTag.TABLE_FIELD_TYPE_STRING),
                    childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_PRECISION, null),
                    childNode.getStringAttribute(XMLBuilderTag.XML_ATTR_FORMAT, "yyyy-MM-dd HH:mm:ss")));
        }
        return table;
    }
}
