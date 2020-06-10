package org.eocencle.magnet.servlet.context;

import org.eocencle.magnet.core.context.Context;

/**
 * Servlet执行环境
 * @author: huan
 * @Date: 2020-06-06
 * @Description:
 */
public class ServletContext extends Context {
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
