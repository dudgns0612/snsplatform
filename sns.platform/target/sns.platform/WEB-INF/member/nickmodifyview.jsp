<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="../resources/css/nickmodifyview.css" type="text/css" />
<link rel="stylesheet" href="../resources/css/commonheader.css" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="../resources/js/bootstrap.min.js" type="text/javascript" /></script>
<script src="../resources/js/nickmodifyview.js" type="text/javascript" /></script>
<title>Insert title here</title>
</head>
<jsp:include page="../common/commonheader.jsp" flush="false" />
<body>
	<div class="container">
		<div class="modifynick-container">
			<div class="modifynick-form">
		         <form>
		         	 <table class= "modify-table">
			         	 <tr >
			         	 	 <td colspan="2" class="modify-title" >닉네임 수정</td>
			         	 </tr>
			             <tr>
			               	 <td> Nickname : </td>
			               	 <td><input type="text" id="nick" class="input-control overlap" />
			             	 <button type="button" id="nick-overlap-btn" class="overlap-btn"> 중복확인 </button></td>
			             </tr>
			             <tr>	
			             	<td colspan="2" class="last-td"> 
			                  <button type="button" class="btn float-left">수&nbsp;&nbsp;정</button>
			                  <button type="button" class="btn float-right">돌아가기 </button>
			                </td>
			             </tr>
		              </table>
		         </form>
	        </div>     
		</div>
	</div>
</body>
</html>