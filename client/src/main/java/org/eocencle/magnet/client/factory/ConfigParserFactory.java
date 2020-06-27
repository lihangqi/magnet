package org.eocencle.magnet.client.factory;

import org.eocencle.magnet.core.builder.ProjectConfigBuilder;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.session.ProjectConfig;
import org.eocencle.magnet.jsonbuilder.builder.JSONConfigurationBuilder;
import org.eocencle.magnet.jsonbuilder.session.JsonProjectConfig;
import org.eocencle.magnet.xmlbuilder.builder.XMLConfigurationBuilder;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;

import java.io.FileNotFoundException;

/**
 * 配置文件解析工厂类
 * @author: huan
 * @Date: 2020-06-14
 * @Description:
 */
public class ConfigParserFactory {
    /**
     * 使用适当的解析器解析配置
     * @author: huan
     * @Date: 2020-06-14
     * @Description:
     */
    public static ProjectConfig getProjectConfig(String filePath) throws FileNotFoundException {
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
        ProjectConfigBuilder builder = null;
        // 创建项目构建工具
        if ("xml".equalsIgnoreCase(suffix)) {
            builder = new XMLConfigurationBuilder(new XmlProjectConfig(), filePath);
        } else if ("json".equalsIgnoreCase(suffix)) {
            builder = new JSONConfigurationBuilder(new JsonProjectConfig(), filePath);
        } else {
            throw new UnsupportedException(suffix + " parser not supported");
        }
        // 构建项目
        builder.build();
        // 获取配置信息
        return builder.getConfig();
    }

}
