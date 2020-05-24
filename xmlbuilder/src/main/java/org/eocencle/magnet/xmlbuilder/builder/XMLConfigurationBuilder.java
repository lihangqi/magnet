package org.eocencle.magnet.xmlbuilder.builder;

import org.eocencle.magnet.core.builder.ProjectConfigBuilder;
import org.eocencle.magnet.core.component.wrapper.MatchWrapper;
import org.eocencle.magnet.core.component.wrapper.WrapperManager;
import org.eocencle.magnet.core.mapping.SQLInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;
import org.eocencle.magnet.core.session.ProjectConfig;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;
import org.eocencle.magnet.xmlbuilder.parsing.XPathParser;
import org.eocencle.magnet.xmlbuilder.session.XmlProjectConfig;
import org.eocencle.magnet.xmlbuilder.util.XMLBuilderTag;
import org.eocencle.magnet.xmlbuilder.wrapper.ParameterReplaceWrapper;
import org.eocencle.magnet.xmlbuilder.wrapper.SQLScriptParserWrapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * XML配置建构者类
 * @author: huan
 * @Date: 2020-01-13
 * @Description:
 */
public class XMLConfigurationBuilder extends ProjectConfigBuilder {
    // 节点
    private XNode node;

    public XMLConfigurationBuilder(ProjectConfig config, String filePath) throws FileNotFoundException {
        this(config, new FileInputStream(filePath));
    }

    public XMLConfigurationBuilder(ProjectConfig config, InputStream stream) {
        this(config, new XPathParser(stream));
    }

    public XMLConfigurationBuilder(ProjectConfig config, XPathParser parser) {
        this(config, parser.evalNode(XMLBuilderTag.XML_EL_PROJECT));
    }

    public XMLConfigurationBuilder(ProjectConfig config, XNode node) {
        super(config);
        this.node = node;
        config.putParameterInfo(CoreTag.MAGNET_CONFIG_READ_MODE, CoreTag.CONFIG_READ_MODE_XML);
    }

    @Override
    public void build() {
        this.parseFragment();
        super.build();
    }

    /**
     * 解析SQL碎片
     * @Author huan
     * @Date 2020-03-07
     * @Param []
     * @Return void
     * @Exception
     * @Description
     **/
    public void parseFragment() {
        FragmentBuilder builder = FragmentBuilder.getInstance();
        builder.parse(this.node, (XmlProjectConfig) this.getConfig());
    }

    @Override
    public void parseParameter() {
        ParameterBuilder builder = ParameterBuilder.getInstance();
        builder.parse(this.node, (XmlProjectConfig) this.getConfig());
    }

    @Override
    public void parseDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.getInstance();
        builder.parse(this.node, (XmlProjectConfig) this.getConfig());
    }

    @Override
    public void parseWorkFlow() {
        WorkFlowBuilder builder = WorkFlowBuilder.getInstance();
        builder.parse(this.node, (XmlProjectConfig) this.getConfig());
    }

    @Override
    public void register() {
        WrapperManager.registerComponentWrapper(new MatchWrapper(XMLBuilderTag.XML_EL_SQL, SQLScriptParserWrapper.class) {
            @Override
            public boolean check(WorkStageInfo info) {
                if (info instanceof SQLInfo) {
                    return true;
                }
                return false;
            }
        });
        WrapperManager.registerComponentWrapper(new MatchWrapper(CoreTag.STRING_STAR, ParameterReplaceWrapper.class) {
            @Override
            public boolean check(WorkStageInfo info) {
                return true;
            }
        });
    }
}
