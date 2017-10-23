package com.maker997.dao;

import com.maker997.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by liujiaqi on 17/10/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilledTest()
    {
        long seckillId = 1001L;
        long userPhone = 13834561234L;
        int insertNumber = successKilledDao.insertSuccessKilled(seckillId,userPhone);
        System.out.println("insertNumber = "+insertNumber);

    }

    @Test
    public void getByIdWithSeckill()
    {
        long seckillId = 1001L;
        long userPhone = 13834561234L;
        SuccessKilled successKilled = successKilledDao.getByIdWithSeckill(seckillId,userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}
