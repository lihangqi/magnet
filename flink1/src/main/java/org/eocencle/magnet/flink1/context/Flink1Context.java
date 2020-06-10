package org.eocencle.magnet.flink1.context;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.util.CoreTag;

/**
 * Flink1.10版执行环境
 * @author: huan
 * @Date: 2020-05-24
 * @Description:
 */
public class Flink1Context extends Context {
    // 批处理环境
    private ExecutionEnvironment batchEnv;
    // 流处理环境
    private StreamExecutionEnvironment streamEnv;

    @Override
    public void start() {
        // 执行模式判断
        if (CoreTag.PROCESS_MODE_BATCH.equalsIgnoreCase(this.getProcessMode())) {
            this.batchEnv = ExecutionEnvironment.getExecutionEnvironment();
        } else if (CoreTag.PROCESS_MODE_STREAM.equalsIgnoreCase(this.getProcessMode())) {
            this.streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        } else {
            throw new UnsupportedException("Unknown execution mode " + this.getProcessMode());
        }
    }

    @Override
    public void stop() {
        if (CoreTag.PROCESS_MODE_STREAM.equalsIgnoreCase(this.getProcessMode())) {
            try {
                this.streamEnv.execute(this.getAppName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getContext() {
        return this.batchEnv;
    }

    @Override
    public Object getSQLContext() {
        return null;
    }

    @Override
    public Object getStreamContext() {
        return this.streamEnv;
    }
}
