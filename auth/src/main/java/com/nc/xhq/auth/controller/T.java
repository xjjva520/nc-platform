package com.nc.xhq.auth.controller;

import com.alibaba.fastjson.JSON;
import com.nc.api.crypto.annotation.crypto.CryptoAes;
import com.nc.api.crypto.annotation.decrypt.Decrypt;
import com.nc.auth.core.entity.NcPrincipal;
import com.nc.auth.core.props.TokenProperties;
import com.nc.component.redis.client.RedisLockClient;
import com.nc.component.redis.client.RedisOprClient;
import com.nc.component.redis.enums.LockType;
import com.nc.core.common.utils.*;
import com.nc.xhq.auth.entity.info.WxUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.channels.ServerSocketChannel;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/24
 * @package: com.nc.xhq.auth.controller
 */
@RestController
@RequestMapping("/t")
public class T {

    @Autowired
    private RedisOprClient redisOprClient;
    @Autowired
    private RedisLockClient redisLockClient;

    @RequestMapping("a")
    public String aa(NcPrincipal ncPrincipal) throws InterruptedException {
        boolean b = redisLockClient.tryLock("123456", LockType.REENTRANT, 5, 5, TimeUnit.SECONDS);
        try{
            if(b){
                System.out.println("lock------------------------------");
            }
        }catch (Exception e){

        }finally {
            redisLockClient.unLock("123456", LockType.REENTRANT);
        }

        System.out.println(JSON.toJSONString(ncPrincipal));
        WxUserInfo wxUserInfo = new WxUserInfo();
        wxUserInfo.setUnionId("sadasda");
        wxUserInfo.setPhoneNumber("adadad");
        wxUserInfo.setOpenId("123213123123");
        redisOprClient.setObj("xujianjianjian",wxUserInfo,2000,TimeUnit.SECONDS);

        WxUserInfo map1 = (WxUserInfo) redisOprClient.getObj("xujianjianjian");
        return JSON.toJSONString(map1);
    }

    @PostMapping("b")
    @ResponseBody
    @CryptoAes
    public WxUserInfo bb(@RequestBody WxUserInfo wxUserInfo){
       /*  Map<String,String> map = new HashMap<>();
         map.put("asdasd","asdasd");
        map.put("12321","1111");*/
        wxUserInfo.setUnionId("sadasda");
        wxUserInfo.setPhoneNumber("adadad");
        wxUserInfo.setOpenId("123213123123");
        redisOprClient.setObj("xujianjianjian",wxUserInfo,2000,TimeUnit.SECONDS);

        WxUserInfo map1 = (WxUserInfo) redisOprClient.getObj("xujianjianjian");
        return map1;
    }

    public static void main(String[] args) throws IOException {
       /* String aa = "APP:APP";
        String decode = Base64Util.encode(aa);
        System.out.println(decode);*/
        //

    }

    public static String crpty(String aesKey){
        String aa = "AZI8qqIa2C9I+4AfrMx12XJolXxSwwdlfxwpkrhbKc7G2FFjrACawQ8JE7EV62sTgZPhGaPpdYyCUUhFLcKKLQ==";
        byte[] bytes = AesUtil.decryptFormBase64(aa.getBytes(), aesKey);
        String s = new String(bytes);
        System.out.println(s);
        return s;
    }
}
