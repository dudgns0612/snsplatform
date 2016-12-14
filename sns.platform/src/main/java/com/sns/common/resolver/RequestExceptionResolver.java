package com.sns.common.resolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.sns.biz.vo.DataMap;

public class RequestExceptionResolver extends SimpleMappingExceptionResolver{
	
	Logger logger = LoggerFactory.getLogger(RequestExceptionResolver.class);	
	
	@Resource(name="jsonView") MappingJackson2JsonView jsonView;
	
	
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		DataMap excaptionMap = new DataMap();
		
		logger.info("Exception Message : "+ex.getMessage());
		
		if(ex.toString().contains("Duplicate")){
			excaptionMap.put("exception", "duplicate");
		}
	
		
		return new ModelAndView(jsonView,excaptionMap.getData());	
	}
	

	
}
