package com.maker997.dto;

import lombok.Data;

/**
 * 封装ajax请求的结果
 *
 * @author liujiaqi
 * @date 2017/10/23
 */
@Data
public class SeckillResult<T> {

    private boolean success;

    private T data;

    private String error;

    public SeckillResult(boolean success, T data)
    {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String error)
    {
        this.success = success;
        this.error = error;
    }
}
