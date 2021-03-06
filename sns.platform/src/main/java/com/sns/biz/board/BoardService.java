/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */
package com.sns.biz.board;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sns.biz.file.FileDAO;
import com.sns.biz.vo.CommandMap;
import com.sns.common.constant.NetworkConstants;
//import com.sns.common.push.service.PushService;
import com.sns.common.util.UtilMethod;

/**
 * Controller를 통하여 들어온 데이터를 통해 필요한 로직 수행
 * 
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
@Service("boardservice")
public class BoardService {
	@Value("#{local['userfilepath']}") private String userFile;
	@Value("#{local['boardfilepath']}") private String boardFile;
	@Value("#{local['localpath']}") private String localpath;
	@Value("#{local['localhost']}") private String localhost;
	@Value("#{local['port']}") private String port;
	@Value("#{local['separator']}") private String separator;

	@Resource
	private BoardDAO boardDao;
	@Resource
	private FileDAO fileDao;


	/**
	 * 생성자
	 */
	public BoardService() {
		// Default Constructor
	}

	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

	/**
	 * 게시물 생성서비스
	 * 
	 * @param commandMap
	 * @return
	 */
	public int createBoard(CommandMap commandMap) {
		logger.info("●●●●●●●●●●●●●●●● Board Create ●●●●●●●●●●●●●●●●");
		int check = 0;
		HashMap<String, Object> member = (HashMap<String, Object>) commandMap.get(NetworkConstants.MEMBER);
		int userNum = NumberUtils.toInt(String.valueOf(member.get("userNum")));
		int board = 0;
		commandMap.put("userNum", userNum);
		// 멀티파트맵을 통하여 파일을 업로드
		if (commandMap.containsKey("multipartListMap")) {
			List<MultipartFile> MultipartListMap = (List<MultipartFile>) commandMap.get("multipartListMap");
			ArrayList<HashMap<String, Object>> fileList = fileUpload(MultipartListMap, commandMap);
			check = boardDao.insertFileBoard(commandMap.getMap());
			board = NumberUtils.toInt(String.valueOf(commandMap.get("boardNum")));

			// DB에 성공적으로 저장 시 파일DB 저장
			if (check == 1) {
				for (HashMap<String, Object> map : fileList) {
					map.put("boardNum", board);

					check = fileDao.insertFile(map);

					if (check != 1) {
						return check;
					}
				}
				// firstImageNum 저장
				String fileNum = fileDao.selectImageNum(commandMap.getMap());
				commandMap.put("firstImageNum", fileNum);
				boardDao.updatetBoardFirstNum(commandMap.getMap());
			}
		} else {
			if (!commandMap.get("boardContent").equals("")) {
				check = boardDao.boardInsert(commandMap.getMap());
				board = NumberUtils.toInt(String.valueOf(commandMap.get("boardNum")));
			}
		}
//		try {
//			pushService.pushForWriteBoard(board);
//		} catch (Exception ex) {
//			logger.info("#############PUSH MESSAGE#############");
//		}

		return check;
	}

	/**
	 * 파일 업로드
	 * 
	 * @param fileList
	 * @param commandMap
	 * @return
	 */
	private ArrayList<HashMap<String, Object>> fileUpload(List<MultipartFile> fileList, CommandMap commandMap) {
		int firstmageCheck = 0;
		// 파일 업로드시 필요한 폴더 저장소를 생성
		String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filedirectory = boardFile + today;
		UtilMethod.directoryCreate(filedirectory);
		ArrayList<HashMap<String, Object>> boardFileList = new ArrayList<HashMap<String, Object>>();
		int image = 0;
		int audio = 0;
		int video = 0;

		for (MultipartFile file : fileList) {
			try {
				HashMap<String, Object> fileMap = new HashMap<String, Object>();
				String filePath = filedirectory + separator + UUID.randomUUID() + "_"
						+ file.getOriginalFilename();
				String dbFilePath = separator + "board" + separator
						+ filePath.split(separator + "board" + separator)[1];
				String fileType = file.getContentType();
				if (fileType.contains("image")) {
					image++;
					if (firstmageCheck == 0) {
						commandMap.getMap().put("boardFirstImg", dbFilePath);
					}
					firstmageCheck++;
				} else if (fileType.contains("audio")) {
					audio++;
				} else {
					video++;
				}
				// Map에 담아 DB정보로 사용
				fileMap.put("filePath", dbFilePath);
				fileMap.put("fileType", fileType);
				fileMap.put("fileName", file.getOriginalFilename());
				fileMap.put("fileSize", file.getSize() / 1024.0);

				boardFileList.add(fileMap);
				file.transferTo(new File(filePath));

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// file정보를 담은 리스트를 반환 // commandMap에 각 파일의 타입 갯수를 담음
		commandMap.getMap().put("boardImgCnt", image);
		commandMap.getMap().put("boardAudCnt", audio);
		commandMap.getMap().put("boardVidCnt", video);

		return boardFileList;
	}

	/**
	 * 게시물 상세
	 * 
	 * @param boardNum
	 * @return
	 */
	public Map<String, Object> searchBoard(int boardNum) {
		logger.info("●●●●●●●●●●●●●●●● Board detail ●●●●●●●●●●●●●●●●");
		HashMap<String, Object> infoMap = boardDao.boardSelect(boardNum);

		return infoMap;
	}

	/**
	 * 게시물 삭제
	 * 
	 * @param boardNum
	 * @return
	 */
	public int removeBoard(int boardNum) {
		logger.info("●●●●●●●●●●●●●●●● Board Delete ●●●●●●●●●●●●●●●●");
		int check = 0;

		List<HashMap<String, Object>> delfileList = boardDao.deleteBoardFile(boardNum);
		check = boardDao.deleteBoard(boardNum);
		if (check > 0) {
			UtilMethod.fileDelete(delfileList,localpath);
		}
		return check;
	}

	/**
	 * 게시물 목록 검색 조회
	 * 
	 * @param commandMap
	 * @return
	 */
	public List<HashMap<String, Object>> listBoard(CommandMap commandMap) {
		logger.info("●●●●●●●●●●●●●●●● Board List ●●●●●●●●●●●●●●●●");
		int lastBoardNum = -1;
		int pageNum = NumberUtils.toInt(String.valueOf(commandMap.get("pageNum")));
		if (pageNum == 0) {
			pageNum = 5;
		}
		if (commandMap.containsKey("lastBoardNum")) {
			lastBoardNum = NumberUtils.toInt(String.valueOf(commandMap.get("lastBoardNum")));
		}
		commandMap.put("pageNum", pageNum);
		commandMap.put("lastBoardNum", lastBoardNum);
		logger.info("●●●●●●●●●●●●●●●● pageNum : " + pageNum + "●●●●●●●●●●●●●●●●");
		logger.info("●●●●●●●●●●●●●●●● lastBoardNum : " + lastBoardNum + "●●●●●●●●●●●●●●●●");
		// SearchType이 date일경우 하루동안 작성된 게시물을 조회 하기 위하여 format
		if (commandMap.containsKey("searchType")) {
			if (commandMap.get("searchType").toString().contains("date")) {
				String keyWord = String.valueOf(commandMap.get("keyWord"));
				String startDate = keyWord + "000000";
				String endDate = keyWord + "235959";
				commandMap.put("startDate", startDate);
				commandMap.put("endDate", endDate);
			}
		}
		// 파일url로 변환하여 리스트에 수록
		List<HashMap<String, Object>> infoListMap = boardDao.selectBoardList(commandMap.getMap());
		for (HashMap<String, Object> map : infoListMap) {
			int boardNum = NumberUtils.toInt(String.valueOf(map.get("boardNum")));
			int fileNum = NumberUtils.toInt(String.valueOf(map.get("boardImgNum")));
			if (fileNum > 0) {
				String firstImageUrl = localhost + ":" + port + "/board/" + boardNum
						+ "/file/" + fileNum;
				map.put("firstImageUrl", firstImageUrl);
			}
		}
		return infoListMap;
	}

	/**
	 * 게시물 수정
	 * 
	 * @param commandMap
	 * @return
	 */
	public int modifyBoard(CommandMap commandMap) {
		logger.info("●●●●●●●●●●●●●●●● Board Edit ●●●●●●●●●●●●●●●●");
		logger.info("●●●●●●●●●●●●●●●● delFiles ●●●●●●●●●●●●●●●●");
		int check = 0;// 쓰일때
		int image = 0;
		int audio = 0;
		int video = 0;
		int boardNum = NumberUtils.toInt(String.valueOf(commandMap.get("boardNum")));
		List<HashMap<String, Object>> delFileList = null;
		String delfiles[] = (String[]) commandMap.get("delFiles");
		// 파일 업로드
		if (commandMap.containsKey("multipartListMap")) {
			List<MultipartFile> MultipartListMap = (List<MultipartFile>) commandMap.get("multipartListMap");
			ArrayList<HashMap<String, Object>> fileList = fileUpload(MultipartListMap, commandMap);

			// 추가된 파일DB추가
			for (HashMap<String, Object> map : fileList) {
				// 참조에 필요한 게시물 번호 추가
				map.put("boardNum", boardNum);
				check = fileDao.insertFile(map);
			}
		}

		// 현재 게시물의 각 파일의 갯수와 추가된 파일의 갯수 삭제된 파일의 갯수를 통합
		// 현재 게시물의 cnt와 첫번째 이미지를 가져옴
		HashMap<String, Object> boardOrignalMap = boardDao.selectEditBoard(boardNum); // 지우고
																						// 추가된것과
																						// 삭제된것만
																						// 가지고
																						// 업데이트시
																						// 더함
		if (boardOrignalMap != null) {
			commandMap.put("boardFirstImgNum", boardOrignalMap.get("boardFirstImgNum"));
			image += NumberUtils.toInt(String.valueOf(boardOrignalMap.get("boardImgCnt")));
			audio += NumberUtils.toInt(String.valueOf(boardOrignalMap.get("boardAudCnt")));
			video += NumberUtils.toInt(String.valueOf(boardOrignalMap.get("boardVidCnt")));
			image += NumberUtils.toInt(String.valueOf(commandMap.get("boardImgCnt")));
			audio += NumberUtils.toInt(String.valueOf(commandMap.get("boardAudCnt")));
			video += NumberUtils.toInt(String.valueOf(commandMap.get("boardVidCnt")));
			if (delfiles != null) {
				HashMap<String, Object> delFileMap = new HashMap<String, Object>();
				delFileMap.put("delFiles", delfiles);
				delFileList = fileDao.selectDelFileInfo(delFileMap);
				if (delFileList != null) {
					for (HashMap<String, Object> delfile : delFileList) {
						String fileType = String.valueOf(delfile.get("fileType"));
						System.out.println("FileType : " + fileType);
						if (fileType != null) {
							if (fileType.contains("image")) {
								image--;
							} else if (fileType.contains("audio")) {
								audio--;
							} else {
								video--;
							}
						}
					}

				}
				// 삭제된 파일의 DB데이터 삭제
				fileDao.deleteFile(commandMap.getMap());
			}
			// 가져온 첫번째 이미지가 있는지 비교
			String fristImage = fileDao.selectFristImage(commandMap.getMap());
			System.out.println(fristImage);
			commandMap.put("boardImgCnt", image);
			commandMap.put("boardAudCnt", audio);
			commandMap.put("boardVidCnt", video);
			if (fristImage == null) {
				// 다음 이미지가 존재할 경우 그 파일의 번호를 가져옴
				HashMap<String, Object> map = fileDao.selectBoardFile(boardNum);
				// 이미지를 맵에 넣어 UPDATE문을 실행 map이 null일경우 firstImageNum = null
				if (map != null) {
					commandMap.put("firstImageNum", map.get("firstImageNum"));
				}
				check = boardDao.updateBoard(commandMap.getMap());
			} else {
				commandMap.put("firstImageNum", fristImage);
				check = boardDao.updateBoard(commandMap.getMap());
			}

			if (delFileList != null) {
				UtilMethod.fileDelete(delFileList,localpath);
			}
		}
		return check;
	}

}
