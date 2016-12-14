/**
 * 
 */
package com.sns.common.push.service;

import javax.inject.Inject;
import org.springframework.stereotype.Service;

import com.sns.common.push.dao.PushDao;

/**
 * @author openit
 *
 */
@Service
public class PushService {
	
	@Inject
	private PushDao dao;
	
	private String PUSH_TYPE_NORMAL = "01";
	
	private String PUSH_TYPE_NEW_BOARD = "02";
	
	private String PUSH_TYPE_NEW_REPLY = "03";
	
	/**
	 * 글 등록 후 푸시등록
	 * 
	 * @param boardNum
	 * @throws Exception
	 */
	public void pushForWriteBoard(Integer boardNum) throws Exception{
		dao.putPush(PUSH_TYPE_NEW_BOARD, boardNum);
	}
	
	/**
	 * 댓글 등록 후 푸시등록
	 * 
	 * @param boardNum
	 * @throws Exception
	 */
	public void pushForWriteReply(Integer boardNum) throws Exception{
		dao.putPush(PUSH_TYPE_NEW_REPLY, boardNum);
	}
	
	/**
	 * 공지용 푸시등록(발송대상 전체)
	 * 
	 * @throws Exception
	 */
	public void pushForNormal() throws Exception{
		dao.putPush(PUSH_TYPE_NORMAL, null);
	}

}
