/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
 
package com.sns.biz.vo;

import java.util.HashMap;
/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
public class DataMap extends HashMap<String, Object>{
	HashMap<String, Object> data;

	private void dataMap() {}
	
	public DataMap() {
		data = new HashMap<String, Object>();
		data.put("code", 0);
		data.put("message", "실패");
	};
	
	public DataMap(HashMap<String, Object> data) {
		this.data = data;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}
	
	public Object get(String key){
		return data.get(key);
	}
	
	public void remove(String key){
		data.remove(key);
	}
	
	public void setCode(int code){
		if(code > 0){
			data.put("code", code);
			data.put("message", "성공");
		}
	}
	
	public void setMap(HashMap<String,Object> data){
		this.data = data;
	}

	public Object put(String key,Object value){
		return data.put(key, value);
	}

	
}
