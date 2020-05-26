package org.eocencle.magnet.spark1.component;

import kafka.common.TopicAndPartition;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaCluster;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.exception.IgnoreException;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.spark1.component.handler.KafkaOffsetManager;
import org.eocencle.magnet.spark1.util.SparkUtil;

import java.io.Serializable;
import java.util.*;

/**
 * spark流作业节点类
 * @author: huan
 * @Date: 2020-02-02
 * @Description:
 */
public class SparkStreamWorkStage extends StreamWorkStage implements Serializable {
    // kafka的offset管理器
    private KafkaOffsetManager kafkaOffsetManager;

    @Override
    public void initHandler(WorkStageHandler handler) {
        this.kafkaOffsetManager = (KafkaOffsetManager) handler;
    }

    @Override
    public List<WorkStageResult> execute(WorkStageParameter parameter) {
        Context context = parameter.getContext();
        JavaStreamingContext ssc = (JavaStreamingContext) context.getStreamContext();

        // 设置checkpoint
        String checkPoint = this.getParent().getParam(CoreTag.MAGNET_STREAM_CHECK_POINT).toString();
        ssc.checkpoint(checkPoint);

        // 主题
        Set<String> topics = new HashSet<>(Arrays.asList(this.streamInfo.getTopics().split(CoreTag.STRING_COMMA)));

        // kafka参数
        StrictMap<String> kafkaConfig = this.streamInfo.getConfig().get(CoreTag.STREAM_CONFIG_KAFKA).getMap();

        // 获取kafka集群信息
        KafkaCluster kafkaCluster = new KafkaCluster(SparkUtil.toScalaMap(kafkaConfig));

        // 分组
        String group = kafkaConfig.get("group.id");
        // 关闭自动提交
        kafkaConfig.put("enable.auto.commit", "false", true);

        // 获取offset信息
        Map<TopicAndPartition, Long> offset =
            this.kafkaOffsetManager.getOffset(kafkaCluster, group, this.streamInfo.getTopics());

        this.getParent().putParam(CoreTag.TASK_EXCEPTION_PRINT_OFFSET, offset);

        // 创建流
        JavaInputDStream<String> dStream =
                KafkaUtils.createDirectStream(ssc,
                        String.class, String.class,
                        StringDecoder.class, StringDecoder.class,
                        String.class,
                        kafkaConfig,
                        offset,
                        (MessageAndMetadata<String, String> meta) -> meta.message());

        // 遍历流
        dStream.foreachRDD((JavaRDD<String> line) -> {
            ComponentFactory factory = WorkStageComponentBuilderAssistant.getFactory();

            // 创建RDD
            JavaRDD<Row> rdd = SparkUtil.createRDD(line, streamInfo.getSeparator(), streamInfo.getFields());
            // 创建DataFrame
            DataFrame df = SparkUtil.createDataFrame((SQLContext) context.getSQLContext(), streamInfo.getFields(), rdd);

            SparkWorkStageResult result = (SparkWorkStageResult) factory.createWorkStageResult();
            result.setId(streamInfo.getId());
            result.setAlias(streamInfo.getAlias());
            result.setRdd(rdd);
            result.setDf(df);

            WorkStageComposite parent = getParent();
            String id = streamInfo.getId();
            String idName = parent.getMixedTableName(id);
            parent.putTableName(id, idName);

            df.registerTempTable(idName);

            parent.changeLastResult(result);
            parent.setStreamBatchResult(result);

            try {
                for (WorkStageComponent component: components) {
                    component.execute(parameter);
                }
            } catch (IgnoreException e) {
                // 忽略异常，不做任何处理
            }
        });

        // 保存offset
        this.kafkaOffsetManager.saveOffset(kafkaCluster, dStream, group);

        return null;
    }

    @Override
    public void add(WorkStageComponent component) {
        this.components.add(component);
        component.setParent(this.getParent());
    }
}
