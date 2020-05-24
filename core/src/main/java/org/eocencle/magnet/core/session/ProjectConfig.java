package org.eocencle.magnet.core.session;

import org.eocencle.magnet.core.mapping.DataSourceInfo;
import org.eocencle.magnet.core.mapping.WorkFlowInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;

/**
 * 项目配置类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class ProjectConfig {
    // 参数信息
    private StrictMap<Object> parameterInfo = new StrictMap<Object>("Parameter info");
    // 数据源信息
    private StrictMap<DataSourceInfo> dataSourceInfo = new StrictMap<DataSourceInfo>("Data Source info");
    // 工作流信息
    private StrictMap<WorkFlowInfo> workFlowInfo = new StrictMap<WorkFlowInfo>("Work Flow info");

    public ProjectConfig() {
        this.initParameters();
    }

    /**
     * 初始化系统参数
     * @Author huan
     * @Date 2020-02-14
     * @Param []
     * @Return void
     * @Exception
     * @Description
     **/
    private void initParameters() {
        this.putParameterInfo(CoreTag.MAGNET_NAME, CoreTag.CONTEXT_APPNAME_DEFAULT);
        this.putParameterInfo(CoreTag.MAGNET_FILE_NAME, CoreTag.CONTEXT_APPNAME_DEFAULT);
        this.putParameterInfo(CoreTag.MAGNET_CONTEXT, CoreTag.CONTEXT_SPARK1);
        this.putParameterInfo(CoreTag.MAGNET_VERSION, CoreTag.CONTEXT_VERSION_DEFALUT);
        this.putParameterInfo(CoreTag.MAGNET_ENV_MODE, CoreTag.ENV_MODE_LOCAL);
        this.putParameterInfo(CoreTag.MAGNET_PROCESS_MODE, CoreTag.PROCESS_MODE_BATCH);
        this.putParameterInfo(CoreTag.MAGNET_DURATION, CoreTag.STREAM_DEFAULT_DURATION);
        this.putParameterInfo(CoreTag.MAGNET_SQL_ENGINE, CoreTag.SQL_ENGINE_SPARK);
        StrictMap<String> prints = new StrictMap<>("Exception prints");
        prints.put(CoreTag.TASK_EXCEPTION_PRINT_STACK, CoreTag.TRUE);
        this.putParameterInfo(CoreTag.MAGNET_TASK_EXCEPTION_PRINT, prints);
        this.putParameterInfo(CoreTag.MAGNET_TASK_EXCEPTION_BREAK, CoreTag.TRUE);
        this.putParameterInfo(CoreTag.MAGNET_STREAM_STATE, CoreTag.STREAM_STATE_NONE);
    }

    public void putParameterInfo(String key, Object value) {
        this.parameterInfo.put(key, value, true);
    }

    public StrictMap<Object> getParameterInfo() {
        return parameterInfo;
    }

    public Object getParameterInfo(String key) {
        return this.parameterInfo.get(key);
    }

    public void setParameterInfo(StrictMap<Object> parameterInfo) {
        this.parameterInfo = parameterInfo;
    }

    public StrictMap<DataSourceInfo> getDataSourceInfo() {
        return dataSourceInfo;
    }

    public void setDataSourceInfo(StrictMap<DataSourceInfo> dataSourceInfo) {
        this.dataSourceInfo = dataSourceInfo;
    }

    public void putDataSourceInfo(String id, DataSourceInfo dataSourceInfo) {
        this.dataSourceInfo.put(id, dataSourceInfo);
    }

    public StrictMap<WorkFlowInfo> getWorkFlowInfo() {
        return workFlowInfo;
    }

    public void setWorkFlowInfo(StrictMap<WorkFlowInfo> workFlowInfo) {
        this.workFlowInfo = workFlowInfo;
    }

    public void putWorkFlowInfo(String id, WorkFlowInfo workFlowInfo) {
        this.workFlowInfo.put(id, workFlowInfo, true);
    }
}
