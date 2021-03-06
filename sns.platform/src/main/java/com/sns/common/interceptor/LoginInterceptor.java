/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.common.interceptor;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sns.biz.common.CustomUserDetailService;
import com.sns.common.constant.NetworkConstants;
/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	public LoginInterceptor() {}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		
		HttpSession session = request.getSession();
		
		HashMap<String, Object> member =  (HashMap<String, Object>) request.getAttribute(NetworkConstants.MEMBER);
		
		
		logger.info("===============================!!!!!!!!!!!!!!!!!!!!!!!!!!!########################");
		
		if (member != null) {
			session.setAttribute("member", member);
			logger.info("==============Login Interceptor Session In================");
			logger.info("=============== Login Infomation ==================");
			logger.info("=============== USERID = "+member.get("userId") +"===================");
			logger.info("=============== USERID = "+member.get("userPass") +"===================");
			logger.info("=============== USERPATH = "+member.get("userFile") +"===================");
		}
		
		
		
	}
		
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
			return true;
		
	}
}
	

