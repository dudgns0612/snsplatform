package com.sns.biz.common;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sns.biz.vo.DataMap;

@ControllerAdvice
public class ExceptionhandlingController {
	
/*	private Logger logger = LoggerFactory.getLogger(ExceptionhandlingController.class);
	
	@ResponseBody
	@ExceptionHandler({SQLException.class , DataAccessException.class})
	public HashMap<String, Object> databaseError(HttpServletRequest req, Exception exception){
		DataMap excaptionMap = new DataMap();
		
		//Duplicated exception일 경우
		if(exception.getClass().toString().contains("Duplicate")){
			logger.info("################# ExceptionName : "+exception.getClass() +"Message : "+ exception.getMessage()+"#################");

			excaptionMap.put("exception", "duplicate");
			
			return excaptionMap.getData();
		}
		
		return excaptionMap;
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionError(Exception exception){
		return null;
	}*/
}
