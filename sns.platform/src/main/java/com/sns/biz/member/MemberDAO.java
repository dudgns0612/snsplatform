/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.member;

import java.util.HashMap;
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
public class MemberDAO {
	
	@Resource
	private SqlSession sqlSession;

    public MemberDAO() {
    	//Default Constructor
    }
    
	private static final String NAMESPACE = "MemberMapper";
	
	
	public int insertMember(HashMap<String, Object> map){
		return sqlSession.insert(NAMESPACE+".insertMember", map);
	}
	
	public int deleteMember(HashMap<String, Object> map){
		return sqlSession.delete(NAMESPACE+".deleteMember", map);
	}

	public HashMap selectMember(String userId){
		return (HashMap)sqlSession.selectOne(NAMESPACE+".selectMember", userId);
	}
	
	public int pnsTokenUpdate(HashMap<String, Object> map){
		return sqlSession.update(NAMESPACE+".pnsTokenUpdate", map);
	}
	
	public HashMap<String,Object> loginMember(HashMap<String,Object> map){
		return sqlSession.selectOne(NAMESPACE+".loginMember", map);
	}
	
	public HashMap<String, Object> searchMember(int userNum){
		return sqlSession.selectOne(NAMESPACE+".searchMember", userNum);
	}
	
	public int updateMember(HashMap<String, Object> map){
		return sqlSession.update(NAMESPACE+".updateMember", map);
	}
	
	public String nickNameOverlap(String userNick){
		return sqlSession.selectOne(NAMESPACE+".nickNameOverLap",userNick);
	}
	
	public String idOverlap(String userId){
		return sqlSession.selectOne(NAMESPACE+".idOverLap",userId);
	}
	
	public String passwordCheck(HashMap<String, Object> map){
		return sqlSession.selectOne(NAMESPACE+".passwordCheck", map);
	}
	
	public String selectImgPath(int userNum){
		return sqlSession.selectOne(NAMESPACE+".selectImgPath",userNum);
	}
	
	public HashMap<String,Object> selectOldImgPath(int userNum){
		return sqlSession.selectOne(NAMESPACE+".selectOldImgPath", userNum);
	}
	
	public HashMap<String,Object> selectSecurityValue(String userId){
		return sqlSession.selectOne(NAMESPACE+".selectSecurityValue",userId);
	}
}
