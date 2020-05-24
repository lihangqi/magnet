package org.eocencle.magnet.core.validation;

import org.eocencle.magnet.core.exception.ValidationException;
import org.eocencle.magnet.core.session.ProjectConfig;

/**
 * 验证抽象类
 * @author: huan
 * @Date: 2020-03-24
 * @Description:
 */
public abstract class AbstractValidation {
    // 下一个验证
    protected AbstractValidation next;

    /**
     * 验证方法
     * @Author huan
     * @Date 2020-03-24
     * @Param [config]
     * @Return void
     * @Exception ValidationException
     * @Description
     **/
    public void valid(ProjectConfig config) throws ValidationException {
        this.doValid(config);
        if (null != this.next) {
            this.next.valid(config);
        }
    }

    /**
     * 设置下一个验证
     * @Author huan
     * @Date 2020-03-29
     * @Param [validation]
     * @Return void
     * @Exception
     * @Description
     **/
    public void setNext(AbstractValidation validation) {
        this.next = validation;
    }

    /**
     * 验证实现
     * @Author huan
     * @Date 2020-03-24
     * @Param [config]
     * @Return void
     * @Exception ValidationException
     * @Description
     **/
    abstract protected void doValid(ProjectConfig config) throws ValidationException;
}
