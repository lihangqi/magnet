package org.eocencle.magnet.core.session;

import org.eocencle.magnet.core.util.CoreTag;

/**
 * 执行环境配置
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class RuntimeEnvironmentConfig {
    // 执行程序名称
    private String runEnv;
    // 执行程序版本
    private String version;

    public RuntimeEnvironmentConfig(ProjectConfig projectConfig) {
        this(projectConfig.getParameterInfo(CoreTag.MAGNET_CONTEXT).toString(),
                projectConfig.getParameterInfo(CoreTag.MAGNET_VERSION).toString());
    }

    public RuntimeEnvironmentConfig(String runEnv, String version) {
        this.runEnv = runEnv;
        this.version = version;
    }

    public String getRunEnv() {
        return runEnv;
    }

    public void setRunEnv(String runEnv) {
        this.runEnv = runEnv;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
