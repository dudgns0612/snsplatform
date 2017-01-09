package com.sns.biz.common;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.sns.biz.member.MemberDAO;

public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	//DAO설정
	@Resource MemberDAO memberDao;
	//property 사용
	@Value("#{local['localhost']}") private String localhost;
	@Value("#{local['port']}") private String port;
	
	Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String userId = authentication.getName();
		logger.info(">> Login Request UserId  :  "+ userId);
	
		HashMap<String,Object> userMap = memberDao.selectSecurityValue(userId);
		int userNum = NumberUtils.toInt(userMap.get("userNum").toString());
		
		String userFile = localhost+":"+port+"/user/"+userNum+"/image";
		
		userMap.put("userFile", userFile);
		
		
		//세션 주입
		HttpSession session = request.getSession(true);
		
		session.setAttribute("member",userMap);
		
		//성공했을 시 /board로 redirect
		response.sendRedirect("/board");
	}

}
