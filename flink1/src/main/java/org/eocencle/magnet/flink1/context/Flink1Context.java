package org.eocencle.magnet.flink1.context;

import org.eocencle.magnet.core.context.Context;

/**
 * Flink1.10版执行环境
 * @author: huan
 * @Date: 2020-05-24
 * @Description:
 */
public class Flink1Context extends Context {
    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public Object getContext() {
        return null;
    }

    @Override
    public Object getSQLContext() {
        return null;
    }

    @Override
    public Object getStreamContext() {
        return null;
    }
}
