<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="../resources/css/commonheader.css" type="text/css">
<link rel="stylesheet" href="../resources/css/boardcreateview.css" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<title>Insert title here</title>

</head>
<jsp:include page="../common/commonheader.jsp" flush="false" />
<body>
	
	 <div class="container">
	 	<div class="board-container">
			<div class="board-create-form">
				<div class="board-create-title">
					<p>게시물 작성</p>
				</div>
		   		<form class="formdata">
	   				<div>
	   					<textarea id="board-content" class="input-control" name="boardContent" placeholder="내용을 입력하세요." autofocus ></textarea>
	   				</div> 
	   				<div class="fileListTitle">
	   					File Upload List
	   				</div>
	   				<div class="filebox" id="fileupload"> <label for="input-file">파일 업로드</label>
	   					<input type="file" multiple="multiple"  name="files[]" class="fileupload" id="input-file" accept=".jpg, .png, .avi, .mp3, .mp4, .gif, .wmv" >
	   				</div>
	   				<div>
	   					<button type="button" class="filecanclebtn" >파일전부삭제 </button>
	   				</div>
	   				<div class="boardbtn">
	   					  <button type="button" class="btn" id="boardcreate">게시물등록</button>
	   					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                  <button type="button" class="btn" id="boardcancle">취 &nbsp;&nbsp;&nbsp;소 </button>
	   				</div>
	   				<div class="file-upload-list" >
	   				</div>
		   	 	</form>
	  		</div>  
  		</div> 
  	</div>
</body>
<script src="../resources/js/boardcreateview.js" type="text/javascript"></script>
<script src="../resources/js/bootstrap.min.js" type="text/javascript" /></script>
</html>