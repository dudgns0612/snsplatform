<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../resources/css/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet" href="../resources/css/memberjoinview.css"
	type="text/css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="../resources/js/bootstrap.min.js" type="text/javascript" /></script>
<script src="../resources/js/memberjoinJs.js" type="text/javascript" /></script>
</head>
<body>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">경 고</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default center-block"
						data-dismiss="modal">확 인</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="checkModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">경 고</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-left"
						data-dismiss="modal">확인</button>
					<button type="button" class="btn btn-default pull-right"
						data-dismiss="modal">취소</button>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
		<img src="../resources/img/openit.png" width="200" height="70" id="logo" />
		<div class="join-form">
			<form>
				<table>
					<tr>
						<td colspan="2" class="join-title">회원가입</td>
					</tr>
					<tr id="image-tr">
						<td colspan="2" style="padding-bottom: 0px;"><img
							src="../resources/img/basicimage.png"
							style="width: 150px; height: 150px;" class="img-rounded"></td>
					</tr>
					<tr>
						<td class="filebox" colspan="2"><label for="input-file">파일선택</label>
							<input type="file" id="input-file" class="upload-hidden"
							accept=".jpg, .png, .avi, .mp3, .mp4, .gif, .wmv"><br />
							<button type="button" id="file-cancle-btn">선택 취소</button></td>
					</tr>
					<tr>
						<td>ID :</td>
						<td><input type="text" id="user-id"
							class="input-control overlap" name="userId"
							placeholder="아이디를 입력하세요." />
							<button type="button" id="id-overlap-btn" class="overlap-btn">
								중복확인</button></td>
					</tr>
					<tr>
						<td>PASSWORD :</td>
						<td><input type="password" class="input-control"
							name="userPass" placeholder="패스워드를 입력하세요." /></td>
					</tr>
					<tr>
						<td>PASSWORD<br /> CHECK :
						</td>
						<td><input type="password" class="input-control"
							name="PasswordCheck" placeholder="패스워드를 다시 입력하세요." /></td>
					</tr>
					<tr>
						<td>NICKNAME :</td>
						<td><input type="text" id="user-nick"
							class="input-control overlap" name="userNick"
							placeholder="닉네임을 입력하세요." />
							<button type="button" id="nick-overlap-ntn" class="overlap-btn">
								중복확인</button></td>
					</tr>
					<tr>
						<td colspan="2" id="last-td">
							<button type="button" class="btn float-left">가입완료</button>
							<button type="button" class="btn float-right">취 소</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>