<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="../resources/css/commonheader.css" type="text/css">
<link rel="stylesheet" href ="/resources/css/passwordmodifyview.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="../resources/js/bootstrap.min.js" type="text/javascript" /></script>
<script src="../resources/js/passwordmodifyview.js" type="text/javascript" /></script>
<title>Insert title here</title>
</head>
<jsp:include page="../common/commonheader.jsp" flush="false" />
<body>
	 <div class="container">
		<div class="modifypass-container">
			<div class="modifypass-form">
		         <form>
		         	 <table class= "modify-table">
			         	 <tr >
			         	 	 <td colspan="2" class="modify-title" >패스워드 수정</td>
			         	 </tr>
			             <tr>
			                <td> PASSWORD : </td>
			                <td><input type="password" id="password" class="input-control overlap" name="password" />
			             </tr>
			              <tr>
			             	 <td>PASSWORD CHECK :</td>
			             	 <td><input type="password" id="passwordCheck"  class="input-control overlap" name="passwordCheck" />
			             </tr>
			             <tr>	
			             	<td colspan="2" class="last-td"> 
			                  <button type="button" class="btn float-left">확&nbsp;&nbsp;인</button>
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