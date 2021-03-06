var allFileData = new Array() ;
var deleteFileData = new Array() ;
var currentUrl = $(location).attr('href');
var $modalContent = $('.modal-body');
var $mymodalContent = $('.my-modal-body');
var boardNum;
var cnt = 0;
$(document).ready(function() {
	$fileUploadList = $('.file-upload-list');
	boardNum = currentUrl.split('boardNum=')[1];
	
	
	$.ajax({
		url : '/board/'+boardNum,
		type : 'get',
		dataType : 'json',
		success : function(data){
			$('#board-content').val(data.data.boardContent);
		},
	    error : function(error){
			$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
			$('#myModal').modal();
	    }
		
	});
	
	
	$.ajax({
		url : '/board/'+boardNum+'/files',
		type : 'get',
		dataType : 'json',
		success : function(data) {
			var fileList = data.data;
			if(fileList != null){
				for (var i = cnt; i < fileList.length; i++) {
					 var el = '';
					 el += '<div class="orgfiles" >';
					 el += '<input type="hidden" name="fno" value='+fileList[i].fileNum+' /> '
					 el +=  ' '+ fileList[i].fileName + '&nbsp; <label class="filedelete">ⓧ</label>  ';
					 el += '</div>';
					 $fileUploadList.append(el);
				}
			}
		},
	    error : function(error){
			$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
			$('#myModal').modal();
	    }
	});
	
	
	
	
	
	
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
$(document).on('click','#boardmodify',function(){	
		$modalContent.html("게시물을 수정하시겠습니까?");
		$('#checkModal').modal();
		$('.modal-footer > .pull-left').click(function() {
			var fileLists = $('.files');
			var formData = new FormData();
			var boardContent = $('#board-content');
			
			if(boardContent.val() != ''){
				formData.append('boardContent',boardContent.val());
				 if(allFileData.length > 0){
					 for(var i=0;i<fileLists.length;i++){	 	
					       if($(fileLists[i]).is(":visible")==true){
								formData.append('files',allFileData[i]);	
					       }         
					 }
				 }
				
				 if(deleteFileData.length > 0){
					 for(var i=0; i < deleteFileData.length; i++){
						 formData.append('delFiles',deleteFileData[i]);
					 }
				}			 
				 
				$.ajax({
					type : 'post', 
					url : '/board/'+boardNum,
					processData : false,
					contentType : false,
					dataType : 'json',
					data : formData,
					success : function(data) {
						if(data.code == 1){
							window.location.href='/board';
						}else{
							$('.my-modal-body').html("게시물 수정에 실패하셨습니다. 다시 시도하여주세요.");
							$('#myModal').modal();
						}
				    }, 
				    error : function(error){
						$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
						$('#myModal').modal();
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
	   $deleteFileNum = $(this).parents('.orgfiles').children('input[type=hidden]').val();
	   //기존에 있던 것 삭제시 삭제할 파일번호를 저장
	   if($deleteFileNum != null){
		   // 기본값 delfiles가 1개라도 있을시 배열로 보내기위해서
		   deleteFileData.push('0');
		   deleteFileData.push('0');
		   deleteFileData.push($deleteFileNum);
	   } else{
		   //새로 추가된것은 숨김
		   $(this).parent().hide(); /*선택한 div hide*/
	   }
	
	  
});


