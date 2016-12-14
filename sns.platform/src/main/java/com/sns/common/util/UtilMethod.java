/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.common.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;

import com.sns.biz.file.FileDAO;
import com.sns.biz.member.MemberDAO;
import com.sns.biz.vo.CommandMap;
import com.sns.common.constant.NetworkConstants;
/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
public class UtilMethod {
	
	@Resource MemberDAO memberDao;
	
	public UtilMethod() {}
	
	public static void directoryCreate(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	public static void fileDelete(List<HashMap<String, Object>> delfileList,String localpath){
		for(HashMap<String, Object> map : delfileList){
			if(map != null){
				String delfilePath = localpath+map.get("filePath");
				File file = new File(delfilePath);
				if(file.exists()){
					file.delete();
				}
			}
		}
	}

	public HashMap<String,Object> getMap(String key){
		HashMap<String,Object> userMap = memberDao.searchMember(NumberUtils.toInt(key));
		
		return userMap;
	}
	
}
