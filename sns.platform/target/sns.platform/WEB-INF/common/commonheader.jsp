<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>var userNum = ${member.userNum}</script>
<body>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	        <h4 class="modal-title" id="myModalLabel">경    고</h4>
	      </div>
	      <div class="my-modal-body">
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default center-block" data-dismiss="modal">확  인</button>
	      </div>
	    </div>
	  </div>
	</div>

	<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	        <h4 class="modal-title" id="myModalLabel">경     고</h4>
	      </div>
	      <div class="modal-body">
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">확인</button>
	        <button type="button" class="btn btn-default pull-right" data-dismiss="modal">취소</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="replyModifyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	        <h4 class="modal-title" id="myModalLabel">댓글&nbsp;&nbsp;수정</h4>
	      </div>
	      <div class="reply-modal-body">
	      	 <textarea class="replymodifytext"></textarea>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">수정</button>
	        <button type="button" class="btn btn-default pull-right" data-dismiss="modal">취소</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="container">
		<div class="header-container">
			<table class="header-table">
			<tr>
				<td class="main-logo">
					<img src="../resources/openit.png" class="mainlogoimg"/>
					<p>IT Company OpenIt Sns</p>
				</td>
		        <td class="user-profile">
		        	<img src= ""  class="userprofileimage"/>
		        </td>
		        <td id="name-limit">
		        	<p id="welcome-nick"> ${member.userNick}님 환영합니다.</p>
		       	</td>
		       	<td>
		       		<button type="button" id="user-modify" class="header-btn"> 나의정보 </button>
		       		<button type="button" id="user-logout" class="header-btn"> 로그아웃 </button>
		       	</td>
		     </tr>
	        </table>
         </div>
	</div>
</body>
<script src="../resources/js/commonheader.js"></script>
<script src="../resources/js/bootstrap.min.js" type="text/javascript" /></script>
</html>