package org.eocencle.magnet.spark1.component.wrapper;

import kafka.common.TopicAndPartition;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.component.WorkStageComponent;
import org.eocencle.magnet.core.component.WorkStageComposite;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.component.wrapper.WorkStageComponentWrapper;
import org.eocencle.magnet.core.exception.IgnoreException;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;

import java.util.List;
import java.util.Map;

/**
 * 收集作业组件执行异常包装类
 * @author: huan
 * @Date: 2020-05-09
 * @Description:
 */
public class SparkCollectExceptionWrapper extends WorkStageComponentWrapper {
    private static final Logger logger = Logger.getLogger(SparkCollectExceptionWrapper.class);

    public SparkCollectExceptionWrapper(WorkStageComponent workStageComponent) {
        super(workStageComponent);
    }

    @Override
    public void before(WorkStageParameter parameter) {

    }

    @Override
    public void after(WorkStageParameter parameter, List<WorkStageResult> results) {
        Exception e = WorkStageComponentWrapper.getTaskException();
        if (null != e) {
            WorkStageComposite parent = this.getParent();
            // 打印异常时信息
            StrictMap<String> param = (StrictMap<String>) parent.getParam(CoreTag.MAGNET_TASK_EXCEPTION_PRINT);
            String streamMode = parent.getParam(CoreTag.MAGNET_PROCESS_MODE).toString();
            if (param.containsKey(CoreTag.TASK_EXCEPTION_PRINT_STACK) &&
                    Boolean.parseBoolean(param.get(CoreTag.TASK_EXCEPTION_PRINT_STACK))) {
                this.printStack();
            }
            if (CoreTag.PROCESS_MODE_STREAM.equalsIgnoreCase(streamMode) &&
                    param.containsKey(CoreTag.TASK_EXCEPTION_PRINT_OFFSET) &&
                    Boolean.parseBoolean(param.get(CoreTag.TASK_EXCEPTION_PRINT_OFFSET))) {
                this.printOffset();
            }
            if (param.containsKey(CoreTag.TASK_EXCEPTION_PRINT_PREV_RESULT)) {
                this.printPrevResult(param.get(CoreTag.TASK_EXCEPTION_PRINT_PREV_RESULT));
            }
            if (CoreTag.PROCESS_MODE_STREAM.equalsIgnoreCase(streamMode) &&
                    param.containsKey(CoreTag.TASK_EXCEPTION_PRINT_STREAM_SOURCE)) {
                this.printStreamSource(param.get(CoreTag.TASK_EXCEPTION_PRINT_STREAM_SOURCE));
            }

            e.printStackTrace();
            // 异常是否中断
            if (CoreTag.PROCESS_MODE_STREAM.equalsIgnoreCase(streamMode) &&
                    !Boolean.parseBoolean(parent.getParam(CoreTag.MAGNET_TASK_EXCEPTION_BREAK).toString())) {
                throw new IgnoreException(e.getMessage());
            } else {
                System.exit(1);
            }
        }
    }

    /**
     * 打印任务栈
     * @Author huan
     * @Date 2020-05-09
     * @Param []
     * @Return void
     * @Exception
     * @Description
     */
    private void printStack() {
        StrictMap<WorkStageResult> resultIdMap = this.getParent().getResultIdMap();
        int idx = 0;
        logger.error("Task stack:");
        for (Map.Entry<String, WorkStageResult> result: resultIdMap.entrySet()) {
            logger.error("step" + idx + " -> " + result.getValue().getId());
            idx ++;
        }
    }

    /**
     * 打印偏移量
     * @Author huan
     * @Date 2020-05-09
     * @Param []
     * @Return void
     * @Exception
     * @Description
     */
    private void printOffset() {
        Map<TopicAndPartition, Long> offset =
                (Map<TopicAndPartition, Long>) this.getParent().getParam(CoreTag.TASK_EXCEPTION_PRINT_OFFSET);
        logger.error("Stream offset:");
        for (Map.Entry<TopicAndPartition, Long> entry: offset.entrySet()) {
            logger.error("partition" + entry.getKey().partition() + " -> " + entry.getValue());
        }
    }

    /**
     * 打印上一次结果集
     * @Author huan
     * @Date 2020-05-09
     * @Param [dir]
     * @Return void
     * @Exception
     * @Description
     */
    private void printPrevResult(String dir) {
        dir += CoreTag.STRING_UNDERLINE + System.currentTimeMillis();
        logger.error("Previous result set dir: " + dir);
        ((SparkWorkStageResult) this.getParent().getRefResult()).getRdd().map((Row row) -> {
            String[] val = new String[row.length()];
            for (int i = 0; i < row.length(); i++) {
                if (row.isNullAt(i)) {
                    val[i] = CoreTag.STRING_BLANK;
                } else {
                    val[i] = row.get(i).toString();
                }
            }
            return StringUtils.join(val, CoreTag.STRING_COMMA);
        }).saveAsTextFile(dir);
    }

    /**
     * 打印流批次数据集
     * @Author huan
     * @Date 2020-05-12
     * @Param [dir]
     * @Return void
     * @Exception
     * @Description
     */
    private void printStreamSource(String dir) {
        dir += CoreTag.STRING_UNDERLINE + System.currentTimeMillis();
        logger.error("Stream source set dir: " + dir);
        ((SparkWorkStageResult) this.getParent().getStreamBatchResult()).getRdd().map((Row row) -> {
            String[] val = new String[row.length()];
            for (int i = 0; i < row.length(); i++) {
                if (row.isNullAt(i)) {
                    val[i] = CoreTag.STRING_BLANK;
                } else {
                    val[i] = row.get(i).toString();
                }
            }
            return StringUtils.join(val, CoreTag.STRING_COMMA);
        }).saveAsTextFile(dir);
    }
}
