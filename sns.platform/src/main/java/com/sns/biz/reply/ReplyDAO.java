/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.reply;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
@Repository
public class ReplyDAO {
	private static final String NAMESPACE = "ReplyMapper";
	
	@Resource SqlSession sqlSessoin;

	public ReplyDAO() {
		//Default Constructor
	}
	
	public int insertReply(HashMap<String, Object> map){
		return sqlSessoin.insert(NAMESPACE+".insertReply",map);			
	}
	
	public int deleteReply(HashMap<String,Object> map){
		return sqlSessoin.delete(NAMESPACE+".deleteReply",map);
	}
	
	public int updateReply(HashMap<String,Object> map){
		return sqlSessoin.update(NAMESPACE+".updateReply",map);
	}
	
	public List<HashMap<String,Object>> selectReplyList(HashMap<String,Object> map){
		return sqlSessoin.selectList(NAMESPACE+".selectReplyList",map);
	}
}
