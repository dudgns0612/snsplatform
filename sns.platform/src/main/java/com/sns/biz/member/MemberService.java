/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.member;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sns.biz.vo.CommandMap;
import com.sns.common.constant.NetworkConstants;
import com.sns.common.util.UtilMethod;
/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
@Service("memberservice")
@Transactional
public class MemberService {
	@Value("#{local['userfilepath']}") private String userFile;
	@Value("#{local['localpath']}") private String localpath;
	@Value("#{local['localhost']}") private String localhost;
	@Value("#{local['port']}") private String port;
	@Value("#{local['separator']}") private String separator;
	@Resource
	MemberDAO memberDao;

	public MemberService() {
		//Default Constructor
	}

	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    /**
     * 회원가입
     * @param commandMap
     * @return
     */
	public int createMember(CommandMap commandMap) {
		int check = 0; 
		String filePath = null;
		Object profile = commandMap.get("userFile");
		if (profile != null && profile.getClass() != String.class) {
			filePath = profileUpload((MultipartFile)profile); 
			commandMap.put("userFile", filePath);
		} else {
			commandMap.put("userFile", "/default.jpg");
		}
		
		check = memberDao.insertMember(commandMap.getMap());
		
		
		logger.info("============FileUpload Path : " + filePath + "=================");

		return check;
	}
    
	/**
	 * 파일 존재시 파일업로드
	 * @param profile
	 * @return
	 */
	private String profileUpload(MultipartFile profile) {
		String filepath = null;
		String fileOriName = profile.getOriginalFilename();
		logger.info("==============OriginalFilename : " + profile.getOriginalFilename() + "=================");
		logger.info("==============FilenameType : " + profile.getContentType() + "=================");
		logger.info("==============FileSize : " + profile.getContentType() + "=================");
		String filedirectory= userFile;

		try {

			filepath = filedirectory + UUID.randomUUID() +"_"+fileOriName;

			File uploadfile = new File(filepath);

			profile.transferTo(uploadfile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String dbPath= filepath.split(separator+"user"+separator)[1];

		return separator+"user"+separator+dbPath;
	}

	public HashMap<String, Object> searchMember(int userNum) {
		logger.info("●●●●●●●●●●●●●●●● MemberSearch ●●●●●●●●●●●●●●●●");
		logger.info("=============== userNum : " + userNum + "=================");
		HashMap<String, Object> dataMap = memberDao.searchMember(userNum);
		if (dataMap != null){
			String imageUrl = localhost+":"+port+"/user/"+userNum+"/image";
			dataMap.put("imageUrl", imageUrl);
		}
		
		return dataMap;
	}

    
	/**
	 * 멤버 수정
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	public int modifyMember(CommandMap commandMap)throws Exception{
		logger.info("●●●●●●●●●●●●●●●● MemberModify ●●●●●●●●●●●●●●●●");
		MultipartFile profile = (MultipartFile) commandMap.get("profileFile");
		List<HashMap<String, Object>> oldImageList = new ArrayList<HashMap<String, Object>>();
		int check = 0;
		if (profile != null) {
			//유저프로필 변경 시 전에 사용하던 업로드 이미지 삭제
			HashMap<String, Object>memberMap = (HashMap<String, Object>) commandMap.get(NetworkConstants.MEMBER);
			memberMap.put("filePath", memberMap.get("userFile"));
			oldImageList.add(memberMap);
			//파일 업로드
			String userFile = profileUpload(profile);
			commandMap.put("userFile",userFile);
		} 
		
		if (memberDao.updateMember(commandMap.getMap()) == 1){
			HashMap<String,Object> memberMap = memberDao.searchMember((int) commandMap.get("userNum"));
			commandMap.put("member", memberMap);
			if(oldImageList != null){
				UtilMethod.fileDelete(oldImageList,localpath);
			}
			return NetworkConstants.OK_CODE;
		} 
		
		return check;
	}
    
	/**
	 * 멤버 삭제
	 * @param map
	 * @return
	 */
	public int removeMember(HashMap<String, Object> map) {
		logger.info("●●●●●●●●●●●●●●●● member Delete ●●●●●●●●●●●●●●●●");
		List<HashMap<String, Object>> oldImageList = new ArrayList<HashMap<String, Object>>();
		map.put("filePath", map.get("userFile"));
		oldImageList.add(map);
		int check = memberDao.deleteMember(map);
		if(check > 0){
			UtilMethod.fileDelete(oldImageList,localpath);
		}
		
		return check;
	}
    
	/**
	 * 아이디 중복체크
	 * @param id
	 * @return
	 */
	public String overlapId(String id) {
		logger.info("●●●●●●●●●●●●●●●● overlap Id ●●●●●●●●●●●●●●●●");
		logger.info("=========== ID : " + id + "=============");
		String userId = memberDao.idOverlap(id);
		return userId;
	}
	
    /**
     * 닉네임중복체크
     * @param nickName
     * @return
     */
	public String overlapNickname(String nickName) {
		logger.info("●●●●●●●●●●●●●●●● overlap Nickname ●●●●●●●●●●●●●●●●");
		logger.info("=========== NickName : " + nickName + "=============");
		String userNick  = memberDao.nickNameOverlap((String) nickName);	
		return userNick;
	}
    /**
     * 패스워드 체크
     * @param member
     * @return
     */
	public int passwordCheck(HashMap<String,Object> member) {
		logger.info("●●●●●●●●●●●●●●●● check Password ●●●●●●●●●●●●●●●●");
		logger.info("============Member Password Check================");
	
		String userPass = memberDao.passwordCheck(member);
		if(userPass != null){
			return 1;
		}

		return 0;
	}
    
	/**
	 * 멤버 로그인
	 * @param commandMap
	 * @return
	 */
	public HashMap<String, Object> memberLogin(CommandMap commandMap) {
		logger.info("●●●●●●●●●●●●●●●● Member Login ●●●●●●●●●●●●●●●●");
		HashMap<String,Object> dataMap = new HashMap<String,Object>();
		
		if(memberDao.pnsTokenUpdate(commandMap.getMap()) == 1){
			dataMap = memberDao.loginMember(commandMap.getMap());
		}
		
		return dataMap;
	}

}
