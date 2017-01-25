package com.sns.common.view;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.AbstractView;

public class VideoStreamingView extends AbstractView {
	@Value("#{local['localpath']}")
	String localpath;
	static long stopPartSize = 0;
	
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		@SuppressWarnings("unchecked")
		Map<String,Object> fileMap = (Map<String, Object>) model.get("fileMap"); 
		
		String filePath = String.valueOf(fileMap.get("filePath"));
		//실제 파일이름 
		String orgFileName = String.valueOf(fileMap.get("fileName"));
		
		
		//progressbar에서 특정 위치를 클릭하여 볼 수 있으므로 RandomAccessFile을 사용
		RandomAccessFile randomFile = new RandomAccessFile(new File(localpath+filePath),"r");
		
		long videoSize = randomFile.length();
		long rangeStart = 0;
		long rangeEnd = videoSize-1;
		boolean isPart = false;
		
		String range = request.getHeader("renge");

		
		if (range != null) {
			// 처리의 편의를 위해 요청 range에 end 값이 없을 경우 넣어줌
			if (range.endsWith("-")) {
				range = range + (videoSize - 1);
			}
			int idxm = range.trim().indexOf("-"); // "-" 위치
			rangeStart = Long.parseLong(range.substring(6, idxm));
			rangeEnd = Long.parseLong(range.substring(idxm + 1));
			if (rangeStart > 0) {
				isPart = true;
			}
		} else { // range가 null인 경우 동영상 전체 크기로 초기값을 넣어줌. 0부터 시작하므로 -1
			rangeStart = 0;
			rangeEnd = videoSize - 1;
		}
		
		response.reset();
		
	    response.setStatus(isPart ? 206 : 200);

		
		response.setContentType("video/mp4");

		long partSize = rangeEnd - rangeStart + 1;
		
        //전송 내용을 헤드에 넣어준다. 마지막에 파일 전체 크기를 넣는다.
        response.setHeader("Content-Range", "bytes "+rangeStart+"-"+rangeEnd+"/"+videoSize);
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", ""+partSize);
		
        OutputStream out = response.getOutputStream();
        randomFile.seek(rangeStart);
        
        try{

			int bufferSize = 8 * 1024;
			byte[] buf = new byte[bufferSize];
			do {
				int block = partSize > bufferSize ? bufferSize : (int) partSize;
				int len = randomFile.read(buf, 0, block);
				out.write(buf, 0, len);
				partSize -= block;
				logger.info(partSize);
			} while (partSize > 0);
			logger.debug("sent " + videoSize + " " + rangeStart + "-" + rangeEnd);
		} catch (IOException e) {
			// 전송 중에 브라우저를 닫거나, 화면을 전환한 경우 종료해야 하므로 전송취소.
			// progressBar를 클릭한 경우에는 클릭한 위치값으로 재요청이 들어오므로 전송 취소.
			logger.debug("전송이 취소 되었음");
		} finally {
			stopPartSize = partSize;
			if(stopPartSize > 0){
				
			}
			
			randomFile.close();
		}

	}


}
