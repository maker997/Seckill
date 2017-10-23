package com.maker997.exception;

/**
 * 秒杀关闭异常(运行期异常)
 * 当秒杀关闭时,用户拿我们的秒杀地址继续秒杀就给抛出这个错误
 * @author liujiaqi
 * @date 2017/10/20
 */
public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message)
    {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
