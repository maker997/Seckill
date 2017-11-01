//存放主要交互逻辑的js代码
// javascript 模块化(package.类.方法)

var seckill = {

    //封装秒杀相关ajax的url
    URL: {
        now: function(){
            return '/seckill/time/now';
        },
        exposer: function(seckillId){
            return "/seckill/"+seckillId+"/exposer";
        },
        excute: function(seckillId,md5){

            return "/seckill/"+seckillId+"/"+md5+"/execute";
        }
    },

    //验证手机号
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;//直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
        } else {
            return false;
        }
    },

    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            //手机验证和登录,计时交互
            //规划我们的交互流程
            //在cookie中查找手机号
            var userPhone = $.cookie('userPhone');
            //验证手机号
            if (!seckill.validatePhone(userPhone)) {
                //绑定手机 控制输出
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false//关闭键盘事件
                });

                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log("inputPhone: " + inputPhone);
                    if (seckill.validatePhone(inputPhone)) {
                        //电话写入cookie(7天过期)
                        $.cookie('userPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //验证通过　　刷新页面
                        window.location.reload();
                    } else {
                        //todo 错误文案信息抽取到前端字典里
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                    }
                });
            }

            //倒计时交互获取系统时间
            var seckillId = params['seckillId'];
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            $.get(seckill.URL.now(),{},function(result){
                if (result && result['success'])
                {
                    var nowTime = result['data'];
                    seckill.countDown(seckillId,nowTime,startTime,endTime);
                }else
                {
                    console.log("resutl"+result);
                }

            });
        }

    },

    //倒计时方法
    countDown:function(seckillId,nowTime,startTime,endTime){

        var seckillBox = $('#seckill-box');

        if (nowTime>endTime)
        {
            console.log('秒杀结束');
            seckillBox.html('秒杀结束!');
        }else if (nowTime<startTime)
        {
            console.log("秒杀倒计时");
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function(event){
                var format = event.strftime('倒计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('countdownFinish',function(){
                //倒计时结束,获取秒杀地址,控制显示逻辑. 用户执行秒杀操作
                seckill.handleSeckillKill(seckillId,seckillBox);
            });

        }else
        {
            console.log("正在秒杀");
            seckill.handleSeckillKill(seckillId,seckillBox);
        }
    },

    /**
     * 处理秒杀逻辑
     */
    handleSeckillKill:function(seckillId,node){
        //倒计时结束,获取秒杀地址,控制显示逻辑. 用户执行秒杀操作
        node.hide()
            .html('<button class="btn btn-primary btn-lg" id="seckillBtn">开始秒杀</button>');
        $.get(seckill.URL.exposer(seckillId),{},function(result){

            if (result && result['success'])
            {
                var exposer = result['data'];
                if (exposer['exposed'])
                {
                    //秒杀开启
                    var seckillBtn = $('#seckillBtn');
                    var md5 = exposer['md5'];
                    console.log(seckill.URL.excute(seckillId,md5));
                    seckillBtn.one('click',function(){
                        //让秒杀按钮变灰色
                        seckillBtn.addClass('disabled');
                        //执行秒杀操作
                        console.log('url:'+seckill.URL.excute(seckillId,md5));
                        $.post(seckill.URL.excute(seckillId,md5),{},function(result)
                        {
                            if (result && result['success'])
                            {
                                var seckillExcute =result['data'];
                                node.html('<span class="label label-success">'+seckillExcute['stateInfo']+'<span>')
                            }
                        });
                    });
                    node.show();
                }else
                {
                    var start = exposer['start'];
                    var end = exposer['end'];
                    var now = exposer['now'];
                    seckill.countDown(seckillId,now,start,end);
                }

            }else
            {
                console.log('result:'+result);
            }
        })
    }




}
