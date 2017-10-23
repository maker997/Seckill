package com.maker997.exception;

/**
 * 重复秒杀异常(运行期异常)
 * @author liujiaqi
 * @date 2017/10/20
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message)
    {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
