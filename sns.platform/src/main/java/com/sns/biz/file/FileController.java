/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.file;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.sns.biz.vo.CommandMap;
import com.sns.biz.vo.DataMap;
import com.sns.common.constant.NetworkConstants;
import com.sns.common.view.FileDownloadView;

/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
@Controller
public class FileController {
	
	
	@Resource(name="fileservice") private FileService fileService;
	@Resource(name="fileDownloadView") private FileDownloadView fileDownloadView;
	@Resource(name="jsonView") private MappingJackson2JsonView jsonView;
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class); 
	
	/**
	 * 생성자
	 */
	public FileController() {
		//Default Constructor
	}
	
	/**
	 * 유저의 프로필 사진을 다운
	 * @param userNum
	 * @return
	 */
	@RequestMapping(value="user/{userNum}/image" , method = RequestMethod.GET)
	public ModelAndView MemberProfile(@PathVariable("userNum") String userNum){
		//유저 번호를 통하여 유저에 맞는 DB작업으로 유저의 파일경로 가져옴
		HashMap<String, Object> userFileMap = null;
		int userNumber = NumberUtils.toInt(userNum);
		if (userNumber > 0){
			userFileMap = fileService.fileMemberImageDown(userNumber);
		}

		//파일다운로드 뷰를 사용하여 브라우저에 다운로드
		return new ModelAndView(fileDownloadView,"fileMap",userFileMap);
	}
	
	
	/**
	 * 게시물번호를 통한 게시물 관련 파일리스트
	 * @param boardNum
	 * @return
	 */
	@RequestMapping(value="board/{boardNum}/files", method = RequestMethod.GET)
	public ModelAndView FileList(@PathVariable("boardNum")String boardNum,CommandMap commandMap)
	{	
		//게시물번호를 통하여 DB에서 관련 데이터 가져옴
		DataMap dataMap = new DataMap();
		int boardNumber = NumberUtils.toInt(String.valueOf(boardNum));
		
	
		if(boardNumber > 0){
			List<HashMap<String,Object>> fileList = fileService.fileList(boardNumber);
			if (fileList != null){
				dataMap.put("data", fileList);
				dataMap.setCode(NetworkConstants.OK_CODE);
			}
		}

		return new ModelAndView(jsonView, dataMap.getData());
	}

	/**
	 * 파일번호와 게시물번호를 통하여 관련 파일 다운로드
	 * @param boardNum
	 * @param fileNum
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value="board/{boardNum}/file/{fileNum}", method = RequestMethod.GET)
	public ModelAndView fileDown(@PathVariable("boardNum")String boardNum,@PathVariable("fileNum")String fileNum
			,CommandMap commandMap)
	{	
		HashMap<String, Object> fileMap;
		int boardNumber = NumberUtils.toInt(boardNum);
		int fileNumber = NumberUtils.toInt(fileNum);
		commandMap.put("boardNum", boardNumber);
		commandMap.put("fileNum", fileNumber);
		String view = String.valueOf(commandMap.get("view"));
		//파일 down/image를 default로 image로 대체
		if (view.equals("null")){
			view = "image";
		}
		fileMap = fileService.fileDown(commandMap);
		if(fileMap == null){
			return null;
		}
		fileMap.put("view", view);
		
		//파일다운로드 뷰를 통하여 조건에 맞는 다운로드실행
		return new ModelAndView(fileDownloadView,"fileMap",fileMap);
	}
	
}

