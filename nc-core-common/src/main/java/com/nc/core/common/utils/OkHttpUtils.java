/**
 * Project Name: okhttp-common
 * File Name: OkHttpUtils.java
 * Package Name: com.example.okhttp_common.utils
 * Date: 2018年5月23日下午3:24:54
 * All rights Reserved, Designed By MiGu. Copyright: Copyright(C) 2018-2020.
 * Company MiGu Co., Ltd.
 */

package com.nc.core.common.utils;
/**
 * Name: OkHttpUtils <br/>
 * Description: OkHttpUtils基于okhttp3实现的，提供了HTTPGet及Post方法 . <br/>
 * Date: 2018年5月23日 下午3:24:54 <br/>
 *
 * @author shenq
 * @version
 * @see
 */

import com.alibaba.fastjson.JSON;
import com.nc.core.common.api.R;
import com.nc.core.common.props.OkHttpConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.Request.Builder;


import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public final class OkHttpUtils {

    // 由于OkHttpClient内部处理了并发，多线程和Socket重用等问题，为了节省资源，整个应用中使用一个OkHttpClient对象即可
    private static volatile OkHttpClient sOkHttpClientSingleton = null;

    private static final String MEDIA_TYPE = "application/json;charset=utf-8";

    private OkHttpUtils() {

    }

    private static  OkHttpClient getInstance() {
		if(sOkHttpClientSingleton!=null){
			return sOkHttpClientSingleton;
		}
    	synchronized(OkHttpUtils.class){
			if (sOkHttpClientSingleton == null) {
				OkHttpConfigurationProperties prop = SpringApplicationUtil.getBean(OkHttpConfigurationProperties.class);
				sOkHttpClientSingleton = new OkHttpClient().newBuilder().connectTimeout(prop.getConnectTimeOut(), TimeUnit.MINUTES)
						.readTimeout(prop.getReadTimeOut(), TimeUnit.MINUTES).writeTimeout(prop.getWriteTimeOut(), TimeUnit.MINUTES)
						.protocols(Collections.singletonList((Protocol.HTTP_1_1))).build();
			}
			return sOkHttpClientSingleton;
		}
    }

   /* *
    * @Author jjxu
    * @Description
    * @Date 19:31 2021/7/12
    * @Param [url, jsonParams]
    **/
    public static R<String> sendPostRequest(String url, Map<String,Object> requestMap, Map<String,String> headParams) {
        long start = System.currentTimeMillis();

        try {
            log.info("Send the OKhttp request,and the parameter ={}", JSON.toJSONString(requestMap));
            Builder builder = new Builder().url(url);
			okhttp3.Headers.Builder headBuilder = new okhttp3.Headers.Builder();
            if(CollectionUtil.isNotEmpty(headParams)){
				headParams.entrySet().forEach(s->{
					headBuilder.add(s.getKey(),s.getValue());
				});
			}
			Headers headers = headBuilder.build();

			Request request = builder.headers(headers).post(RequestBody.create(MediaType.parse(MEDIA_TYPE), JSON.toJSONString(requestMap))).build();

			Response response = OkHttpUtils.execute(request);
            String msg = response.body().string();
			log.info("Send the OKhttp request and receive the returned request url = {},result = {},cost={}", url, msg, System.currentTimeMillis() - start);
            return R.data(msg);
        } catch (Exception e) {
            log.error("Send the OkHttp request fail, error", e);
            return R.fail("Send the OkHttp error");
        }
    }

	/* *
	 * @Author jjxu
	 * @Description
	 * @Date 19:31 2021/7/12
	 * @Param [url, jsonParams]
	 **/
	public static R<String> sendPost(String url, Map<String,Object> requestMap, Map<String,String> headParams) {
		long start = System.currentTimeMillis();

		try {
			log.info("Send the OKhttp request,and the parameter ={}",JSON.toJSONString(requestMap));
			Builder builder = new Builder().url(url);
			okhttp3.Headers.Builder headBuilder = new okhttp3.Headers.Builder();
			if(CollectionUtil.isNotEmpty(headParams)){
				headParams.entrySet().forEach(s->{
					headBuilder.add(s.getKey(),s.getValue());
				});
			}
			Headers headers = headBuilder.build();
			FormBody.Builder formBuild = new FormBody.Builder();
			if(CollectionUtil.isNotEmpty(requestMap)){
				headParams.entrySet().forEach(s->{
					formBuild.add(s.getKey(),s.getValue());
				});
			}
			Request request = builder.headers(headers).post(formBuild.build()).build();
			Response response = OkHttpUtils.execute(request);
			String msg = response.body().string();
			log.info("Send the OKhttp request and receive the returned request url = {},result = {},cost={}", url, msg, System.currentTimeMillis() - start);
			return R.data(msg);
		} catch (Exception e) {
			log.error("Send the OkHttp request fail, error", e);
			return R.fail("Send the OkHttp error");
		}
	}

	public static <T> T sendGetRequest(String url, Class<T> responseClazz) {
		T result = null;
		String responseBody = null;
		try {
			Request request = new Request.Builder().url(url).build();
			try (Response response = OkHttpUtils.execute(request)) {
				responseBody = response.body().string();
			} catch (Exception e) {
				log.error("Http request error: url: {},  error: {}", url, e);
			}
			log.info("Http request and response: url: {}, result: {}", url,  responseBody);
			result = (T) JSON.parseObject(responseBody, responseClazz);
		} catch (Exception e) {
			log.error("Http request or response error, url is: {}, request is: {}, response is: {}, error ", url, result,e);
		}
		return result;
	}

	public static void requestAsynchronousGet(String url) {
		long startTime = System.currentTimeMillis();
		Request request = new Request.Builder().url(url).build();
		Call call = OkHttpUtils.getInstance().newCall(request);
		doCallBack(call);
		long endTime = System.currentTimeMillis();
		log.info("OkHttpUtils...requestAsynchronousGet...url=" + url + ";time=" + (endTime - startTime) + "ms");
	}

	/**
	 * doCallBack:(执行回调方法)
	 *
	 * @param call
	 */
	private static void doCallBack(Call call) {
		// 请求加入调度
		call.enqueue(new Callback() {
			// 请求成功的回调
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try (ResponseBody responseBody = response.body()) {
					log.info("OkHttpUtils...doCallBack...onResponse...url=" + call.request().url() + ";response="
						+ responseBody.string());
				}
			}

			// 请求失败的回调
			@Override
			public void onFailure(Call call, IOException e) {
				log.error("OkHttpUtils...doCallBack...onFailure...url=" + call.request().url() + ";exception="
					+ e.getMessage());
			}
		});
	}

	public static <T> T sendGetRequestWithRetry(int retryTimes, String url, String requestBody,
												Class<T> responseClazz) {
		T result = null;
		// 已尝试次数
		int requstTimes = 0;
		// 如果尝试次数小于配置上限，则重试
		while (requstTimes < retryTimes && null == result) {
			result = sendGetRequestAndGetResult(url, requestBody, responseClazz);
			requstTimes++;
			if (null == result) {
				log.error("response is null and request already try " + requstTimes + " times.");
			}
		}
		return result;
	}

	public static <T> T sendGetRequestAndGetResult(String url, String requestBody, Class<T> responseClazz) {
		T result = null;
		String responseBody = null;
		try {
			Request request = new Request.Builder().url(url + "?" + requestBody).build();
			try (Response response = OkHttpUtils.execute(request)) {
				responseBody = response.body().string();
			} catch (Exception e) {
				log.error("Http request error: url: {},  error: {}", url, e);
			}
			log.info("Http request and response: url: {}, requestBody: {}, result: {}", url, requestBody, responseBody);
			result = (T) JSON.parseObject(responseBody, responseClazz);
		} catch (Exception e) {
			log.error("Http request or response error, url is: {}, request is: {}, response is: {}, error ", url,
				requestBody, result,e);
		}
		return result;
	}

	/**
	 * execute:(执行同步请求)
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private static Response execute(Request request) throws IOException {
		return OkHttpUtils.getInstance().newCall(request).execute();
	}
}
