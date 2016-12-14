/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.file;


import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
@Repository
public class FileDAO {
	
	//sql을 실행하기 위한 메소드들 가지고 있는 객체
	@Resource SqlSession sqlSession;
	
	public static final String NAMESPACE = "FileMapper";
	
	/**
	 * 생성자
	 */
	public FileDAO() {
		//Default Constructor
	}
	
	/**
	 * 파일에 대한 정보를 DB에 INSERT
	 * @param map
	 * @return
	 */
	public int insertFile(HashMap<String,Object> map){
		System.out.println(map.toString());
		return sqlSession.insert(NAMESPACE+".insertFile",map);
	}
	
	/**
	 * 삭제될 파일 넘버를 가지고 있는 String[]을 통하여 파일 DELETE
	 * @param fileNum
	 * @return
	 */
	public int deleteFile(HashMap<String, Object> fileNum){
		return sqlSession.delete(NAMESPACE+".deleteFile",fileNum);
	}
	
	/**
	 * 삭제되는 파일의 CNT갯수를 반환
	 * @param fileNum
	 * @return
	 */
	public List<HashMap<String,Object>> selectDelFileInfo(HashMap<String, Object> map){
		return sqlSession.selectList(NAMESPACE+".selectDelFileInfo",map);
	}
	
	/**
	 * 유저의 첫번째 이미지를 가져옴
	 * @param map
	 * @return
	 */
	public String selectFristImage(HashMap<String,Object> map){
		return sqlSession.selectOne(NAMESPACE+".selectFristImage", map);
	}
	
	/**
	 * 파일패스와 게시물번호를 통하여 업로드된 게시물의 파일번호를 가져옴
	 * @param map
	 * @return
	 */
	public String selectImageNum(HashMap<String,Object> map){
		return sqlSession.selectOne(NAMESPACE+".selectImageNum", map);
	}
	
	/**
	 * 대표 게시물 사진이 삭제되었고 업로드된 파일도 없을 시 기존에 남아있던 사진의 정보를 선택하여 반환
	 * @param boardNum
	 * @return
	 */
	public HashMap<String,Object> selectBoardFile(int boardNum){
		List<HashMap<String, Object>> listMap = sqlSession.selectList(NAMESPACE+".selectBoardFile",boardNum);
		for(HashMap<String,Object> map : listMap){
			if(map.get("fileType").toString().contains("image")){
				return map;
			} 
		}

		return null;
	}
	
	/**
	 * 파일 다운로드를 위하여 조건에 맞는 정보를 반환
	 * @param map
	 * @return
	 */
	public HashMap<String, Object> selectFileInfo(HashMap<String, Object> map){
		return sqlSession.selectOne(NAMESPACE+".selectFileInfo",  map);
	}
	
	/**
	 * 조건에 맞는 파일리스트 정보를 반환
	 * @param boardNum
	 * @return
	 */
	public List<HashMap<String, Object>> selectFileList(int boardNum){
		return sqlSession.selectList(NAMESPACE+".selectFileList", boardNum);
	}
}
