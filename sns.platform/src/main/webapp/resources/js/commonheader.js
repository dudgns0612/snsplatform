$(document).ready(function(){
	var $userModifyBtn = $('#user-modify');
	var userLogoutBtn = $('#user-logout');
	var modalContent = $('.modal-body');
	var currentUrl = $(location).attr('href');

	
	
	$.ajax({
		url: '/user/'+userNum,
		type : 'get',
		dataType : 'json',
		success : function(data){
			$('.userprofileimage').attr('src', data.data.imageUrl);	
		}
	});
		
	
	
	
	$userModifyBtn.click(function(){
		if(currentUrl.match('member/modify')){
			modalContent.html("정보수정을 마치시겠습니까?");
			$('#checkModal').modal();
			$('.pull-left').click(function(){
				window.location.href="/board";
			});
			$('.pull-right').click(function(){
				return;
			});
		}else if(currentUrl.match('board/create')){
			modalContent.html("게시물 작성을 마치시겠습니까?");
			$('#checkModal').modal();
			$('.pull-left').click(function(){
				window.location.href="/board";
			});
			$('.pull-right').click(function(){
				return;
			});	
		} else{
			window.location.href="/member/modify";
		}
	});
	
	userLogoutBtn.click(function(){
		modalContent.html("정말로 로그아웃 하시겠습니까?");
		$('#checkModal').modal();
		$('.pull-left').click(function(){
			$.ajax({
				 type : 'get'
				,dataType : 'json'
				,url :'/logout'
				,success : function(data){
						window.location.href='/home';
				},
			    error : function(error){
			    	$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
					$('#myModal').modal();
					window.location.href = '/home';
			    }
			});
			
		});
	}); 

})




$(document).on('click','.mainlogoimg',function(){
	window.location.href = '/board';
});


