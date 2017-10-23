package com.maker997.dao;

import com.maker997.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *
 * @author liujiaqi
 * @date 2017/10/31
 */
public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 返回影响的行数,如果返回0 说明减库存操作失败
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    /**
     * 根据 id 查商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> listAll(@Param("offset") int offset,@Param("limit") int limit);

}
