package org.eocencle.magnet.core.component.wrapper;

import org.eocencle.magnet.core.component.WorkStageComponent;
import org.eocencle.magnet.core.component.WorkStageHandler;
import org.eocencle.magnet.core.mapping.WorkStageInfo;
import org.eocencle.magnet.core.util.CoreTag;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 包装管理类
 * @author: huan
 * @Date: 2020-02-24
 * @Description:
 */
public class WrapperManager {
    // 执行包装map
    private static List<MatchWrapper> runningWrappers = new ArrayList<>();

    static {
        // 收集结果
        registerComponentWrapper(new MatchWrapper(CoreTag.STRING_STAR, CollectResultWrapper.class) {
            @Override
            public boolean check(WorkStageInfo info) {
                return true;
            }
        });
    }

    /**
     * 组件包装
     * @Author huan
     * @Date 2020-02-27
     * @Param [component, info, handler]
     * @Return org.eocencle.magnet.client.component.WorkStageComponent
     * @Exception
     * @Description
     **/
    public static WorkStageComponent wrapper(WorkStageComponent component, WorkStageInfo info, WorkStageHandler handler) {
        component.initData(info);
        component.initHandler(handler);

        try {
            Constructor constructor = null;
            for (MatchWrapper mapper: runningWrappers) {
                if (mapper.check(info)) {
                    constructor = mapper.getWrapperCls().getConstructor(WorkStageComponent.class);
                    component = (WorkStageComponent) constructor.newInstance(component);
                    component.initData(info);
                    component.initHandler(handler);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return component;
    }

    /**
     * 添加包装
     * @Author huan
     * @Date 2020-04-19
     * @Param [matchWrapper]
     * @Return void
     * @Exception
     * @Description
     **/
    public static void registerComponentWrapper(MatchWrapper matchWrapper) {
        runningWrappers.add(matchWrapper);
    }
}
