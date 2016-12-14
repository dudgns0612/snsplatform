/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.common;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
@Controller
public class PageControll {
	
	private static final Logger logger = LoggerFactory.getLogger(PageControll.class);
	
	public PageControll() {
		//Default Constructor
	}
	
	@RequestMapping(value= {"/home", "/"})
	public String HomeView(){
		return "home/homeview";
	}
	
	@RequestMapping(value = "/board")
	public String BoardView(){	
		return "board/boardview";
	}
	
	@RequestMapping(value="/member/join", method = RequestMethod.GET)
	public String JoinView(){
		return "member/joinview";
	}
	
	@RequestMapping(value="/member/modify", method = RequestMethod.GET)
	public String ModifyView(){
		return "member/modifyview";
	}
	
	@RequestMapping(value="/board/create" , method = RequestMethod.GET)
	public String BoardCreateView(){
		return "board/boardcreateview";
	}
	@RequestMapping(value="/board/modify" , method = RequestMethod.GET)
	public String MemberModifyBoardView(){
		return "board/boardmodifyview";
	}
	
	@RequestMapping(value="/member/modifypassword" , method = RequestMethod.GET)
	public String MemberModifyPassView(){
		return "member/passmodifyview";
	}
	
	@RequestMapping(value="/member/modifynickname" , method = RequestMethod.GET)
	public String MemberModifyNickView(){
		return "member/nickmodifyview";
	}

	

}
