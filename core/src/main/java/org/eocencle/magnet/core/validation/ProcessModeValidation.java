package org.eocencle.magnet.core.validation;

import org.eocencle.magnet.core.exception.IllegalNodeException;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.exception.ValidationException;
import org.eocencle.magnet.core.mapping.BranchInfo;
import org.eocencle.magnet.core.mapping.DataSourceInfo;
import org.eocencle.magnet.core.mapping.StreamInfo;
import org.eocencle.magnet.core.mapping.WorkFlowInfo;
import org.eocencle.magnet.core.session.ProjectConfig;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;

import java.util.Map;

/**
 * 处理模式验证类
 * @author: huan
 * @Date: 2020-03-30
 * @Description:
 */
public class ProcessModeValidation extends AbstractValidation {
    @Override
    protected void doValid(ProjectConfig config) throws ValidationException {
        String mode = config.getParameterInfo(CoreTag.MAGNET_PROCESS_MODE).toString();
        StrictMap<DataSourceInfo> datasource = config.getDataSourceInfo();
        int streamCnt = 0;
        for (Map.Entry<String, DataSourceInfo> data: datasource.entrySet()) {
            if (data.getValue() instanceof StreamInfo) {
                streamCnt ++;
            }
        }
        StrictMap<WorkFlowInfo> workflow = config.getWorkFlowInfo();
        int branchCnt = 0;
        for (Map.Entry<String, WorkFlowInfo> work: workflow.entrySet()) {
            if (work.getValue() instanceof BranchInfo) {
                branchCnt ++;
            }
        }

        if (CoreTag.PROCESS_MODE_BATCH.equalsIgnoreCase(mode)) {
            if (0 != streamCnt) {
                throw new IllegalNodeException("Batch mode cannot have stream data source!");
            }
        } else if (CoreTag.PROCESS_MODE_STREAM.equalsIgnoreCase(mode)) {
            if (1 < streamCnt) {
                throw new IllegalNodeException("Stream mode can only have one stream data source!");
            }
            if (0 != branchCnt) {
                throw new IllegalNodeException("Stream mode cannot have branch nodes!");
            }
        } else {
            throw new UnsupportedException(CoreTag.MAGNET_ENV_MODE + " does not support " + mode + " mode!");
        }
    }
}
