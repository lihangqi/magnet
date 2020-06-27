package org.eocencle.magnet.jsonbuilder.builder;

import com.alibaba.fastjson.JSONObject;
import org.eocencle.magnet.core.builder.ProjectConfigBuilder;
import org.eocencle.magnet.core.component.wrapper.MatchWrapper;
import org.eocencle.magnet.core.component.wrapper.WrapperManager;
import org.eocencle.magnet.core.mapping.SQLInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;
import org.eocencle.magnet.core.session.ProjectConfig;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.jsonbuilder.session.JsonProjectConfig;
import org.eocencle.magnet.jsonbuilder.util.JSONBuilderTag;
import org.eocencle.magnet.jsonbuilder.wrapper.TableNameReplaceWrapper;

import java.io.*;

/**
 * JSON配置建构者类
 * @author: huan
 * @Date: 2020-05-25
 * @Description:
 */
public class JSONConfigurationBuilder extends ProjectConfigBuilder {
    // json解析对象
    JSONObject jsonParser;

    public JSONConfigurationBuilder(ProjectConfig config, String filePath) throws FileNotFoundException {
        this(config, (JSONObject) JSONObject.parse(readJsonFile(filePath)));
    }

    public JSONConfigurationBuilder(ProjectConfig config, JSONObject jsonParser) {
        super(config);
        this.jsonParser = jsonParser;
        config.putParameterInfo(CoreTag.MAGNET_CONFIG_READ_MODE, CoreTag.CONFIG_READ_MODE_JSON);
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

    @Override
    public void register() {
        WrapperManager.registerComponentWrapper(new MatchWrapper(JSONBuilderTag.JSON_ATTR_SQL, TableNameReplaceWrapper.class) {
            @Override
            public boolean check(WorkStageInfo info) {
                if (info instanceof SQLInfo) {
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 读取JSON文件
     * @Author huan
     * @Date 2020-06-22
     * @Param [fileName]
     * @Return java.lang.String
     * @Exception
     * @Description
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
