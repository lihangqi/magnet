package org.eocencle.magnet.spark2.context;

import org.eocencle.magnet.core.context.Context;

/**
 * Spark2.x版执行环境
 * @author: huan
 * @Date: 2020-05-25
 * @Description:
 */
public class Spark2Context extends Context {
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
