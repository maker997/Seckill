package com.maker997.dto;

import com.maker997.entity.SuccessKilled;
import com.maker997.enums.SeckillStateEnum;
import lombok.Data;

@Data
/**
 * @author liujiaqi
 * @date 2017/10/31
 */
public class SeckillExecution {

    private long seckillId;

    /**
     * 秒杀状态
     */
    private int state;

    /**
     * 秒杀状态
     */
    private String stateInfo;

    /**
     * 秒杀记录对象
     */
    private SuccessKilled successKilled;

    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum, SuccessKilled successKilled)
    {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    /**
     * 在秒杀失败的时候,构建一个秒杀执行对象
     * @param seckillId
     */
    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum)
    {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
}
