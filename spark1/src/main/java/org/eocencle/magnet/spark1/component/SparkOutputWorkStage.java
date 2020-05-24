package org.eocencle.magnet.spark1.component;

import org.eocencle.magnet.core.component.OutputWorkStage;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkOutputer;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark输出作业节点类
 * @author: huan
 * @Date: 2020-02-01
 * @Description:
 */
public class SparkOutputWorkStage extends OutputWorkStage {
    // 输出器
    private SparkOutputer outputer;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.outputer = (SparkOutputer) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        // 获取引用结果
        SparkWorkStageResult ref =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.outputInfo.getRef());

        WorkStageResult result = this.outputer.output(this.outputInfo, ref);
        if (null == result) {
            return null;
        } else {
            List<WorkStageResult> list = new ArrayList<>();
            list.add(result);
            return list;
        }
    }
}
