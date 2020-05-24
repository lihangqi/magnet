package org.eocencle.magnet.core.component;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eocencle.magnet.core.component.wrapper.WorkStageComponentWrapper;
import org.eocencle.magnet.core.mapping.BranchInfo;
import org.eocencle.magnet.core.mapping.OutputInfo;
import org.eocencle.magnet.core.mapping.WorkStageInfo;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.core.util.CoreTag;

import java.util.ArrayList;
import java.util.List;

/**
 * 混合作业组件类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class WorkStageComposite extends WorkStageComponent {
    private static final Logger logger = Logger.getLogger(WorkStageComposite.class);
    // 命名空间前缀
    private String namePrefix;
    // 工作节点以ID为索引的映射
    private StrictMap<WorkStageResult> resultIdMap = new StrictMap<WorkStageResult>("WorkStage result by id index");
    // 工作节点以Alias为索引的映射
    private StrictMap<WorkStageResult> resultAliasMap = new StrictMap<WorkStageResult>("WorkStage result by alias index");
    // SQL表名映射
    private StrictMap<String> tableNameMap = new StrictMap<String>("SQL table mapping");
    // 执行时参数
    private StrictMap<Object> params = new StrictMap<Object>("WorkStage params");
    // 分支信息
    private BranchInfo branchInfo;
    // 最后一个结果
    private WorkStageResult lastResult;
    // 引用结果
    private WorkStageResult refResult;
    // 流间隔数据
    private WorkStageResult streamBatchResult;

    @Override
    public void initData(WorkStageInfo info) {
        this.branchInfo = (BranchInfo) info;
        this.namePrefix = this.branchInfo.getProjectConfig().getParameterInfo(CoreTag.MAGNET_FILE_NAME).toString();
    }

    @Override
    public void initHandler(WorkStageHandler handler) {

    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        logger.info("Start execution of container '" + this.branchInfo.getId() + "'");
        List<WorkStageResult> results = null;
        List<WorkStageResult> branchResults = new ArrayList<>();
        for (WorkStageComponent component: this.components) {
            results = component.execute(parameter);

            // 收集Branch返回结果
            if (null != results &&
                    ((WorkStageComponentWrapper) component).getWorkStageInfo() instanceof OutputInfo) {
                branchResults.add(results.get(0));
            }
        }
        logger.info("Container '" + this.branchInfo.getId() + "' stop");
        return branchResults;
    }

    @Override
    public void add(WorkStageComponent component) {
        this.components.add(component);
        component.setParent(this);
    }

    /**
     * 获取混合后的名称
     * @Author huan
     * @Date 2020-01-21
     * @Param [tableName]
     * @Return java.lang.String
     * @Exception
     * @Description
     **/
    public String getMixedTableName(String tableName) {
        return this.namePrefix + CoreTag.STRING_UNDERLINE + System.currentTimeMillis() + CoreTag.STRING_UNDERLINE + tableName;
    }

    /**
     * 添加执行结果
     * @Author huan
     * @Date 2020-02-29
     * @Param [result]
     * @Return void
     * @Exception
     * @Description
     **/
    public void addResult(WorkStageResult result) {
        this.resultIdMap.put(result.getId(), result, true);
        if (StringUtils.isNotBlank(result.getAlias())) {
            this.resultAliasMap.put(result.getAlias(), result, true);
        }
    }

    public void changeLastResult(WorkStageResult result) {
        this.lastResult = result;
    }

    /**
     * 获取依赖的结果
     * @Author huan
     * @Date 2020-03-25
     * @Param [ref]
     * @Return org.eocencle.magnet.client.component.WorkStageResult
     * @Exception
     * @Description
     **/
    public WorkStageResult getPrevResult(String ref) {
        if (StringUtils.isNotBlank(ref)) {
            this.refResult = this.getIdResult(ref);
        } else {
            this.refResult = this.lastResult;
        }
        return this.refResult;
    }

    public boolean tableNameContains(String key) {
        return this.tableNameMap.containsKey(key);
    }

    public void putTableName(String key, String value) {
        this.tableNameMap.put(key, value, true);
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public WorkStageResult getIdResult(String id) {
        return this.resultIdMap.get(id);
    }

    public Object getParam(String key) {
        return this.params.get(key);
    }

    public StrictMap<Object> getParams() {
        return params;
    }

    public BranchInfo getBranchInfo() {
        return branchInfo;
    }

    public StrictMap<String> getTableNameMap() {
        return tableNameMap;
    }

    public void setParams(StrictMap<Object> params) {
        this.params = params;
    }

    public void putParam(String key, Object value) {
        this.params.put(key, value, true);
    }

    public StrictMap<WorkStageResult> getResultIdMap() {
        return resultIdMap;
    }

    public WorkStageResult getRefResult() {
        if (null == this.refResult) {
            this.refResult = this.lastResult;
        }
        return this.refResult;
    }

    public WorkStageResult getStreamBatchResult() {
        return streamBatchResult;
    }

    public void setStreamBatchResult(WorkStageResult streamBatchResult) {
        this.streamBatchResult = streamBatchResult;
    }
}
