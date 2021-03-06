<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="../resources/css/boardcreateview.css" type="text/css">
<link rel="stylesheet" href="../resources/css/commonheader.css" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<jsp:include page="../common/commonheader.jsp" flush="false" />
<body>
	<div class="container ">
		<div class="board-container">
			<div class="board-create-form">
				<div class="board-create-title">
					<p>게시물 수정</p>
				</div>
		   		<form class="formdata">
	   				<div>
	   					<textarea id="board-content" class="input-control" name="boardContent"></textarea>
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
	   					  <button type="button" class="btn" id="boardmodify">게시물수정</button>
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
</body>
<script src="../resources/js/boardmodifyview.js" type="text/javascript"></script>
<script src="../resources/js/boardcreateview.js" type="text/javascript"></script>
<script src="../resources/js/bootstrap.min.js" type="text/javascript" /></script>
</html>