<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="../resources/css/membermodifyview.css" type="text/css" />
<link rel="stylesheet" href="../resources/css/commonheader.css" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="../resources/js/bootstrap.min.js" type="text/javascript" /></script>
<title>Insert title here</title>
</head>
<jsp:include page="../common/commonheader.jsp" flush="false" />
<body>
 	<div class="container">
		<div class="modify-container">
			<div class="modify-form">
		         <form>
		         	 <table class= "modify-table">
			         	 <tr>
			         	 	 <td colspan="2" class="join-title" >나의정보</td>
			         	 </tr>
			         	 <tr id="image-tr">
			         	 	 <td colspan="2">
			         	 	 	<img src="" style="width: 150px; height: 150px;" class="img-rounded">
			         	 	 </td>
			         	 </tr>
			         	 <tr>
			         	 	<td class="filebox" colspan="2"> <label for="input-file">파일선택</label> 
		  					<input type="file" id="input-file" class="upload-hidden" accept=".jpg, .png, .avi, .mp3, .mp4, .gif, .wmv"><br/>
			         	 </tr>
			             <tr>
			             	 <td>ID : </td>
			             	 <td><input type="text" id="user-id" class="input-control" name="userId" disabled="disabled" value="${member.userId}" /></td>
			             </tr>
			             <tr>
			                <td> PASSWORD : </td>
			                <td><input type="password" class="input-control overlap" name="PasswordCheck" disabled="disabled" value="${member.userPass}" />
	 						<button type="button" id="id-overlap-btn" class="overlap-btn passwordEdit"> 수&nbsp;&nbsp;정 </button></td>
			             </tr>
			              <tr>
			             	 <td>NICKNAME :</td>
			             	 <td><input type="text" id="user-nick"  class="input-control overlap" name="userNick" disabled="disabled" value="${member.userNick}"/>
			             	 <button type="button" id="nick-overlap-ntn" class="overlap-btn nicknameedit"> 수&nbsp;&nbsp;정 </button></td> 
			             </tr>
			             <tr>	
			             	<td colspan="2" id="last-td"> 
			                  <button type="button" class="btn float-left">확&nbsp;&nbsp;인</button>
			                  <button type="button" class="btn float-right">돌아가기 </button>
			                </td>
			             </tr>
		              </table>
		         </form>
	        </div>     
		</div>
	</div>
	<script src="../resources/js/membermodifyview.js" type="text/javascript"></script>
</body>
</html>