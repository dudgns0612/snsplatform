/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.vo;

import java.util.HashMap;
import javax.websocket.Session;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
public class CommandMap {
	private HashMap<String,Object> map = new HashMap<String, Object>();
	private MultiValueMap<String, MultipartFile> multipartMap ;
	private Session session;
	
	
	public CommandMap() {
		//Default Constructor
	}
	
	public CommandMap(HashMap<String, Object> map, MultiValueMap<String, MultipartFile> multipartMap, Session session) {
		this.map = map;
		this.multipartMap = multipartMap;
		this.session = session;
	}
	

	public Object get(String key){
		return map.get(key);
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void put(String key,Object value){
		map.put(key, value);
	}
	
	public boolean containsKey(String key){
		return map.containsKey(key);
	}
	
	public boolean multiparContainsMap(){
		if(multipartMap == null){
			return false;
		}
		return multipartMap.size() > 0;
	}
	
	
	public boolean containsValue(Object value){
		return map.containsValue(value);
	}
	
	public HashMap<String, Object> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}
	
	public void remove(String key){
		map.remove(key);
	}
	
	public MultiValueMap<String, MultipartFile> getMultipartMap() {
		return multipartMap;
	}
	
	public void setMultipartMap(MultiValueMap<String, MultipartFile> multipartMap) {
		this.multipartMap = multipartMap;
	}
	
	
	
}
