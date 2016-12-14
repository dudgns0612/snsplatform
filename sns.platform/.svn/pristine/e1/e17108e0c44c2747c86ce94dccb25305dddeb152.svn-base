package com.sns.common.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import com.sns.biz.vo.CommandMap;
public class ControllerAroundAspect {
	Object response;
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerAroundAspect.class);
	
	public Object aroundAspect(ProceedingJoinPoint pjoinPoint) throws Throwable{
		
		String location = pjoinPoint.getSignature().getDeclaringTypeName();
		logger.info("LOCATION-START  :   ###################"+location+"###################");
		Object[] request =pjoinPoint.getArgs();
		for(Object requestVal : request){
			if(requestVal.getClass().equals(CommandMap.class)){
				CommandMap commandMap = (CommandMap)requestVal;
				HashMap<String, Object> requestMap = commandMap.getMap();
				for(String key : requestMap.keySet()){			
					if(key.equals("member")){
						HashMap<String, Object> memberMap= (HashMap<String, Object>) requestMap.get(key);
						for(String val : memberMap.keySet()){
							if(val.contains("userNick")){
								logger.info(String.format("REQ>>>>>>>>>>>KEY : %s ,>>>>>>>>>> VALUE : %s",val,memberMap.get(val)));
							}
						}
					}else{
						logger.info(String.format("REQ>>>>>>>>>>>KEY : %s ,>>>>>>>>>> VALUE : %s",key,requestMap.get(key)));
					}
				}
			}else{
				logger.info(String.format("REQ>>>>>>>>>>>"+requestVal.toString()));
			}
		}
			
		response = pjoinPoint.proceed();
		
		ModelAndView modelAndView = (ModelAndView)response;
		Map<String, Object> responseMap = modelAndView.getModel();
		for(String key : responseMap.keySet()){
			if(key.contains("fileMap")){
				Map<String,Object> fileMap = (Map<String, Object>) responseMap.get(key);
				for(String filekey : fileMap.keySet()){
					logger.info(String.format("RES>>>>>>>>>>>KEY : %s ,>>>>>>>>>> VALUE : %s",filekey,fileMap.get(filekey)));
				}
			}else if(key.contains("data")){
				if(responseMap.get(key).getClass().getCanonicalName().contains("ArrayList")){
					List<HashMap<String,Object>> dataList = (List<HashMap<String,Object>>) responseMap.get(key);
					if(dataList.size() > 0){
						for(int i = 0; i < dataList.size() ; i++){
							HashMap<String,Object> dataMap = dataList.get(i);
							logger.info("===============================================================");
							for(String dataKey : dataMap.keySet()){
								logger.info(String.format("["+i+"]RES>>>>>>>>>KEY : %s , >>>>>>>>>VALUE : %s",dataKey,dataMap.get(dataKey)));
							}
							logger.info("===============================================================");
						}						
					}
				}else{
					HashMap<String, Object> dataMap = (HashMap<String, Object>) responseMap.get(key);
					for(String dataKey : dataMap.keySet()){
						logger.info(String.format("RES>>>>>>>>>KEY : %s , >>>>>>>>>VALUE : %s",dataKey,dataMap.get(dataKey)));
					}
				}				
			} else{
				logger.info(String.format("RES>>>>>>>>>KEY : %s , >>>>>>>>>VALUE : %s",key,responseMap.get(key)));
			}
			
		}	
		
		return response;
	}
	
}
