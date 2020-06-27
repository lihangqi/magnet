package org.eocencle.magnet.spark1.component;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.spark1.component.handler.SparkRowNumHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark的行号作业节点类
 * @author: huan
 * @Date: 2020-06-07
 * @Description:
 */
public class SparkRowNumWorkStage extends RowNumWorkStage {
    private static final Logger logger = Logger.getLogger(SparkRowNumWorkStage.class);
    // 行号处理器
    private SparkRowNumHandler rowNumHandler;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.rowNumHandler = (SparkRowNumHandler) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

        SparkWorkStageResult prevResult =
                (SparkWorkStageResult) this.getParent().getPrevResult(this.rowNumInfo.getRef());

        // 创建RDD
        JavaRDD<Row> rdd = this.rowNumHandler.createRDD(prevResult.getDf());
        // 创建DataFrame
        DataFrame df = this.rowNumHandler.createDataFrame((SQLContext) parameter.getContext().getSQLContext(), this.rowNumInfo, prevResult.getDf(), rdd);

        // 设置返回值
        SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
        result.setId(this.rowNumInfo.getId());
        result.setAlias(this.rowNumInfo.getAlias());
        result.setRdd(rdd);
        result.setDf(df);
        List<WorkStageResult> list = new ArrayList<>();
        list.add(result);
        return list;
    }
}
