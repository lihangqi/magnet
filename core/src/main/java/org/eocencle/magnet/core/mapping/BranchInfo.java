package org.eocencle.magnet.core.mapping;

import org.eocencle.magnet.core.session.ProjectConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 分支信息类
 * @author: huan
 * @Date: 2020-01-18
 * @Description:
 */
public class BranchInfo extends WorkFlowInfo {
    // 文件源
    private String src;
    // 数据集
    private List<DataSet> dataSet = new ArrayList<DataSet>();
    // 分支配置
    private ProjectConfig projectConfig;

    /**
     * 分支返回的数据集
     * @author: huan
     * @Date: 2020-01-18
     * @Description:
     */
    public static class DataSet extends WorkStageInfo {
        // 别名
        private String alias;

        public DataSet(String id, String alias) {
            this.setId(id);
            this.alias = alias;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getFileName() {
        return new File(this.src).getName().replace(".xml", "");
    }

    public void addDataSet(DataSet dataSet) {
        this.dataSet.add(dataSet);
    }

    public List<DataSet> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<DataSet> dataSet) {
        this.dataSet = dataSet;
    }

    public ProjectConfig getProjectConfig() {
        return projectConfig;
    }

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }
}
