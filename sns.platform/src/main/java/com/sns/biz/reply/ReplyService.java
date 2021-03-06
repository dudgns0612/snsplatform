/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.reply;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sns.biz.board.BoardDAO;
import com.sns.biz.vo.CommandMap;
//import com.sns.common.push.service.PushService;

/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
@Service("replyservice")
public class ReplyService {
	
	public ReplyService() {
		//Default Constructor
	}
	
	@Resource private ReplyDAO replyDao;
	@Resource private BoardDAO boardDao;
//	@Autowired private PushService pushService;
	
	private static final Logger logger = LoggerFactory.getLogger(ReplyService.class);
	
	
	public int createReply(int boardNum,CommandMap commandMap){
		logger.info("●●●●●●●●●●●●●●●● ReplyCreate ●●●●●●●●●●●●●●●●");
		int check = 0;
		HashMap<String, Object> member = (HashMap<String, Object>) commandMap.get("member");
		commandMap.put("userNum", member.get("userNum"));
		commandMap.put("boardNum", boardNum);
		check = replyDao.insertReply(commandMap.getMap());
		if(check == 1){
			boardDao.updateAddReplyCnt(boardNum);
		}
		
		try{
//			pushService.pushForWriteReply(boardNum);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return check;
		
	}
	
	public List<HashMap<String, Object>> listReply(CommandMap commandMap){
		logger.info("●●●●●●●●●●●●●●●● REPLYLIST ●●●●●●●●●●●●●●●●");
		int pageNum = NumberUtils.toInt(String.valueOf(commandMap.get("pageNum")));
		if(pageNum == 0){
			pageNum =5;
		}
		int lastReplyNum = NumberUtils.toInt(String.valueOf(commandMap.get("lastReplyNum")));
		commandMap.put("pageNum", pageNum);
		commandMap.put("lastReplyNum", lastReplyNum);
		List<HashMap<String, Object>> replyList= replyDao.selectReplyList(commandMap.getMap());
			
		return replyList;	
	}
	
	public int modifyReply(CommandMap commandMap){
		logger.info("●●●●●●●●●●●●●●●● Reply Edit ●●●●●●●●●●●●●●●●");
		int check = 0;
		check = replyDao.updateReply(commandMap.getMap());		
	
		return check;
	}
	
	
	
	public int removeReply(CommandMap commandMap){
		logger.info("●●●●●●●●●●●●●●●● replyRemove ●●●●●●●●●●●●●●●●");
		int check = 0;
		check = replyDao.deleteReply(commandMap.getMap());
		if(check > 0){
			int boardNum = NumberUtils.toInt(String.valueOf(commandMap.get("boardNum")));
			boardDao.updateDelReplyCnt(boardNum);
		}

		return check;
	}
	
}

