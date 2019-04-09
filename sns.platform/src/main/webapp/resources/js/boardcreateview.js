var allFileData = new Array() ;
var $modalContent = $('.modal-body'); // $ 쓰기
var $mymodalContent = $('.my-modal-body');
var cnt = 0;
$(document).ready(function() {
});


/*선택된 파일 업로드*/ 
$(document).on('change','.fileupload',function(e){
	var fileList = $('#input-file')[0].files;
	var fileArea= $('.file-upload-list');
	var len = parseInt(fileList.length+cnt);
	var cnttmp = 0;
	if(fileList != null){
		for (var i = cnt; i < len; i++) {
			 var el = '';
			 allFileData.push(fileList[i-cnt]);
			 el += '<div class="files" >';
			 el +=  ' '+ fileList[parseInt(i-cnt)].name + '&nbsp; <label  class="filedelete">ⓧ</label>  ';
			 el += '</div>';
			 cnttmp = parseInt(cnttmp+1);
			 fileArea.append(el);
		}
	}
	cnt += cnttmp;
	
	$("#input-file").val(''); /*초기화*/
});

/*파일전체삭제*/
$(document).on('click','.filecanclebtn',function(){
	var removefile = $('.files');
	
	allFileData = new Array(); /*초기화*/ 
	
	removefile.remove();
});


/*최종결과 게시물 등록*/
$(document).on('click','#boardcreate',function(){	
		$modalContent.html("게시물을 등록하시겠습니까?");
		$('#checkModal').modal();
});

$('.modal-footer > .pull-left').click(function() {
	var fileLists = $('.files');
	var formData = new FormData();
	var boardContent = $('.input-control');
	
	if(boardContent.val() != ''){
		formData.append('boardContent',boardContent.val());
		 for(var i=0;i<fileLists.length;i++)
		 {
		       if($(fileLists[i]).is(":visible")==true)
		       {
		    	   formData.append('files',allFileData[i]);
		       }         
		 }
	
		$.ajax({
			type : 'post', 
			url : '/board',
			processData : false,
			contentType : false,
			dataType : 'json',
			data : formData,
			success : function(data) {
				if(data.code == 1){
					window.location.href='/board';
				}else{
					modalContent.html("게시물 등록에 실패하셨습니다. 다시 시도하여주세요.");
					$('#myModal').modal();
				}
		    },
		    error : function(error){
				$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
				$('#myModal').modal();
				window.location.href = '/home'
		    }
			
	   });
	} else{
		$mymodalContent.html("게시물 내용을 적어주세요.");
		$('#myModal').modal();
	}
	
});

$('.modal-footer > .pull-left').click(function() {
	var formData = new FormData();
	var boardContent = $('.input-control');
	
	if(boardContent.val() != ''){
		formData.append('boardContent',boardContent.val());
		 for(var i=0;i<fileLists.length;i++)
		 {
		       if($(fileLists[i]).is(":visible")==true)
		       {
		    	   formData.append('files',allFileData[i]);
		       }         
		 }
	
		$.ajax({
			type : 'post', 
			url : '/board',
			processData : false,
			contentType : false,
			dataType : 'json',
			data : formData,
			success : function(data) {
				if(data.code == 1){
					window.location.href='/board';
				}else{
					modalContent.html("게시물 등록에 실패하셨습니다. 다시 시도하여주세요.");
					$('#myModal').modal();
				}
		    },
		    error : function(error){
				$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
				$('#myModal').modal();
				window.location.href = '/home'
		    }
			
	   });
	} else{
		$mymodalContent.html("게시물 내용을 적어주세요.");
		$('#myModal').modal();
	}
	
});

$('.modal-footer > .pull-right').click(function() {
	return;
});

$(document).on('click','#boardcancle',function(){
		$modalContent.html("게시물등록을 종료하시겠습니까?");
		$('#checkModal').modal();
		$('.modal-footer > .pull-left').click(function() {
			window.location.href = "/board";
		});
		$('.modal-footer > .pull-right').click(function() {
			return;
		});
	
});

/*파일 삭제*/
$(document).on('click', '.filedelete', function(){
	   $(this).parent().hide(); /*선택한 div hide*/
});


