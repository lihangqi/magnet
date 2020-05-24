package org.eocencle.magnet.core.builder;

import org.eocencle.magnet.core.component.wrapper.WrapperRegister;
import org.eocencle.magnet.core.session.ProjectConfig;

/**
 * 配置建构者抽象类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public abstract class ProjectConfigBuilder implements WrapperRegister {
    // 项目配置
    private ProjectConfig config;

    public ProjectConfigBuilder(ProjectConfig config) {
        this.config = config;
    }

    /**
     * 项目构建
     * @Author huan
     * @Date 2020-02-02
     * @Param []
     * @Return void
     * @Exception
     * @Description
     **/
    public void build() {
        this.parseParameter();
        this.parseDataSource();
        this.parseWorkFlow();
        this.register();
    }

    /**
     * 解析参数信息
     * @Author huan
     * @Date 2020-1-12
     * @Param []
     * @Return void
     * @Exception
     * @Description
     **/
    public abstract void parseParameter();

    /**
     * 解析数据源
     * @Author huan
     * @Date 2020-1-12
     * @Param []
     * @Return void
     * @Exception
     * @Description
     **/
    public abstract void parseDataSource();

    /**
     * 解析工作流
     * @Author huan
     * @Date 2020-1-12
     * @Param []
     * @Return void
     * @Exception
     * @Description
     **/
    public abstract void parseWorkFlow();

    public ProjectConfig getConfig() {
        return config;
    }

    @Override
    public void register() {

    }
}
