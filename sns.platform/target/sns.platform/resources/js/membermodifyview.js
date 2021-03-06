var upload = document.getElementById('input-file'); /*파일 업로드*/
var modalContent = $('.modal-body'); /*버튼이 2개인모달 컨텐트*/
var myModalContent = $('.my-modal-body'); /*버튼이 하나인 모달 컨텐트*/
var fileCheck = 0;
	$(document).ready(function(){
		$.ajax({
			url: '/user/'+userNum,
			type : 'get',
			dataType : 'json',
			success : function(data){
				$('.img-rounded').attr('src', data.data.imageUrl);	
			}
		});
	});
	
	
	/*수정완료 버튼 이벤트*/
	$(document).on('click','.float-left',function(){
		var data = $('.input-control');
		var file = upload.files[0]; 
		var formData = new FormData();
		if(fileCheck == 1){
			formData.append("userFile",file);
		}
		formData.append("userPass",data.eq(1).val());
		formData.append("userNick",data.eq(2).val());
		$.ajax({
			type : 'post',
			url : '../user/modify',
			data : formData,
			dataType : 'json',
			processData : false,
			contentType : false,
			success : function(data){
				if(data.code){
					 myModalContent.html("&nbsp&nbsp수정이 완료되었습니다.");
					 $('#myModal').modal();
				} else{
					 myModalContent.html("&nbsp&nbsp수정이 실패되었습니다. 다시 시도하여 주세요.");
					 $('#myModal').modal();	
				}
				$(document).on('click','.center-block',function(){
					window.location.href="/board";
				});
			},
			  error : function(error){
					$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
					$('#myModal').modal();
			  }	
		});
	});

	$(document).on('click','.float-right',function(){
		modalContent.html("정보수정을 종료하시겠습니까?");
		$('#checkModal').modal();
		$('.pull-left').click(function(){
			window.location.href="/board";
		});
		$('.pull-right').click(function(){
			return;
		});
		
	});
	
	
	$(document).on('change',upload,function(){
			var reader = new FileReader;
			var file = upload.files[0]; 
			
			reader.onload = function(event){
				$('.img-rounded').attr('src', event.target.result).width(150).height(150);
			}
			if(file){
				reader.readAsDataURL(file);	
				fileCheck = 1;
			}else {
				$('.img-rounded').attr('src', "../resources/basicimage.png").width(150).height(150);
			}	
	});
	
	
	$(document).on('click',('.passwordEdit'),function(){
		window.location.href = '/member/modifypassword';
	});
	
	$(document).on('click',('.nicknameedit'),function(){
		window.location.href = '/member/modifynickname';
	});
	

	
	
	