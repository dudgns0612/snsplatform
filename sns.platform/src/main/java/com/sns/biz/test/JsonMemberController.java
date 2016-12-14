package com.sns.biz.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class JsonMemberController {

	
	
	@Test
	public void testCreate(){
		System.out.println("Exception");
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="test", method= RequestMethod.POST)
	public void memberJoin(@RequestBody Map<String,Object> value){
		testCreate();
		System.out.println("RequestBody"+value.getClass() + "//" + value.toString());
		HashMap<String, Object> map = (HashMap<String, Object>)value;
		for(String key : map.keySet()){
			System.out.println("KEY :"+key+"// VALUE : "+ map.get(key));
		}
	}
	
	@RequestMapping(value="test", method= RequestMethod.GET)
	public void requestParam(@RequestParam Map<String,Object> map, @RequestParam("userId") String id){
		System.out.println("RequestParam"+map.getClass() + "//" + map.toString());
		System.out.println("userId = "+id); //변수명으로 가져옴
		for(String key : map.keySet()){ // 변수명 선언언하지 않고 맵으로 선언하면 맵으로 모든 파라미터들을 받을수 있음
			System.out.println("KEY :"+key+"// VALUE : "+ map.get(key));
		}
	}
	
	@RequestMapping(value="exception", method= RequestMethod.POST)
	public void exception(@RequestParam("userId") String userId){
		System.out.println(userId);
		if(userId.equals("1")){
			throw new ValueNotFoundException();
		}
	}
	//404일경우 
	@ResponseStatus(value=HttpStatus.NOT_FOUND , reason="No such Order")
	class ValueNotFoundException extends RuntimeException{
		public ValueNotFoundException() {
			System.out.println("HTTP 상태코드이용");
		}
	}
	
	
	
/*	@RequestMapping(value="test", method= RequestMethod.POST)
	public void requestParam(HttpServletRequest request){

		System.out.println(request.getParameter("userId"));
	}*/
	
	//@ModelAttribute 
	/*@RequestMapping(value="test", method= RequestMethod.POST)
	public void requestParam(@RequestParam Article article){
		System.out.println(article.toString());
		System.out.println(article.getFile().getOriginalFilename());
	}*/
		
	
}
