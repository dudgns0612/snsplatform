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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.sns.biz.vo.CommandMap;
import com.sns.biz.vo.DataMap;
import com.sns.common.constant.NetworkConstants;
/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
@Controller
public class ReplyController {
	@Resource(name = "replyservice") ReplyService replyService;
	@Resource(name = "jsonView") MappingJackson2JsonView jsonView;
	
	
	public ReplyController() {
		//Default Constructor
	}
	
	/**
	 * 댓글 작성
	 * @param boardNum
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value= "board/{boardNum}/reply", method = RequestMethod.POST)
	public ModelAndView replyCreate(@PathVariable("boardNum") String boardNum, CommandMap commandMap){
		DataMap dataMap = new DataMap();
		int boardNumber = NumberUtils.toInt(boardNum);
		if(boardNumber > 0){
			if(replyService.createReply(boardNumber,commandMap)==1){
				dataMap.setCode(NetworkConstants.OK_CODE);
			}
		}
		return new ModelAndView(jsonView,dataMap.getData());
		
	}
	
	/**
	 * 댓글 조회
	 * @param boardNum
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value= "/board/{boardNum}/replys", method = RequestMethod.GET)
	public ModelAndView replyList(@PathVariable("boardNum")String boardNum,CommandMap commandMap){
		DataMap dataMap = new DataMap();
		int boardNumber = NumberUtils.toInt(boardNum);
		commandMap.put("boardNum", boardNumber);
		List<HashMap<String, Object>> replyList = replyService.listReply(commandMap);
		if(replyList != null){
			dataMap.put("data", replyList);
			dataMap.setCode(NetworkConstants.OK_CODE);
		}
		
		return new ModelAndView(jsonView,dataMap.getData());	
	}
	
	/**
	 * 댓글 수정
	 * @param boardNum
	 * @param replyNum
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value= "board/{boardNum}/reply/{replyNum}", method = RequestMethod.POST)
	public ModelAndView replyModify(@PathVariable("boardNum") String boardNum,@PathVariable("replyNum") String replyNum,CommandMap commandMap){
		DataMap dataMap = new DataMap();
		int boardNumber = NumberUtils.toInt(boardNum);
		int replyNumber = NumberUtils.toInt(replyNum);
		if (boardNumber > 0 && replyNumber >0 ){
			commandMap.put("boardNum", boardNumber);
			commandMap.put("replyNum", replyNumber);
			if(replyService.modifyReply(commandMap) == 1){
				dataMap.setCode(NetworkConstants.OK_CODE);
			}
		}
		
		return new ModelAndView(jsonView,dataMap.getData());	
	}
	
	
	/**
	 * 댓글 삭제
	 * @param boardNum
	 * @param replyNum
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value= "board/{boardNum}/reply/{replyNum}", method = RequestMethod.DELETE)
	public ModelAndView replyDelete(@PathVariable("boardNum") String boardNum,@PathVariable("replyNum") String replyNum,CommandMap commandMap){			
		DataMap dataMap = new DataMap();
		int boardNumber = NumberUtils.toInt(boardNum);
		int replyNumber = NumberUtils.toInt(replyNum);
		if (boardNumber > 0 && replyNumber >0 ){
			commandMap.put("boardNum", boardNumber);
			commandMap.put("replyNum", replyNumber);
			if(replyService.removeReply(commandMap) == 1){
				dataMap.setCode(NetworkConstants.OK_CODE);
			}
		}
		
		return new ModelAndView(jsonView, dataMap.getData());	
	}
	
	
	
}
