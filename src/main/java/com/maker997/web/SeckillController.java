package com.maker997.web;

import com.maker997.dto.Exposer;
import com.maker997.dto.SeckillExecution;
import com.maker997.dto.SeckillResult;
import com.maker997.entity.Seckill;
import com.maker997.enums.SeckillStateEnum;
import com.maker997.exception.RepeatKillException;
import com.maker997.exception.SeckillCloseException;
import com.maker997.exception.SeckillException;
import com.maker997.service.SeckillService;
import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 秒杀控制层
 *
 * @author liujiaqi
 * @date 2017/10/23
 */
@Controller
@RequestMapping("/seckill")// url:模块/资源/{id}/细分
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    //获取列表页
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model)
    {
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        /**
         * jsp + model = ModelAndView
         * /WEB-INF/jsp/list.jsp
         */
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId,Model model){
        /**
         * 用户不穿 seckillId 或者乱传 seckillId
         */
        if (seckillId == null)
        {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null)
        {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,produces = {"application/json; charset=UTF-8"} )
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillId)
    {
        SeckillResult<Exposer> result;
        try
        {
            Exposer exposer = seckillService.exportSkillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e)
        {
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    /**
     * 执行秒杀操作
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execute",method = RequestMethod.POST,
            produces ={"application/json;charset=UTF-8"} )
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @CookieValue(value = "killPhone",required = false) Long phone,
                                                   @PathVariable("md5") String md5)
    {
        SeckillResult<SeckillExecution> result;

        try {
            SeckillExecution seckillExecution = seckillService.excuteSeckill(seckillId,phone,md5);
            result = new SeckillResult<SeckillExecution>(true,seckillExecution);
            return result;
        }catch (RepeatKillException e)
        {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);

        }catch (SeckillCloseException e)
        {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);

        }catch (Exception e)
        {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false,seckillExecution);
        }
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> time()
    {
        Date date = new Date();
        SeckillResult result = new SeckillResult(true,date.getTime());
        return result;
    }


}
