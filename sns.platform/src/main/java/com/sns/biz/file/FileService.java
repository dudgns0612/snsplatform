/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.file;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sns.biz.member.MemberDAO;
import com.sns.biz.vo.CommandMap;

/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
@Service("fileservice")
public class FileService {
	@Value("#{local['localhost']}") private String localhost;
	@Value("#{local['port']}") private String port;
	
	@Resource FileDAO fileDao;
	@Resource MemberDAO memberDao; 
	private static final Logger logger = LoggerFactory.getLogger(FileService.class); 
	
	public FileService() {
		//Default Constructor
	}
	
	public HashMap<String,Object> fileMemberImageDown(int userNum)
	{
		logger.info("●●●●●●●●●●●●●●●● MemberImageDown ●●●●●●●●●●●●●●●●");
		logger.info("userNum : " + userNum);
		HashMap<String,Object> userFileMap = null;
		String userPath = memberDao.selectImgPath(userNum);
		if(userPath != null){
			String fileName = userPath.substring(userPath.lastIndexOf("_")+1,userPath.length());
			userFileMap = new HashMap<String,Object>();
			userFileMap.put("filePath", userPath);
			userFileMap.put("fileName", fileName);
			userFileMap.put("view", "image");
		}
		
		
		return userFileMap;
		
	}
	
	
	
	/**
	 * 파일 다운로드에 필요한 파일정보를 전달
	 * @param boardNum
	 * @param fileNum
	 * @param response
	 */
	public HashMap<String, Object> fileDown(CommandMap commandMap)
	{
		logger.info("●●●●●●●●●●●●●●●● File Download ●●●●●●●●●●●●●●●●");
		
		HashMap<String, Object> fileMap= fileDao.selectFileInfo(commandMap.getMap());
		
		return fileMap;
	}

	/**
	 * 게시물번호를 이용하여 그에 맞는 파일리스트를 전달
	 * @param boardNum
	 * @return
	 */
	public List<HashMap<String,Object>> fileList(int boardNum)
	{
		logger.info("●●●●●●●●●●●●●●●● File List ●●●●●●●●●●●●●●●●");
		List<HashMap<String, Object>> fileList = fileDao.selectFileList(boardNum);
		
		if(fileList != null){
			for(HashMap<String,Object> map : fileList){
				String fileNumber = String.valueOf(map.get("fileNum"));
				String boardNumber = String.valueOf(map.get("boardNum"));
				//URL을 파싱하여 fileURI전달
				String fileUrl = localhost+":"+port+"/board/"+boardNumber+"/file/"+fileNumber;
				map.put("fileUrl", fileUrl);
				map.remove("boardNum");
			}
		}
	
		return fileList;
	}
	


}
