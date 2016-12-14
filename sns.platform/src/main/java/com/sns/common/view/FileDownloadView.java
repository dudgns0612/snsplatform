/**
 * (주) 오픈잇 | http://www.openit.co.kr
 * Copyright (c)2016-2016,  openit Inc.
 * All right reserved.
 */

package com.sns.common.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;
import com.sns.common.constant.NetworkConstants;
/**
 * url을 통하여 게시판 작업을 관리하는 컨트롤러
 * @author 사업본부 사원 김영훈
 * @version 0.1
 * @created 2016. 11. 15
 */
public class FileDownloadView extends AbstractView{
	@Value("#{local['localpath']}")
	private String localpath;
	public FileDownloadView() {
		setContentType("application/octet-stream; charset=utf-8");
	}
	
	
	/**
	 * 파일 다운로드뷰 model에 담아놓았던 fileMap을 통하여 파일 다운 및 보기
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		FileInputStream in = null;
		OutputStream out = null;

		
		HashMap<String, Object> fileMap = (HashMap<String, Object>) model.get("fileMap");
		if(fileMap != null){
			String filePath = String.valueOf(fileMap.get("filePath"));
			String orgFileName = URLEncoder.encode((String) fileMap.get("fileName"),"UTF-8"); 
			//request의 User-Agent정보를 가져옴 //Content-Disposition과 브라우저 정보를 확인하기 위해
			String userAgent = request.getHeader("User-Agent");
			String view = String.valueOf(fileMap.get("view"));
			String contentDisposition;
			//view가 image일경우 disposition을 inline으로 설정
			if (view.equals("image")){
				contentDisposition = "inline;";
				String extention = orgFileName.substring(orgFileName.lastIndexOf(".")+1);
				response.setContentType("image/"+extention);
			} else{
				contentDisposition = "attachment;";
				response.setContentType(getContentType());
			}
			File file = new File(localpath+filePath);
			response.setContentLength((int) file.length());
			//IE일 경우
			if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1) { 
				response.setHeader("Content-Disposition", contentDisposition+"filename=" + orgFileName.replaceAll("\\+", "\\") + "\"");
			//Chrome
			}else{
				response.setHeader("Content-Disposition", contentDisposition+"filename=\""+ new String(orgFileName.getBytes("UTF-8"),"ISO8859_1") + "\"");   
			}
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Pragma", "no-cache");
				
			try{
				out = response.getOutputStream();
				
				in = new FileInputStream(file);
				
				FileCopyUtils.copy(in, out);
				
			} catch (IOException e) {
				logger.info("=================IO에러 :"+e+"==================");
			} finally {
				if(in != null){
					try {
						in.close();
						out.flush();
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	
			}
			
		}
	}

}
