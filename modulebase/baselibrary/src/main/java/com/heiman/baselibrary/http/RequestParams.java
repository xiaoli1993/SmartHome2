package com.heiman.baselibrary.http;


import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @Date 2015年7月17日 下午5:46:35
 * @Description []
 * @version 1.0.0
 */
public class RequestParams {
	HashMap<String, Object> map;

	public RequestParams() {
		// TODO Auto-generated constructor stub
		map = new HashMap<String, Object>();
	}

	public void put(String key, Object value) {
		map.put(key, value);
	}

	public String getStringData() {
		JSONObject data = XlinkUtils.getJsonObject(map);
		return data.toString();
	}

	public HttpEntity getJsonEntity() {
		JSONObject data = XlinkUtils.getJsonObject(map);
		StringEntity entity = null;
		try {
			entity = new StringEntity(data.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return entity;
	}
}
