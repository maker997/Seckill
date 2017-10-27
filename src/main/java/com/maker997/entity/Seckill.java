package com.maker997.entity;

import lombok.Data;

import java.util.Date;

/**
 *
 * @author liujiaqi
 * @date 2017/10/31
 */
@Data
public class Seckill {

    private long seckillId;

    private int number;

    private String name;

    private Date startTime;

    private Date endTime;

    private Date createTime;

}
