package com.maker997.dao;

import com.maker997.dao.SeckillDao;
import com.maker997.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by liujiaqi on 17/10/2017.
 *
 * 配置 spring和 junit整合,junit 启动时加载 springIOC容器
 * spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉 junit spring 的配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    /**
     * resource 标签
     * 标注的元素会去 spring 容器中查找实现类
     */
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void getByIdTest(){
        long id = 1000;
        Seckill seckill = seckillDao.getById(id);
        System.out.println(seckill);
    }

    @Test
    public void listAllTest(){
        List<Seckill> seckillList = seckillDao.listAll(1,10);
        for (Seckill seckill : seckillList) {
            System.out.println(seckill);
        }
    }

    @Test
    public void reduceNumberTest(){
        Date killTime = new Date();
        int updateNumber = seckillDao.reduceNumber(1000L,killTime);
        System.out.println("updateNumber = "+updateNumber);
    }
}
