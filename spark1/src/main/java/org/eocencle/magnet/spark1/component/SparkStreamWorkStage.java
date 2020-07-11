package org.eocencle.magnet.spark1.component;

import kafka.common.TopicAndPartition;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaCluster;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.eocencle.magnet.core.component.*;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.exception.IgnoreException;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.spark1.component.handler.KafkaOffsetManager;
import org.eocencle.magnet.spark1.util.SparkUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
            if (!line.isEmpty()) {
                getParent().setStreamBatchResult(new SparkStreamReceiveWorkStageResult(line));

                try {
                    // 执行组件
                    for (WorkStageComponent component: components) {
                        component.execute(parameter);
                    }
                } catch (IgnoreException e) {
                    // 忽略异常，不做任何处理
                }
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
