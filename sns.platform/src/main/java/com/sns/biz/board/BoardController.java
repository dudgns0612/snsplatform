/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
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
public class BoardController {
	/**
	 * 생성자
	 */
	public BoardController() {
		//Default Constructor
	}
	
	@Resource(name = "boardservice") private BoardService boardService;
	@Resource(name= "jsonView") private MappingJackson2JsonView jsonView;
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	/**
	 * 게시물 생성
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value ="board", method = RequestMethod.POST)
	public ModelAndView createBoard(CommandMap commandMap){	
		//멀티파트가 존재 할 경우 commandMap에 담아서 Service로 이동
		DataMap dataMap = new DataMap();
		if(commandMap.multiparContainsMap()){
			MultiValueMap<String, MultipartFile> multipartMap = commandMap.getMultipartMap();
			List<MultipartFile> multipartListMap = multipartMap.get("files");
			commandMap.put("multipartListMap", multipartListMap);
		}
		int check = boardService.createBoard(commandMap);
		
		//결과에 따라 code = 1 / message = 성공
		if(check == 1){
			dataMap.setCode(NetworkConstants.OK_CODE);
		}
		
		return new ModelAndView(jsonView,dataMap.getData());	
	}
	
	
	/**
	 * 게시물 상세
	 * @param boardNum
	 * @return
	 */
	@RequestMapping(value = "board/{boardNum}" , method = RequestMethod.GET)
	public ModelAndView detailBoard(@PathVariable("boardNum")String boardNum){
		//게시물번호를 통한 조건에 맞는 게시물 상세 data response
		int searchNum = NumberUtils.toInt(boardNum);
		DataMap dataMap = new DataMap();
		
		if(searchNum >0){
			Map<String, Object> infoMap = boardService.searchBoard(searchNum);
			if(infoMap != null){
				dataMap.put("data", infoMap);
				dataMap.setCode(NetworkConstants.OK_CODE);
			}
		}
			
		return new ModelAndView(jsonView,dataMap.getData());
	}
	
	/**
	 * 게시물 삭제
	 * @param boardNum
	 * @return
	 */
	@RequestMapping(value = "board/{boardNum}" , method = RequestMethod.DELETE)
	public ModelAndView boardDelete(@PathVariable("boardNum") String boardNum){
		 //게시물 번호를 통한 게시물 삭제
		 DataMap dataMap = new DataMap();
		 
		 int boardNumber = NumberUtils.toInt(String.valueOf(boardNum));
		 if(boardNumber > 0){
			 int check = boardService.removeBoard(boardNumber);
			 if(check > 0){
				 dataMap.setCode(NetworkConstants.OK_CODE);
			 }
		 }
		 
		 
		return new ModelAndView(jsonView,dataMap.getData());
	}
	
	/**
	 * 게시물 목록/검색 조회 
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value="boards", method = RequestMethod.GET)
	public ModelAndView boardList(CommandMap commandMap){
		//QueryString을 통하여 게시판의 조건에 따라 게시물 목록 response
		DataMap dataMap = new DataMap();
		
		//조건에 맞는 게시물이 없을경우 실패코드
		List<HashMap<String, Object>> infoListMap = boardService.listBoard(commandMap);
		if (infoListMap != null && !infoListMap.isEmpty()){
			dataMap.put("data", infoListMap);
			dataMap.setCode(NetworkConstants.OK_CODE);
		}
		
		return new ModelAndView(jsonView,dataMap.getData());
	}
	
	/**
	 * 게시물 수정
	 * @param boardNum
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value="board/{boardNum}" ,method = RequestMethod.POST)
	public ModelAndView boardModify(@PathVariable("boardNum") String boardNum,CommandMap commandMap){
		//게시물 번호와 form-data를 통하여 게시물 수정
		DataMap dataMap = new DataMap();
		
		int modifyNum = NumberUtils.toInt(boardNum);
		commandMap.put("boardNum", modifyNum);
		if(modifyNum > 0){
			if(commandMap.multiparContainsMap()){
				MultiValueMap<String, MultipartFile> multipartMap = commandMap.getMultipartMap();
				List<MultipartFile> multipartListMap = multipartMap.get("files");
				commandMap.put("multipartListMap", multipartListMap);
			}
		}
		
		if(boardService.modifyBoard(commandMap) == 1){
			dataMap.setCode(NetworkConstants.OK_CODE);
		}
		
		
		return new ModelAndView(jsonView,dataMap.getData()); 
	}



}
