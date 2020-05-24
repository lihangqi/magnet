package org.eocencle.magnet.core.exception;

/**
 * 不支持异常类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class UnsupportedException extends RuntimeException {
    public UnsupportedException(String message) {
        super(message);
    }
}
