/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.board;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
/**
 * DB와 연결하여 게시물 DB를 관리하기 위한 DAO클래스
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
@Repository
public class BoardDAO {

	//sql을 실행하기 위한 메소드들 가지고 있는 객체
	@Resource SqlSession sqlSession;

	private static final String NAMESPACE = "BoardMapper";
	
	/**
	 * 생성자
	 */
	public BoardDAO() {
		//Default Constructor
	}
	/**
	 * 멀티파일이 존재 할 경우 필요한 정보를 담은 MAP을 매개변수로  게시물 생성
	 * @param map
	 * @return
	 */
	public int insertFileBoard(HashMap<String, Object> map) {
		return sqlSession.insert(NAMESPACE + ".insertFileBoard", map);
	}
	
	/**
	 * 멀티파일이 존재하지 않을 경우 정보를 담은 MAP을 매개변수로 게시물 생성
	 * @param map
	 * @return
	 */
	public int boardInsert(HashMap<String, Object> map) {
		return sqlSession.insert(NAMESPACE + ".insertBoard", map);
	}
	
	/**
	 * 게시물 번호를 매개변수로 받아 게시물 정보를 반환
	 * @param boardNum
	 * @return
	 */
	public HashMap<String, Object> boardSelect(int boardNum) {
		return sqlSession.selectOne(NAMESPACE + ".selectBoard", boardNum);
	}

	/**
	 * 게시물 목록조회시 필요한 데이터를 받아 조건에 맞는 게시물리스트를 반환
	 * @param map
	 * @return
	 */
	public List<HashMap<String, Object>> selectBoardList(HashMap<String, Object> map) {
		return sqlSession.selectList(NAMESPACE + ".selectBoardList", map);

	}

	/**
	 * 게시물번호를 매개변수로 받아 게시물 수정시 업데이트에 필요한 게시물 정보를 반환
	 * @param boardNum
	 * @return
	 */
	public HashMap<String, Object> selectEditBoard(int boardNum) {
		return sqlSession.selectOne(NAMESPACE + ".selectEditBoard", boardNum);
	}
	
	/**
	 * 수정에 필요한 모든 데이터를 MAP으로 받아 게시물 수정
	 * @param map
	 * @return
	 */


	public int updateBoard(HashMap<String, Object> map) {
		return sqlSession.update(NAMESPACE + ".updateBoard", map);
	}
	
	/**
	 * 첫번째 이미지 정보를 수정
	 * @param map
	 * @return
	 */
	public int updatetBoardFirstNum(HashMap<String, Object> map) {
		return sqlSession.insert(NAMESPACE + ".updatetBoardFirstNum", map);
	}
	
	/**
	 * 게시물의 댓글 갯수를 증가
	 * @param boardNum
	 * @return
	 */
	public int updateAddReplyCnt(int boardNum) {
		return sqlSession.update(NAMESPACE + ".updateAddReplyCnt", boardNum);
	}
	
	/**
	 * 게시물의 댓글 갯수를 감소
	 * @param boardNum
	 * @return
	 */
	public int updateDelReplyCnt(int boardNum) {
		return sqlSession.update(NAMESPACE + ".updateDelReplyCnt", boardNum);
	}
	
	/**
	 * 게시물을 삭제하였을 시 모든 게시물 관련 데이터를 삭제
	 * @param boardNum
	 * @return
	 */
	public int deleteBoard(int boardNum){
		return sqlSession.delete(NAMESPACE+".deleteBoard",boardNum);
	}
	
	/**
	 * 업로드된 파일을 삭제하기 위한 리스트 반환
	 * @param boardNum
	 * @return
	 */
	public List<HashMap<String, Object>> deleteBoardFile(int boardNum){
		return sqlSession.selectList(NAMESPACE+".deleteBoardFile", boardNum);
	}
}
