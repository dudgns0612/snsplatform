var nicknameCheck = false;
var nickname;
$(document).ready(function(){
		
});


$(document).on('click','#nick-overlap-btn',function(){
	nickname = $('#nick').val();
	if(nickname == ''){
		$('.my-modal-body').html("닉네임을 입력하여 주세요.");
		$('#myModal').modal();
	}else{
		$.ajax({
			url : '/user/checkname/'+nickname ,
			type : 'get',
			dataType : 'json',
			success : function(data){
				if(data.code == 1){
					$('.my-modal-body').html("사용이 가능한 닉네임입니다.");
					$('#myModal').modal();
					nicknameCheck = true;
				} else{
					$('.my-modal-body').html("이미 사용중인 닉네임입니다.");
					$('#myModal').modal();
					nicknameCheck = false;
				}
			},
		    error : function(error){
				$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
				$('#myModal').modal();
		    }
		});
	}
	
	
	
});


$(document).on('click','.float-left',function(){
	var currentNickname = $('#nick').val();
	
	if(nicknameCheck){
		if(currentNickname != nickname){
			$('.my-modal-body').html("중복확인을 해주세요.");
			$('#myModal').modal();
		} else{
			var data = ({'userNick':currentNickname});
			$.ajax({
				url : '../user/modify',
				type : 'post' ,
				data : data ,
				dataType : 'json' ,
				success : function(data){
					if(data.code == 1){
						$('.my-modal-body').html("수정이 완료되었습니다.");
						$('#myModal').modal();
					} else{
						$('.my-modal-body').html("수정이 실패되었습니다. 재시도하여 주세요.");
						$('#myModal').modal();
					}
					$('.center-block').click(function(){
						window.location.href = '/board';
						
					});
				},
				  error : function(error){
						$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
						$('#myModal').modal();
				  }			
				
			});
		}
	} else{
		$('.my-modal-body').html("중복확인을 해주세요");
		$('#myModal').modal();
	}
	
		
});

