package org.eocencle.magnet.spark1.component.handler.impl;

import kafka.common.TopicAndPartition;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.kafka.HasOffsetRanges;
import org.apache.spark.streaming.kafka.KafkaCluster;
import org.apache.spark.streaming.kafka.OffsetRange;
import org.eocencle.magnet.spark1.component.handler.KafkaOffsetManager;
import org.eocencle.magnet.spark1.util.SparkUtil;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.mutable.ArrayBuffer;
import scala.util.Either;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Kafka的offset管理默认类
 * @author: huan
 * @Date: 2020-05-05
 * @Description:
 */
public class KafkaOffsetDefaultManager implements KafkaOffsetManager {
    @Override
    public void init(Object... objs) {

    }

    @Override
    public Map<TopicAndPartition, Long> getOffset(KafkaCluster kafkaCluster, String group, String topic) {
        Map<TopicAndPartition, Long> topicAndPartitionOffsetLong = new HashMap<>();

        // 获取分区信息
        Set<String> topics = new HashSet<>();
        topics.add(topic);
        Either<ArrayBuffer<Throwable>, scala.collection.immutable.Set<TopicAndPartition>> partitions =
                kafkaCluster.getPartitions(SparkUtil.toScalaSet(topics));

        // 获取offset
        if (partitions.isRight()) {
            scala.collection.immutable.Set<TopicAndPartition> topicAndPartitionSet = partitions.right().get();
            Either<ArrayBuffer<Throwable>, scala.collection.immutable.Map<TopicAndPartition, Object>> consumerOffsets =
                    kafkaCluster.getConsumerOffsets(group, topicAndPartitionSet);

            if (consumerOffsets.isLeft()) {
                Iterator<TopicAndPartition> iterator = topicAndPartitionSet.iterator();
                while (iterator.hasNext()) {
                    topicAndPartitionOffsetLong.put(iterator.next(), 0l);
                }
            } else {
                scala.collection.immutable.Map<TopicAndPartition, Object> topicAndPartitionObjectMap =
                        consumerOffsets.right().get();

                Iterator<Tuple2<TopicAndPartition, Object>> iterator = topicAndPartitionObjectMap.iterator();
                while (iterator.hasNext()) {
                    Tuple2<TopicAndPartition, Object> next = iterator.next();
                    topicAndPartitionOffsetLong.put(next._1, Long.parseLong(next._2.toString()));
                }
            }

            return topicAndPartitionOffsetLong;
        }

        throw new RuntimeException("Topic " + topic + " not found!");
    }

    @Override
    public void saveOffset(KafkaCluster kafkaCluster, JavaInputDStream<String> inputDStream, String group) {
        inputDStream.foreachRDD((JavaRDD<String> rdd) -> {
            final OffsetRange[] offsets = ((HasOffsetRanges) rdd.rdd()).offsetRanges();
            for (OffsetRange offsetRange: offsets) {
                java.util.Map<TopicAndPartition, Object> offset = new HashMap<>();
                offset.put(offsetRange.topicAndPartition(), offsetRange.untilOffset());
                Either<ArrayBuffer<Throwable>, scala.collection.immutable.Map<TopicAndPartition, Object>> result =
                        kafkaCluster.setConsumerOffsets(group, SparkUtil.toScalaMap(offset));
                if (result.isLeft()) {
                    throw new RuntimeException("Failed to save offset!");
                }
            }
        });
    }
}
