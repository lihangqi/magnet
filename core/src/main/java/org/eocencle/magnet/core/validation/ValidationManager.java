package org.eocencle.magnet.core.validation;

import org.eocencle.magnet.core.exception.ValidationException;
import org.eocencle.magnet.core.session.ProjectConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证管理类
 * @author: huan
 * @Date: 2020-03-29
 * @Description:
 */
public class ValidationManager {
    // 验证组件
    private static List<AbstractValidation> valids = new ArrayList<>();
    // 第一个验证
    private static AbstractValidation first;

    static {
        valids.add(new ProcessModeValidation());
    }

    /**
     * 初始化验证管理器
     * @Author huan
     * @Date 2020-03-29
     * @Param []
     * @Return void
     * @Exception
     * @Description
     **/
    public static void init() {
        AbstractValidation prev = null;
        for (AbstractValidation validation: valids) {
            if (null == prev) {
                prev = validation;
                first = validation;
            } else {
                prev.setNext(validation);
                prev = validation;
            }
        }
    }

    /**
     * 进入验证
     * @Author huan
     * @Date 2020-03-29
     * @Param [config]
     * @Return void
     * @Exception
     * @Description
     **/
    public static void enterValid(ProjectConfig config) {
        try {
            if (null != first) {
                first.valid(config);
            }
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
