package com.nc.xhq.auth.entity.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 微信获取openId 以及会话秘钥信息
 * @author: jjxu
 * @time: 2021/8/2
 * @package: com.sino.platform.auth.entity
 */
@Data
public class Code2SessionResp implements Serializable {

	private static final long serialVersionUID = -4639500981117844003L;

	//用户唯一标识
	private String openId;

	//会话密钥
	private String session_key;

	//用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回
	private String unionid;

	//错误码
	private String errcode;

	//错误信息
	private String errmsg;



}
