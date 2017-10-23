package com.maker997.dto;

import lombok.Data;

/**
 *
 * @author liujiaqi
 * @date 2017/10/31
 */
@Data
public class Exposer {

    private boolean exposed;

    private String md5;

    private long seckillId;

    private long now;

    private long start;

    private long end;

    public Exposer(boolean exposed, String md5, long seckillId)
    {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(long seckillId, long now, long start, long end)
    {
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long seckillId)
    {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}
