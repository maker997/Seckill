package com.maker997.service;

import com.maker997.dto.Exposer;
import com.maker997.dto.SeckillExecution;
import com.maker997.entity.Seckill;
import com.maker997.exception.RepeatKillException;
import com.maker997.exception.SeckillCloseException;
import com.maker997.exception.SeckillException;

import java.util.List;

/**
 *
 * @author liujiaqi
 * @date 2017/10/20
 */
public interface SeckillService {

    /**
     * 获取单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 获取所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 暴露秒杀接口
     * @param seckillId
     * @return
     */
     Exposer exportSkillUrl(long seckillId);

    /**
     * 执行秒杀记录
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws SeckillCloseException
     * @throws RepeatKillException
     */
     SeckillExecution excuteSeckill(long seckillId, long userPhone, String md5)
             throws SeckillException,SeckillCloseException,RepeatKillException;
}
