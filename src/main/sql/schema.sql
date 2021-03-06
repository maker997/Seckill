-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
CREATE TABLE seckill(
  'seckill_id' BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  'name' VARCHAR(120) NOT NULL COMMENT '商品名称',
  'number' int NOT NULL COMMENT '商品数量',
  'start_time' TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
  'end_time' TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  'create_time' TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
INSERT INTO
  seckill (name,number,start_time,end_time)
VALUES
  ('1000元秒杀 iPhone8',100,'2017-10-1 00:00:00','2017-10-2 00:00:00'),
  ('500元秒杀小米mix2',200,'2017-10-1 00:00:00','2017-10-2 00:00:00'),
  ('2000元秒杀MacBook',50,'2017-10-1 00:00:00','2017-10-2 00:00:00');

-- 秒杀成功明细表
CREATE TABLE success_killed(
  'seckill_id' BIGINT NOT NULL COMMENT '秒杀商品 id',
  'user_phone' BIGINT NOT NULL COMMENT '用户手机号',
  'state' TINYINT NOT NULL DEFAULT -1 COMMENT '-1 无效 0 秒杀成功 1 商品已付款 2 商品已发货',
  'create_time' TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone),/*联合主键,一个用户在同一时间只能秒杀一个商品,所以 */
  KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
