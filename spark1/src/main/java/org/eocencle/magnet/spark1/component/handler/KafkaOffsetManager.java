package org.eocencle.magnet.spark1.component.handler;

import kafka.common.TopicAndPartition;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.kafka.KafkaCluster;
import org.eocencle.magnet.core.component.WorkStageHandler;

import java.sql.SQLException;
import java.util.Map;

/**
 * Kafka的offset管理接口
 * @author: huan
 * @Date: 2020-05-05
 * @Description:
 */
public interface KafkaOffsetManager extends WorkStageHandler {
    /**
     * 初始化
     * @Author huan
     * @Date 2020-05-05
     * @Param [objs]
     * @Return void
     * @Exception
     * @Description
     */
    void init(Object...objs);

    /**
     * 获取offset
     * @Author huan
     * @Date 2020-05-05
     * @Param [kafkaCluster, group, topic]
     * @Return java.util.Map<kafka.common.TopicAndPartition,java.lang.Long>
     * @Exception
     * @Description
     */
    Map<TopicAndPartition, Long> getOffset(KafkaCluster kafkaCluster, String group, String topic);

    /**
     * 保存offset
     * @Author huan
     * @Date 2020-05-05
     * @Param [kafkaCluster, inputDStream, group]
     * @Return void
     * @Exception
     * @Description
     */
    void saveOffset(KafkaCluster kafkaCluster, JavaInputDStream<String> inputDStream, String group);
}
