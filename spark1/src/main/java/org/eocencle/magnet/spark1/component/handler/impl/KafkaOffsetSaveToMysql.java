package org.eocencle.magnet.spark1.component.handler.impl;

import kafka.common.TopicAndPartition;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.kafka.HasOffsetRanges;
import org.apache.spark.streaming.kafka.KafkaCluster;
import org.apache.spark.streaming.kafka.OffsetRange;

import org.eocencle.magnet.spark1.component.handler.KafkaOffsetManager;
import org.eocencle.magnet.spark1.util.SparkUtil;
import scala.collection.Iterator;
import scala.collection.mutable.ArrayBuffer;
import scala.util.Either;
import java.sql.*;
import java.util.*;

/**
 * Kafka的offset管理到mysql数据库
 * @author: tao
 * @Date: 2020-05-11
 * @Description:
 */
public class KafkaOffsetSaveToMysql implements KafkaOffsetManager {

    String url;
    String user;
    String password;
    String table;




    @Override
    public void init(Object... objs) {
     this.url = (String) objs[0]+objs[1]+"/"+objs[2]+"?characterEncoding=UTF-8";
     this.table= (String) objs[3];
        this.user = (String) objs[4];
     this.password = (String) objs[5];

    }


      /*
       判断是否存在指定管理offset的表，存在则直接使用，不存在则新建
      */

    public boolean createTable(String tableName)  {



        boolean result = false;
        Connection connection =null;

        //获取连接
        try {
            connection=DriverManager.getConnection(url,user,password);
            DatabaseMetaData meta = connection.getMetaData();
            java.sql.ResultSet tables = meta.getTables (null, null, tableName, null);
            System.out.printf("1");
            Statement stmt = connection.createStatement();

            if (tables.next()) {
                System.out.printf("2");
                result = true;
            }else {
                System.out.println("此表不存在，创建");
                String createSql ="CREATE TABLE "+ "`"+table+"`" +"("+
                        "      `topic` varchar(255) NOT NULL," +
                        "      `partition` int(11) NOT NULL," +
                        "      `groupid` varchar(255) NOT NULL," +
                        "      `offset` bigint(20) DEFAULT NULL," +
                        "      PRIMARY KEY (`topic`,`partition`,`groupid`)" +
                        "    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
                stmt.execute(createSql);
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }finally {
            if(connection !=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

       return result;
    }


    @Override
    public Map<TopicAndPartition, Long> getOffset(KafkaCluster kafkaCluster, String group, String topic)  {
    //调用方法判断表是否存在，不存在则创建
           createTable(table);

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
                //通过groupid 以及topic查询表，获取offset
                Connection connection= null;
                try {
                    connection = DriverManager.getConnection(url,user,password);
                    String sql ="select * from "+ table +" where groupid =? and topic=?";
                    PreparedStatement pstmt=connection.prepareStatement
                            (sql);
                    pstmt.setString(1,group);
                    pstmt.setString(2,topic);
                    ResultSet rs=pstmt.executeQuery();
                    while(rs.next()){
                        topicAndPartitionOffsetLong.put(new TopicAndPartition(rs.getString("topic"),rs.getInt("partition")),rs.getLong("offset"));
                    }

                    rs.close();
                    pstmt.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return topicAndPartitionOffsetLong;
        }

        throw new RuntimeException("Topic " + topic + " not found!");
    }





    @Override
    public void saveOffset(KafkaCluster kafkaCluster, JavaInputDStream<String> inputDStream, String group) {


        inputDStream.foreachRDD((JavaRDD<String> rdd) -> {
            Connection connection=DriverManager.getConnection(url,user,password);
            //replaceinto表示之前有就替换,没有就插入

            String sql = "replace into "+ table+" (`topic`,`partition`,`groupid`,`offset`) values(?,?,?,?)";
            PreparedStatement pstmt=connection.prepareStatement
                    (sql);
            final OffsetRange[] offsets = ((HasOffsetRanges) rdd.rdd()).offsetRanges();
            for (OffsetRange offsetRange: offsets) {
                pstmt.setString(1,offsetRange.topic());
                pstmt.setInt(2,offsetRange.partition());
                pstmt.setString(3,group);
                pstmt.setLong(4,offsetRange.untilOffset());
                pstmt.executeUpdate();

            }
            pstmt.close();
            connection.close();

            throw new RuntimeException("Failed to save offset!");


        });

    }

}
