$(document).ready(function(){
	
	
});




$(document).on('click',('.float-left'),function(){
		var password =  $('#password').val();
		var passwordCheck = $('#passwordCheck').val();
		if(password == ''){
			$('.my-modal-body').html("패스워드를 입력하여 주세요.");
			$('#myModal').modal();
		} else if(password != passwordCheck){
			$('.my-modal-body').html("패스워드가 일치하지 않습니다.");
			$('#myModal').modal();
		}else {
			var data = ({'userPass':password});
			$.ajax({
				url : '/user/modify',
				type : 'post',
				data : data,
				dataType: 'json',
				success : function(data){
					if(data.code){
						$('#password').html(password);
						$('.my-modal-body').html("패스워드가 수정되었습니다.");
						$('#myModal').modal();
					}else {
						$('#password').html(password);
						$('.my-modal-body').html("정보가 수정되지 않았습니다. 재시도 해주세요.");
						$('#myModal').modal();
					}
					$('.center-block').click(function(){
						window.location.href = '/member/modify';
					})
				},
			    error : function(error){
					$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
					$('#myModal').modal();
			    }		
			});
		}
	
		
		
	});


$(document).on('click',('.float-right'),function(){
	$('.modal-body').html("패스워드 수정을 종료하시겠습니까?");
	$('#checkModal').modal();
	$('.pull-left').click(function(){
		window.location.href = '/member/modify';
	});
	$('.pull-right').click(function(){
		return;
	});		
	
});