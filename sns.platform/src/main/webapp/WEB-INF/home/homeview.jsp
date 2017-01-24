<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="resources/css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="resources/css/homeview.css" type="text/css">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="../resources/js/bootstrap.min.js" type="text/javascript" /></script>
	<script src="resources/js/homeJs.js" type="text/javascript" /></script>
<%-- 	<script src ="<c:url value="resources/js/test.js" />"></script>	 --%>
	<script>
		
	

		$(document).ready(function() {

			var a = {
				name : function(aa) {

					console.log(aa);
				}
			}

			a.name('aa');

		});
	</script>
	
	
	
	
</head>
<body>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	        <h4 class="modal-title" id="myModalLabel">경    고</h4>
	      </div>
	      <div class="modal-body">
	         	아이디와 패스워드를 확인하세요.
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default center-block" data-dismiss="modal">확  인</button>
	      </div>
	    </div>
	  </div>
	</div>




	<div class="container">
		<div class="login-form">
			 <img src="resources/img/openit.png" width="300" height="150" /><br/><br/><br/>
	         <form action="/j_spring_security_check" method="post">
	             <div>
	                 <input type="text" class="form-control" name="userId"  placeholder="아이디를 입력하세요."  autofocus /><br/>
	             </div>
	             <div>
	                 <input type="password" class="form-control" name="userPass" placeholder="패스워드를 입력하세요." /><br/>
	             </div>
	             <div>
	                 <button type="submit" class="btn float-left">  로그인! </button>
					 <button type="button" class="btn float-right"> 회원가입 </button>   
	             </div>
	         </form>
        </div>
	</div>
</body>
</html>