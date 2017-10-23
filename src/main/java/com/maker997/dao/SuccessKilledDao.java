package com.maker997.dao;

import com.maker997.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author liujiaqi
 * @date 2017/10/31
 */
public interface SuccessKilledDao {

    /**
     * 插入购买成功记录
     * @param seckillId
     * @param userPhone
     * @returnˇˇ
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 通过 seckillId 查询出秒杀记录名查询出秒杀产品的详情
     * @param seckillId
     * @return
     */
    SuccessKilled getByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
