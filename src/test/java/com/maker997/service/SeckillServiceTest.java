package com.maker997.service;

import com.maker997.dto.Exposer;
import com.maker997.dto.SeckillExecution;
import com.maker997.entity.Seckill;
import com.maker997.exception.RepeatKillException;
import com.maker997.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by liujiaqi on 19/10/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService service;

    @Test
    public void getByIdTest()
    {
        Seckill seckill = service.getById(1000L);
        logger.info("seckill={}",seckill);
    }
    @Test
    public void getSeckillListTest()
    {
        List<Seckill> seckillList = service.getSeckillList();
        logger.info("seckillList={}",seckillList);
    }
    @Test
    public void exportSkillUrlTest()
    {
        long id = 1001;
        Exposer exposer = service.exportSkillUrl(id);
        if (exposer.isExposed())
        {
            logger.info("exposer={}",exposer);
            long userPhone = 17091931517L;
            String md5 = exposer.getMd5();
            try
            {
                SeckillExecution execution = service.excuteSeckill(id,userPhone,md5);
                logger.info("excution={}",execution);
            }catch (RepeatKillException e)
            {
                logger.error(e.getMessage());
            }catch (SeckillCloseException e)
            {
                logger.error(e.getMessage());
            }

        }else
        {
            logger.warn("exposer={}",exposer);
        }
    }
}
