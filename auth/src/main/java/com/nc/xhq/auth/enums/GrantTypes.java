package com.nc.xhq.auth.enums;

/**
 * Token认证类型
 */
public enum GrantTypes {

	/**
	 * 授权码认证
	 */
	authorization_code,

	/**
	 * 客户端认证
	 */
	client_credentials,

	/**
	 * 秘密认证
	 */
	password,

	/**
	 * 刷新Token
	 */
	refresh_token,

	/**
	 * 验证码认证
	 */
	captcha,

	/**
	 * 短信认证
	 */
	sms,

	/**
	 * 微信认证
	 */
	wechat,
	/**
	 * 二维码登录
	 */
	qrcode,

	/**
	 * 手机号一键登录
	 */
	phone_one_key,
}
