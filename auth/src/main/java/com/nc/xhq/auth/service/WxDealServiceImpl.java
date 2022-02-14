package com.nc.xhq.auth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nc.core.common.api.R;
import com.nc.core.common.utils.OkHttpUtils;
import com.nc.xhq.auth.entity.info.WxUserInfo;
import com.nc.xhq.auth.entity.resp.Code2SessionResp;
import com.nc.xhq.auth.util.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/18
 * @package: com.nc.xhq.auth.service
 */
@Slf4j
@Service
public class WxDealServiceImpl implements IWxDealService{

    public final String WX_YAZHI_SESSION_KEY = "wx:session_key:%s";

    private static String appId ="wxb937b5b8dc333c1a";

    private static String secret = "51657c945254dd27d9c120545563d224";

    private static String code2sessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code&session_key=fdakop";

    @Override
    public R<WxUserInfo> encryptUserInfo(String wxCode, String encryptedData, String iv) {
        WxUserInfo wxUserInfo = new WxUserInfo();

        String url = String.format(code2sessionUrl, appId, secret, wxCode);
        //调用微信平台，获取openid以及sessionKey
        Code2SessionResp resp = OkHttpUtils.sendGetRequest(url, Code2SessionResp.class);
        if(resp==null || StringUtils.isNotEmpty(resp.getErrcode())){
            throw new RuntimeException();
        }
        try {
            //获取到会话秘钥及openId---然后通过会话秘钥进行用户信息进行解密(看微信小程序开发文档)
            String phoneJson = WeChatUtil.decryptData(encryptedData,resp.getSession_key(),iv);
            JSONObject jsonObject = JSON.parseObject(phoneJson);
            String phoneNumber = jsonObject.getString("phoneNumber");
            wxUserInfo.setOpenId(resp.getOpenId());
            wxUserInfo.setPhoneNumber(phoneNumber);
            wxUserInfo.setUnionId(resp.getUnionid());
            return R.data(wxUserInfo);
        }catch (Exception e){
           log.error("encrypt User info is error",e);
        }
        return R.fail("encrypt user info error");
    }
}
