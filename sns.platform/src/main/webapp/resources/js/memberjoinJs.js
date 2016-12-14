$(document).ready(function(){
	var upload = document.getElementById('input-file'); /*파일 업로드*/ /*change메소드가 크롬에서 안먹음*/
	var fileCancleBtn = $('#file-cancle-btn');
	var idOverlapBtn = $('#id-overlap-btn'); /*아이디중복확인*/
	var nickOverlapBtn = $('#nick-overlap-ntn'); /*닉네임중복확인*/
	var joinSeccessBtn = $('.float-left');/*가입완료*/
	var cancleBtn = $('.float-right'); /*취소*/
	var successCheck = $('.input-control'); /*form data*/
	var idOverlapCheck; /*아이디중복확인을 위한 값*/
	var nickOverlapCheck; /*닉네임 중복확인을 위한 값*/
	var modalContent = $('.modal-body');
	var idInputText = $('#user-id');
	var mainLogo = $('#logo');
	var fileCheck = 0;
	
	/*가입완료*/
	joinSeccessBtn.click(function(){
		var userId = successCheck.eq(0).val();
		var firstPass = successCheck.eq(1).val();
		var twoPass = successCheck.eq(2).val();
		var userNick = successCheck.eq(3).val();
		var check = 1;
		
		//회원가입전 아이디검사 재시도
		$.ajax({
			type : 'get'
			,url : '../user/checkid/'+userId
			,dataType : 'json'
		    ,success : function(data){
				if(data.code == 0){
					return;
				}
			},
			  error : function(error){
					$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
					$('#myModal').modal();
			  }	
		});
		//회원가입전 닉네임 체크 재시도
		$.ajax({
			type : 'get'
			,url : '../user/checkname/'+userNick
			,dataType : 'json'
			,success : function(data) {
				if(data.code == 0){
					return;
				} 
			},
			  error : function(error){
					$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
					$('#myModal').modal();
			  }	
		});
		
		
		if(userId == ""){
			modalContent.html("아이디를 입력하여 주세요.");
			$('#myModal').modal();
			check = 0;
		}else if(idOverlapCheck == ""){
			modalContent.html("아이디 중복확인을 하여주세요.");
			$('#myModal').modal();
			check = 0;
		}else if(idOverlapCheck != userId){/*중복확인 하였을 때와 가입완료 시 입력된 값이 다를때*/
			modalContent.html("아이디 중복확인을 입력하여 주세요.");
			$('#myModal').modal();
			check = 0;
		}else if(firstPass != twoPass){
			modalContent.html("입력하신 비밀번호가 같지 않습니다.");
			$('#myModal').modal();
			check = 0;
		}else if(firstPass == "" || twoPass =="") {
			modalContent.html("비밀번호를 입력하여 주세요.");
			$('#myModal').modal();
			check = 0;
		}else if(userNick == "") {
			modalContent.html("닉네임을 입력하여 주세요.");
			$('#myModal').modal();
			check = 0;
		}else if(nickOverlapCheck == ""){
			modalContent.html("닉네임을 입력하여 주세요.");
			$('#myModal').modal();
			check = 0;
		}else if(nickOverlapCheck != userNick){
			modalContent.html("닉네임 중복확인을 하여주세요.");
			$('#myModal').modal();
			check = 0;
		}

		if(check == 1){ /*true / false  == ===*/
			/*form data*/
			var data = $('.input-control');	
			var file = upload.files[0]; 
			var formData = new FormData();
			if(fileCheck == 1){
				formData.append("userFile",file);
			} else{
				formData.append("userFile",'null');
			}
			formData.append("userType",'user');
			formData.append("userId",data.eq(0).val());
			formData.append("userPass",data.eq(1).val());
			formData.append("userNick",data.eq(3).val());
			$.ajax({
				type : 'post' /*, 위치*/
				,url : '/user'
				,processData : false
				,contentType : false
				,dataType : 'json'
				,data : formData
				,success : function(data) {
					if(data.code == 1){
						modalContent.html("회원가입이 정상적으로 처리되었습니다.");
						$('#myModal').modal();
						$('.modal-footer > .center-block').click(function(){
							window.location.href = "/home"; // 밖으로 공통 //콜백
						});
					}else {
						if(data.exception == "duplicate"){
							modalContent.html("죄송합니다. 닉네임 또는 아이디가 중복되었습니다 재시도 해주시기바랍니다.");
							$('#myModal').modal();
							$('.modal-footer > .center-block').click(function(){
								window.location.href = "/home";
							});
						}else{
							modalContent.html("회원가입이 정상적으로 처리되지 않았습니다.");
							$('#myModal').modal();
							$('.modal-footer > .center-block').click(function(){
								window.location.href = "/home";
							});
						}
						
					}
				},
			    error : function(error,status,errorThrown){
			    	console.dir("error");

			    	$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
					$('#myModal').modal();
					window.location.href = '/home';
			    }
			});
		}
	});
	

	
	/*파일 사진*/
	upload.onchange = function(e){
		var reader = new FileReader;
		var file = upload.files[0]; 
		
		reader.onload = function(event){
			$('.img-rounded').attr('src', event.target.result).width(150).height(150);
		}
		if(file){
			reader.readAsDataURL(file);	
			fileCheck = 1;
		}else {
			$('.img-rounded').attr('src', "../resources/img/basicimage.png").width(150).height(150);
			fileCheck = 0;
		}	
	}
	
	fileCancleBtn.click(function(){
		$('.img-rounded').attr('src', "../resources/img/basicimage.png").width(150).height(150);
		fileCheck = 0;
	});
	
	
	/*아이디 중복확인*/
	idOverlapBtn.click(function(){
		var userId = $('#user-id').val();
		if(userId == ""){
			modalContent.html("아이디를 입력하여 주세요.");
			$('#myModal').modal();
		}
		else {
			$.ajax({
				type : 'get'
				,url : '../user/checkid/'+userId
				,dataType : 'json'
			    ,success : function(data){
					if(data.code == 0){
						modalContent.html("이미 사용중인 아이디입니다.");
						$('#myModal').modal();
					} else{
						modalContent.html("사용이 가능한 아이디입니다.");
						$('#myModal').modal();
						idOverlapCheck = userId;
					}	
				},
				  error : function(error){
						$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
						$('#myModal').modal();
				  }	
			});
		}
		
	});
	
	nickOverlapBtn.click(function(){
		var userNick = $('#user-nick').val();
		if(userNick == ""){
			modalContent.html("닉네임를 입력하여 주세요.");
			$('#myModal').modal();
		} else{
			$.ajax({
				type : 'get'
				,url : '../user/checkname/'+userNick
				,dataType : 'json'
				,success : function(data) {
					if(data.code == 0){
						modalContent.html("이미 사용중인 닉네임입니다.");
						$('#myModal').modal();
					} else{
						modalContent.html("사용이 가능한 닉네임입니다.");
						$('#myModal').modal();
						nickOverlapCheck = userNick;
					}
				},
				  error : function(error){
						$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
						$('#myModal').modal();
				  }	
			});
		}
	});

	
    /*한글 필터링*/
	idInputText.keyup(function(event){
		 var hangul = /[\ㄱ-ㅎㅏ-ㅣ가-힣]/g;/*한글을 체크하는 정규식*/
		 var specialChar = /[~!@\#$%^&*\()\-=+_']/gi; 
		 var inputVal = idInputText.val(); // 입력한 텍스트
			
		 //한글이 입력되면 한글을삭제하고 경고창
		if(hangul.test(inputVal)){//test()를 이용하여 문자열이 포함되어있나 확인
			modalContent.html("영문 숫자조합을 사용해주세요.");
			$('#myModal').modal();
			idInputText.val(inputVal.replace(hangul, '')); 
		}
		//특수문자가 입력되면 그문자를 삭제하고 경고창
		else if(specialChar.test(inputVal)){ 
			idInputText.val(inputVal.replace(specialChar, '')); 
			modalContent.html("영문 숫자조합을 사용해주세요.");
			$('#myModal').modal();
		}
	});
	
	/*메인로고 클릭 시*/
	mainLogo.click(function(){
		modalContent.html("회원가입을 종료하시겠습니까?");
		$('#checkModal').modal();
		$('.modal-footer > .pull-left').click(function() {
			window.location.href = "/home";
		});
		$('.modal-footer > .pull-right').click(function() {
			return;
		});
			
		
	});
	
	
	
	/*취소*/
	cancleBtn.click(function(){
		modalContent.html("회원가입을 종료하시겠습니까?");
		$('#checkModal').modal();
		$('.modal-footer > .pull-left').click(function() {
			window.location.href = "/home";
		});
		$('.modal-footer > .pull-right').click(function() {
			return;
		});
	});
	
	
});