/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.member;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.sns.biz.common.CustomUserDetailService;
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
public class MemberController {
	@Value("#{local['userfilepath']}") private String userFile;
	@Value("#{local['localhost']}") private String localhost;
	@Value("#{local['port']}") private String port;
	
	@Resource(name="memberservice") private MemberService memberService;
	@Resource(name="jsonView") private MappingJackson2JsonView jsonView;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	/**
	 * 생성자
	 */
	public MemberController() {
		//Default Constructor
	}
	
	/**
	 * 유저 회원가입
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value="user", method= RequestMethod.POST )
	public ModelAndView memberJoin(CommandMap commandMap) {	
		logger.info("=============Join Controller=============");
		MultiValueMap<String, MultipartFile> multipartMap = commandMap.getMultipartMap();
		DataMap dataMap = new DataMap();
		if(multipartMap != null){
			if (multipartMap.containsKey("userFile")) {
				MultipartFile userFile = multipartMap.get("userFile").get(0);
				commandMap.put("userFile",userFile);
			}
		}
		int check = 0;
		if (commandMap.get("userId") != null && commandMap.get("userPass") != null && commandMap.get("userNick") != null 
				&& commandMap.get("userType") != null  && !commandMap.get("userId").equals("") 
				&& !commandMap.get("userPass").equals("") && !commandMap.get("userType").equals("") && !commandMap.get("userNick").equals("")) {
			if(commandMap.get("userType").equals("user") || commandMap.get("userType").equals("sns"))
				check = memberService.createMember(commandMap);
				dataMap.setCode(check);
		}	
		return new ModelAndView(jsonView , dataMap.getData());
	}
	
	/**
	 * 유저 검색
	 * @param userNum
	 * @return
	 */
	@RequestMapping(value = "user/{userNum}", method = RequestMethod.GET)
	public ModelAndView memberSearch(@PathVariable("userNum")String userNum)
	{	
		int searchNum = NumberUtils.toInt(userNum);
		DataMap dataMap = new DataMap();
		
		if(searchNum > 0){
	         HashMap<String, Object> infoMap = memberService.searchMember(searchNum);
			 if (infoMap != null){
				 dataMap.put("data", infoMap);
				 dataMap.setCode(NetworkConstants.OK_CODE);
			 }
		}
		return new ModelAndView(jsonView,dataMap.getData());
	}
	
  /**
   * 유저 정보 수정
   * @param commandMap
   * @param request
   * @return
   */
	@RequestMapping(value = "/user/modify", method = RequestMethod.POST)
	public ModelAndView memberModify(CommandMap commandMap,HttpServletRequest request){
		MultiValueMap<String, MultipartFile> multipartMap = commandMap.getMultipartMap();
		DataMap dataMap = new DataMap();
	
		HashMap<String, Object> memberMap = (HashMap<String,Object>)commandMap.get("member");
		int userNum = NumberUtils.toInt((String.valueOf(memberMap.get("userNum"))));	
		if(multipartMap != null){
			if (multipartMap.containsKey("userFile")){
				MultipartFile userFile = multipartMap.get("userFile").get(0);
				commandMap.put("profileFile",userFile);
			}
		}
		commandMap.put("userNum", userNum);
		try {
			if((commandMap.get("userPass") != null && !commandMap.get("userPass").equals("")) || 
					(commandMap.get("userNick") != null && !commandMap.get("userNick").equals(""))){
				if(memberService.modifyMember(commandMap)==1){
					HttpSession session = request.getSession();
					session.setAttribute("member", commandMap.get("member"));
					dataMap.setCode(NetworkConstants.OK_CODE);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView(jsonView,dataMap.getData());
	}
	
	/**
	 * 유저 삭제
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="user" ,method = RequestMethod.DELETE)
	public ModelAndView memberDelete(HttpServletRequest request)
	{
		DataMap dataMap = new DataMap();
		HttpSession session = request.getSession();
		HashMap<String,Object> memberMap =(HashMap<String, Object>) session.getAttribute(NetworkConstants.MEMBER);
		int check = memberService.removeMember(memberMap);
		if(check > 0){
			dataMap.setCode(NetworkConstants.OK_CODE);
		}

		return new ModelAndView(jsonView,dataMap.getData());
	}

	/**
	 * 아이디 중복확인
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "user/checkid/{userid}" , method = RequestMethod.GET)
	public ModelAndView overLapIdCheck(@PathVariable("userid") String id)
	{
		logger.info("id : "+ id );
		DataMap dataMap = new DataMap();
		String userId = memberService.overlapId(id);
		if(userId == null){
			dataMap.setCode(NetworkConstants.OK_CODE);
		}
		
		return new ModelAndView(jsonView,dataMap.getData());
	}
	
	/**
	 * 유저 닉네임 확인
	 * @param nickName
	 * @return
	 */
	@RequestMapping(value = "/user/checkname/{userNick}" , method = RequestMethod.GET)
	public ModelAndView overLapNickCheck(@PathVariable("userNick") String nickName){
		logger.info("nickname : "+ nickName);
		DataMap dataMap = new DataMap();
		
		String userNick = memberService.overlapNickname(nickName);
		if(userNick == null){
			dataMap.setCode(NetworkConstants.OK_CODE);
		}
		
		return new ModelAndView(jsonView,dataMap.getData());
	}

	/**
	 * 패스워드 확인
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "user/checkpass" , method = RequestMethod.POST)
	public ModelAndView userPasswordCheck(CommandMap commandMap){
		DataMap dataMap = new DataMap();
		HashMap<String, Object> member = (HashMap<String, Object>) commandMap.get("member");
		HashMap<String,Object> checkInfo = new HashMap<String,Object>();
		checkInfo.put("userId", member.get("userId"));
		checkInfo.put("userPass", commandMap.get("userPass"));
	
		if (memberService.passwordCheck(checkInfo)==1){
			dataMap.setCode(NetworkConstants.OK_CODE);
		}
		
		return new ModelAndView(jsonView,dataMap.getData());
	}
	
	/**
	 * 유저 로그인
	 * @param commandMap
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "login" , method = RequestMethod.POST)
	public ModelAndView userLogin(CommandMap commandMap,HttpServletRequest request){
		HashMap<String,Object> responseMap = new HashMap<String,Object>();
		DataMap dataMap = new DataMap();
		if ((commandMap.get("userId") != null && commandMap.get("userPass") != null)){
			responseMap = memberService.memberLogin(commandMap);
			if (responseMap != null && responseMap.size() > 0) {
				String userFile = localhost+":"+port+"/user/"+responseMap.get("userNum")+"/image";
				responseMap.put("userFile", userFile);
				dataMap.put("data", responseMap);
				dataMap.setCode(NetworkConstants.OK_CODE);
				//세션인터셉터에서 사용할 정보를 전달
				request.setAttribute(NetworkConstants.MEMBER, responseMap);
			}
		}
		return new ModelAndView(jsonView,dataMap.getData());
	}
	
	
	/**
	 * 유저 로그아웃
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public ModelAndView userLogout(HttpServletRequest request){
		DataMap dataMap = new DataMap();
		HttpSession session = request.getSession();
		
		if (session.getAttribute(NetworkConstants.MEMBER) != null) {
			logger.info("=================Logout Session Remove====================");
			session.removeAttribute(NetworkConstants.MEMBER);
			dataMap.setCode(NetworkConstants.OK_CODE);
		} 
			
		return new ModelAndView(jsonView , dataMap.getData());
	}
	
	
	
	
}
