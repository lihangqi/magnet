package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.mapping.InfoParam;
import org.eocencle.magnet.core.mapping.OutputInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkOutputer;

import java.util.Map;
import java.util.Properties;

/**
 * Spark的kafka输出器类
 * @author: huan
 * @Date: 2020-04-02
 * @Description:
 */
public class SparkKafkaOutputer implements SparkOutputer {
    @Override
    public WorkStageResult output(OutputInfo outputInfo, SparkWorkStageResult result) {
        Properties properties = new Properties();
        for (Map.Entry<String, InfoParam> entry: outputInfo.getOutputConfig().entrySet()) {
            properties.put(entry.getKey(), entry.getValue().getValue());
        }
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        result.getRdd().foreach((Row row) -> {
            String[] val = new String[row.length()];
            for (int i = 0; i < row.length(); i++) {
                if (row.isNullAt(i)) {
                    val[i] = CoreTag.STRING_BLANK;
                } else {
                    val[i] = row.get(i).toString();
                }
            }
            producer.send(new ProducerRecord<>(outputInfo.getTarget(), StringUtils.join(val, outputInfo.getSeparator())));
        });
        producer.close();
        return null;
    }
}
