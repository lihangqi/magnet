package org.eocencle.magnet.spark1.component.wrapper;

import org.eocencle.magnet.core.component.wrapper.MatchWrapper;
import org.eocencle.magnet.core.component.wrapper.WrapperManager;
import org.eocencle.magnet.core.component.wrapper.WrapperRegister;
import org.eocencle.magnet.core.mapping.WorkStageInfo;
import org.eocencle.magnet.core.util.CoreTag;

/**
 * Spark包装类注册类
 * @author: huan
 * @Date: 2020-04-18
 * @Description:
 */
public class SparkWrapperRegister implements WrapperRegister {
    @Override
    public void register() {
        // 收集异常
        WrapperManager.registerComponentWrapper(new MatchWrapper(CoreTag.STRING_STAR, SparkCollectExceptionWrapper.class) {
            @Override
            public boolean check(WorkStageInfo info) {
                return true;
            }
        });
        // spark表注册
        WrapperManager.registerComponentWrapper(new MatchWrapper(CoreTag.STRING_STAR, SparkSQLTableRegisterWrapper.class) {
            @Override
            public boolean check(WorkStageInfo info) {
                return true;
            }
        });
    }
}
