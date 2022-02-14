package com.nc.xhq.auth.service;

import com.nc.core.common.api.R;
import com.nc.xhq.auth.entity.info.WxUserInfo;

/**
 * @description: 微信相关业务处理接口
 * @author: jjxu
 * @time: 2022/1/18
 * @package: com.nc.xhq.auth.service
 */
public interface IWxDealService {

     R<WxUserInfo> encryptUserInfo(String wxCode, String encryptedData, String iv);
}
