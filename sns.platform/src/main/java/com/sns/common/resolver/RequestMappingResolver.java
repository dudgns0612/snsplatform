/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.common.resolver;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.sns.biz.vo.CommandMap;
import com.sns.common.constant.NetworkConstants;

/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */

public class RequestMappingResolver implements HandlerMethodArgumentResolver{
	
	private static final Logger logger = LoggerFactory.getLogger(RequestMappingResolver.class);
	/**
	 * supportsParameter Resolver
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {	
		return CommandMap.class.isAssignableFrom(parameter.getParameterType());
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		CommandMap commandMap = new CommandMap();
		
		HttpSession session = request.getSession();
		HashMap<String, Object> member = (HashMap<String, Object>) session.getAttribute(NetworkConstants.MEMBER);
		if (member != null){
			commandMap.put("member", member);
		}
	
		String contentType = request.getContentType();
		
		if(contentType == null){
			contentType = "null";
		}		
		
		if(contentType.contains(NetworkConstants.TPYE_FORM_URLENCODED) || contentType.contains("null")){
			Enumeration<?> enumeration = request.getParameterNames();
			
			String key = null;
			String[] values = null;
			while(enumeration.hasMoreElements()){
				key = (String)enumeration.nextElement();
				values = request.getParameterValues(key);
					
				if(values != null){
					commandMap.put(key,(values.length > 1) ?  values : values[0]);
				}
			}
		}
		else if(contentType.contains(NetworkConstants.TYPE_MULTIPART)){
			MultipartHttpServletRequest multirequest = (MultipartHttpServletRequest)request;
			MultiValueMap<String, MultipartFile> multipartMap = multirequest.getMultiFileMap();
			
			Enumeration<?> enumeration = multirequest.getParameterNames();
			
			String key = null;
			String[] values = null;
			
			while(enumeration.hasMoreElements()){
				key = (String)enumeration.nextElement();
				values = request.getParameterValues(key);
			
				if(values != null){
					commandMap.put(key,(values.length > 1) ?  values : values[0]);
				}
			}
			commandMap.setMultipartMap(multipartMap);
			logger.info("==========userFile : "+multipartMap.containsKey("userFile")+"============");
			logger.info("==========files : "+multipartMap.containsKey("files")+"============");
		}
		
		
		
		
		return commandMap;
	}
}

