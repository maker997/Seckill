package com.maker997.exception;

/**
 * @author liujiaqi
 * @date 2017/10/20
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
