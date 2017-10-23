package com.maker997.entity;

import lombok.Data;

import java.util.Date;

/**
 *
 * @author liujiaqi
 * @date 2017/10/31
 */
@Data
public class SuccessKilled {

    private long seckillId;

    private long userPhone;

    private short state;

    private Date createTime;

    private Seckill seckill;
}
