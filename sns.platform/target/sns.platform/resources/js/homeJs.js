$(document).ready(function(){
	var loginBtn = $(".float-left");
	var joinBtn = $(".float-right");
	
	loginBtn.click(function(){
		  var data =  $('.form-control');
		  data.push({name:'userType',value:'user'});
		  $.ajax({
		          type: 'post'
		        , url: '/login'
		        , data: data
		        , dataType : 'json'
		        , success: function(data) {	        	
		        	if(data.code == 1){
		        		window.location.href = "board";
		        	}else {
		        		$('#myModal').modal();
		        	}
		         },
				  error : function(error){
						$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
						$('#myModal').modal();
				  }
			 });	
	});
	joinBtn.click(function(){	
		window.location.href = "member/join";
	});
});


 	