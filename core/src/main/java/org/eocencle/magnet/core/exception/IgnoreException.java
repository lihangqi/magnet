package org.eocencle.magnet.core.exception;

/**
 * 忽略异常
 * @author: huan
 * @Date: 2020-05-12
 * @Description:
 */
public class IgnoreException extends RuntimeException {
    public IgnoreException(String message) {
        super(message);
    }
}
