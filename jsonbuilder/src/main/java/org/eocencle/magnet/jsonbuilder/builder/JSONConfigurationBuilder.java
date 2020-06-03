package org.eocencle.magnet.jsonbuilder.builder;

import com.alibaba.fastjson.JSONObject;
import org.eocencle.magnet.core.builder.ProjectConfigBuilder;
import org.eocencle.magnet.core.session.ProjectConfig;
import org.eocencle.magnet.jsonbuilder.session.JsonProjectConfig;

/**
 * JSON配置建构者类
 * @author: huan
 * @Date: 2020-05-25
 * @Description:
 */
public class JSONConfigurationBuilder extends ProjectConfigBuilder {
    // json解析对象
    JSONObject jsonParser;

    public JSONConfigurationBuilder(ProjectConfig config, String filePath) {
        this(config, (JSONObject) JSONObject.parse(filePath));
    }

    public JSONConfigurationBuilder(ProjectConfig config, JSONObject jsonParser) {
        super(config);
        this.jsonParser = jsonParser;
    }

    @Override
    public void parseParameter() {
        ParameterBuilder builder = ParameterBuilder.getInstance();
        builder.parse(this.jsonParser, (JsonProjectConfig) this.getConfig());
    }

    @Override
    public void parseDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.getInstance();
        builder.parse(this.jsonParser, (JsonProjectConfig) this.getConfig());
    }

    @Override
    public void parseWorkFlow() {
        WorkFlowBuilder builder = WorkFlowBuilder.getInstance();
        builder.parse(this.jsonParser, (JsonProjectConfig) this.getConfig());
    }
}
