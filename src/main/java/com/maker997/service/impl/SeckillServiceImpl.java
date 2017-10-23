package com.maker997.service.impl;

import com.maker997.dao.SeckillDao;
import com.maker997.dao.SuccessKilledDao;
import com.maker997.dto.Exposer;
import com.maker997.dto.SeckillExecution;
import com.maker997.entity.Seckill;
import com.maker997.entity.SuccessKilled;
import com.maker997.enums.SeckillStateEnum;
import com.maker997.exception.RepeatKillException;
import com.maker997.exception.SeckillCloseException;
import com.maker997.exception.SeckillException;
import com.maker997.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * @author liujiaqi
 * @date 2017/10/20
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String salt = "dashgfkhaglkohrpawe*3PQ7623@^UF[EiweEW0[YF0W0E";

    @Override
    public Seckill getById(long seckillId)
    {
        return seckillDao.getById(seckillId);
    }

    @Override
    public List<Seckill> getSeckillList()
    {
        return seckillDao.listAll(0,4);
    }

    @Override
    public Exposer exportSkillUrl(long seckillId)
    {
        Seckill seckill = seckillDao.getById(seckillId);
        if (seckill == null)
        {
            Exposer exposer = new Exposer(false,seckillId);
            return exposer;
        }

        Date now = new Date();
        long nowTime = now.getTime();
        long start = seckill.getStartTime().getTime();
        long end = seckill.getEndTime().getTime();

        if (nowTime<start || nowTime>end)
        {
           Exposer exposer = new Exposer(seckillId,nowTime,start,end);
           return exposer;
        }
        String md5 = this.getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    @Override
    @Transactional
    public SeckillExecution excuteSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException
    {
        //1.检查 md5
        if (md5 == null || !md5.equals(getMD5(seckillId)))
        {
            throw new SeckillException("seckill data rewrite");
        }

        //2.执行减库存 + 记录购买成功行为
        Date now = new Date();
        try
        {
            int updatCount = seckillDao.reduceNumber(seckillId,now);
            if (updatCount<=0)
            {
                throw new SeckillCloseException("seckill closed");
            }else
            {
                //3.记录购买成功行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
                if (insertCount<=0)
                {
                    throw new RepeatKillException("repeate seckill");
                }else
                {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.getByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
                }
            }

        }catch (SeckillCloseException e0)
        {
            throw e0;
        }catch (RepeatKillException e1)
        {
            throw e1;
        }catch (Exception e)
        {
            //记录异常
            logger.error(e.getMessage(),e);
            //将编译时期异常转化为运行时期异常
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }

    }

    /**
     * 产生 MD5加密串
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId)
    {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
