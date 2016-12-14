/**
 * 
 */
package com.sns.common.push.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.sns.common.push.vo.SPPushReqVO;

/**
 * @author openit
 *
 */
@Repository
public class PushDao {
	
	@Inject
	private SqlSession session;

	private static String NAME_SPACE = "PushMapper";
	
	/**
	 * 푸시발송등록
	 * SP : SP_PUT_PUSH_DATA
	 * 
	 * @param boardNum
	 */
	public void putPush(String type, Integer boardNum) {
		session.selectOne(NAME_SPACE + ".spPutPushData", new SPPushReqVO(type, boardNum));
	}
	

}
