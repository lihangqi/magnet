package org.eocencle.magnet.client.component;

import org.eocencle.magnet.core.context.ComponentFactory;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.session.RuntimeEnvironmentConfig;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.flink1.context.Flink1ComponentFactory;
import org.eocencle.magnet.spark1.context.Spark1ComponentFactory;

/**
 * 运行时环境建构类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class RuntimeEnvironmentBuilder {
    // 组件工厂
    private static StrictMap<ComponentFactory> FACTORYS = new StrictMap<>("Component factorys");

    static {
        FACTORYS.put(CoreTag.CONTEXT_SPARK1.toUpperCase(), Spark1ComponentFactory.getFactoryInstance());
        FACTORYS.put(CoreTag.CONTEXT_FLINK1.toUpperCase(), Flink1ComponentFactory.getFactoryInstance());
    }

    /**
     * 构造环境工厂
     * @Author huan
     * @Date 2020-02-02
     * @Param [config]
     * @Return org.eocencle.magnet.context.factory.ComponentFactory
     * @Exception
     * @Description
     **/
    public static ComponentFactory construct(RuntimeEnvironmentConfig config) {
        try {
            return FACTORYS.get(config.getRunEnv().toUpperCase());
        } catch (Exception e) {
            throw new UnsupportedException(CoreTag.MAGNET_PROCESS_MODE + " does not support " + config.getRunEnv() + " mode!");
        }
    }
}
