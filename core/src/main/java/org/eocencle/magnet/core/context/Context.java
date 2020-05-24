package org.eocencle.magnet.core.context;

import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;

/**
 * 上下文环境抽象类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public abstract class Context {
    // 应用名称
    private String appName;
    // 环境模式
    private String envMode;
    // 处理模式
    private String processMode;
    // 持续时间
    private Integer duration;
    // SQL引擎
    private String sqlEngine;

    /**
     * 初始化环境
     * @Author huan
     * @Date 2020-04-10
     * @Param [params]
     * @Return void
     * @Exception
     * @Description
     **/
    public void init(StrictMap<Object> params) {
        this.appName = params.get(CoreTag.MAGNET_NAME).toString();
        this.envMode = params.get(CoreTag.MAGNET_ENV_MODE).toString();
        this.processMode = params.get(CoreTag.MAGNET_PROCESS_MODE).toString();
        this.duration = Integer.parseInt(params.get(CoreTag.MAGNET_DURATION).toString());
        this.sqlEngine = params.get(CoreTag.MAGNET_SQL_ENGINE).toString();
    }

    /**
     * 启动环境
     * @Author huan
     * @Date 2020-01-22
     * @Param []
     * @Return void
     * @Exception
     * @Description
     **/
    public abstract void start();

    /**
     * 停止环境
     * @Author huan
     * @Date 2020-01-22
     * @Param []
     * @Return void
     * @Exception
     * @Description
     **/
    public abstract void stop();

    /**
     * 获取上下文
     * @Author huan
     * @Date 2020-01-21
     * @Param []
     * @Return java.lang.Object
     * @Exception
     * @Description
     **/
    public abstract Object getContext();

    /**
     * 获取SQL上下文
     * @Author huan
     * @Date 2020-01-21
     * @Param []
     * @Return java.lang.Object
     * @Exception
     * @Description
     **/
    public abstract Object getSQLContext();

    /**
     * 获取流上下文
     * @Author huan
     * @Date 2020-02-15
     * @Param []
     * @Return java.lang.Object
     * @Exception
     * @Description
     **/
    public abstract Object getStreamContext();

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEnvMode() {
        return envMode;
    }

    public void setEnvMode(String envMode) {
        this.envMode = envMode;
    }

    public String getProcessMode() {
        return processMode;
    }

    public void setProcessMode(String processMode) {
        this.processMode = processMode;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getSqlEngine() {
        return sqlEngine;
    }

    public void setSqlEngine(String sqlEngine) {
        this.sqlEngine = sqlEngine;
    }
}
