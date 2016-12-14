var $boardList = $('.center-form');
var currentUrl = $(location).attr('href');
var href;
var lastBoard;
var lastReply;
var boardFile;
var searchType;
var keyWord;
var boardNum;
var currentBoard;

$(document).ready(function(){
	console.dir(userNum);
	$('.carousel1').carousel();
	href = currentUrl.split('/board',1)//현재 사용중인 서버주소 잘라서가져옴
	boardListView('/boards','append');
	
	/*무한스크롤*/
	$(".center-form").scroll( function() {	
		  var requestMap = new Map;
	      var lastBoardNum = lastBoard.boardNum;
		  var elem = $(".center-form");
		  if ( (elem[0].scrollHeight - elem.scrollTop()) == elem.outerHeight() || (elem[0].scrollHeight - elem.scrollTop()) == (elem.outerHeight()-0.4) 
				  || (elem[0].scrollHeight - elem.scrollTop()) <= (elem.outerHeight()+0.4)){
			  boardListView('/boards?pageNum=5'+'&lastBoardNum='+lastBoardNum+"&searchType="+searchType+"&keyWord="+keyWord,'append');
		    }
		});
	

	//게시글 목록 불러옴
	replyListView('/board/'+boardNum+'/replys');

	//댓글 페이징처리
	$(".replylist").scroll( function() {	
		  var elem = $(".replylist");
		  if ( (elem[0].scrollHeight - elem.scrollTop()) == elem.outerHeight() || (elem[0].scrollHeight - elem.scrollTop()) == (elem.outerHeight()-0.4) 
				  || (elem[0].scrollHeight - elem.scrollTop()) <= (elem.outerHeight()+0.4)){
			  replyListView('/board/'+boardNum+'/replys?pageNum=5'+'&lastBoard='+lastReply);
		    }
		});
			
});


//검색조건 검사 및 게시물 리스트 등록
$(document).on('click','.search-btn',function(){
	var selectIndex = $("#selectBox option").index($("#selectBox option:selected")); 
	var searchValue = $('#searchValue').val();

	if(searchValue == ''){
		$('.my-modal-body').html("검색하실 내용을 입력하여 주세요.");
		$('#myModal').modal();
	} else if(selectIndex == 0){
		$('.my-modal-body').html("검색하실 조건을 선택하여 주세요.");
		$('#myModal').modal();
	} else if(selectIndex == 1){
		if(!searchValue.match('/') || searchValue.length != 10){
			$('.my-modal-body').html("검색 양식을 올바르게 적어주세요.");
			$('#myModal').modal();
		} else{
			searchValue = searchValue.split("/")[0]+searchValue.split("/")[1]+searchValue.split("/")[2];
			searchType = 'date';
			keyWord = searchValue;
			boardListView('boards?searchType=date&keyWord='+searchValue,'html');
		}
	} else {
		boardListView('boards?searchType=name&keyWord='+searchValue,'html')
	}	
});





//댓글 리스트
$(document).on('click','.replyviewbtn',function(){
	replyListView('board/'+boardNum+'/replys');
	//댓글 리스트 보는것과 닫는것
    var $replyList = $('.replylist');
	
	if ($replyList.is(':hidden')){
    	$(this).html('댓글닫기');
		$replyList.slideDown();
    } else {
    	$(this).html('댓글보기');
    	$replyList.slideUp();
    }
});


//댓글 페이징처리 ajax
function replyListView(url){
	var replyList = $('.replylist');
	$.ajax({
		url : url,
		type : 'get',
		dataType : 'json',
		success : function(data){
			if(data.code == 1){
				var replyData = data.data;
				var el = '';
				for(var i = 0 ; i < replyData.length; i++){
					if(i==replyData.length-1){
						lastReply = i;
					}
					var replyDate = replyData[i].replyRegDate.substring(0,8);
					var replyDateFormat = replyDate.substring(0,4)+"/"+replyDate.substring(4,6)+"/"+replyDate.substring(6,8);
					el += '<div class="replylistval"> <input type="hidden" name="rno" value='+replyData[i].replyNum+' /> '
					el += '<div class="replycreator">작성자 <br/>'+replyData[i].userNick+'</div> &nbsp;&nbsp;&nbsp;';
					el += '<div class="replycontent"> '+replyData[i].replyContent+' </div> '
					el += '<div class="replycreatedate"> 작성날짜  <br/> '+replyDateFormat+'</div>';
					if(userNum == replyData[i].userNum){
						el += '<div class="replymodiftdelete"> <img class="replydelete" src="resources/delete.png" /> <img class="replymodify" src ="resources/edit.png" /> </div>'
					} 
					el += '</div><br/>';
				}
				replyList.html(el);
			} else{
				
			} 
		},
	    error : function(error){
			$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
			$('#myModal').modal();
	    }
	})
}



/*게시글 목록에 파일첨부 리스트*/
$(document).on('click', '.boardfile', function(){
    
	$('.filedateform .filelistvalue:visible').slideUp();
	
    var $next = $(this).next(); //현재위치의 다음요소
    if ($next.is(':hidden')){
    	
    	$next.slideDown();
    	$(this).html("▲첨부파일");
    } else {
    	$(this).html("▼첨부파일");
        $next.slideUp();
    }
});

$(document).on('click','.board-create-btn', function() {
	window.location.href='/board/create';
});




function boardListView(url , addType){
	if(addType=='html'){
		$('.center-form').html('');
	}
	$.ajax({
		url : url,
		type : 'get',
		dataType : 'json',
		success : function(data){
			if(data.code == 1){
				var len = data.data.length; /*불러온 게시글의 갯수*/
				for(var i=0; i < len ; i++){
					if(len < 5){
						searchType;
						keyWord;
					}
					if(i==len-1){
						lastBoard = data.data[i];
					}
					var board = data.data[i];
					var boardDate = board.boardRegDate.substring(0,8);
					var boardDateFormat = boardDate.substring(0,4)+"/"+boardDate.substring(4,6)+"/"+boardDate.substring(6,8);
					var fileCnt = board.boardImgCnt + board.boardAudCnt+ board.boardVidCnt;
					var el = '';
					el += '<div class = "boardlist"  >';
					el += '<input type="hidden" name="bno" value='+board.boardNum+' /> '
					el += '<div> <img class= "userimage"  src=" '+href+'/user/'+board.userNum+'/image  " /> </div>';
					
					el += '<div class="boardmaker">';
					el += '작성자<br/>';
					el += ''+board.userNick+' <br/>';
					el += '댓글 : '+board.boardReplyCnt+' </div>';
					if(board.firstImageUrl != null){
						el += '<div> <img class="boardimage" src=" '+board.firstImageUrl+' " />  </div>   ';
						el += '<div class=" boardcontent">  '+board.boardContent+'    </div>';
					}else{
						el += '<div class=" imagenullboardcontent ">  '+board.boardContent+'    </div>';
					}
					el += '<div class="filedateform"> <div class="boardfile">  ▼ 첨부파일  </div> ';
					el += '<div class="filelistvalue"> 파일 갯수 : '+fileCnt+' <br/>';
					/*각 게시물의 파일 제목을 가져옴*/
					$.ajax({
						url : '/board/'+board.boardNum+'/files',
						type : 'get',
						dataType : 'json',
						async: false,  //동기식으로 전환
						success : function(data) {
							boardFile = data.data;
							var fileLen = boardFile.length;
							for(var i = 0; i < fileLen ; i++ ){
								el += ''+ boardFile[i].fileName   +' <br/>';
							}
						}
					});
					el += '</div>';
					el += '<div class="dateboard">';
					el += '등록일자 </div>';
					el += '<div class="dateboardvalue">';
					el += ' '+boardDateFormat+' ';
					el += ' </div> </div>  ';	

					$boardList.append(el);
				
				}
			}
		},
	    error : function(error){
			$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
			$('#myModal').modal();
	    }
	});
}



//셀렉트 박스에 따라 예시조건 보여줌
$(document).on('change','#selectBox',function(){
	var selectIndex = $("#selectBox option").index($("#selectBox option:selected")); 
	
	if(selectIndex == 0){
		$('#searchValue').attr('placeholder','입  력');
	}else if(selectIndex == 1){
		$('#searchValue').attr('placeholder','EX)2016/10/05');
	} else {
		$('#searchValue').attr('placeholder','찾고 싶은 닉네임');
	}
});



//상세게시물의 첨부파일보기 클릭시
$(document).on('click','.boardfilelist',function(){
	

    var $next = $(this).next(); // 현재위치에서 다음 객체

    var $parent = $(this).parent();
    if ($next.is(':hidden')){
    	
    	$next.slideDown();
    	$(this).html("▲첨부파일");
    } else {
    	$(this).html("▼첨부파일");
        $next.slideUp();
    }
	
});

//댓글 작성
$(document).on('click','.replybtn',function(){
	var $replyText = $('.replytext');  // 작성한 댓글 내용을 가져옴 
	var $replyList = $('.replylist');  // 댓글 리스트 div 
	var data = ({'replyContent':$replyText.val()});
	var replyTextVal = $replyText.val();
	
	if(replyTextVal == ''){
		$('.my-modal-body').html("댓글을 입력하여 주세요.");
		$('#myModal').modal();
	} else {
		$.ajax({
			url : 'board/'+boardNum+'/reply',
			type : 'post',
			data : data,
			dataType : 'json',
			success : function(data){
				if(data.code == 1){
					//즉시 cnt적용
					var boardReplyDiv = currentBoard.children('.boardmaker');
					var boardReply = currentBoard.children('.boardmaker').text();
					var boardReplyContent = boardReply.split('댓글 : ')[0];
					var boardCreator = boardReply.split('작성자')[1];
					boardCreator = boardCreator.split(' 댓글 :')[0];
					var boardReplyCnt = boardReply.split('댓글 : ')[1];
					boardReplyCnt = Number(boardReplyCnt);
					boardReplyCnt = parseInt(boardReplyCnt+1);			
					var el = '';
					el += '작성자<br/>';
					el += ''+boardCreator+' <br/>';
					el += '댓글 : '+boardReplyCnt+'';
					boardReplyDiv.html(el);
					
					$replyList.html('');
					
					replyListView('board/'+boardNum+'/replys');
									
				} else{
					$('.my-modal-body').html("댓글 작성에 실패하였습니다. 재시도 해주세요.");
					$('#myModal').modal();
				} 
				$replyText.val('');
			},
		    error : function(error){
				$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
				$('#myModal').modal();
				window.location.href = '/home';
		    }
		})
	}

	
});

// 게시글 상세보기
$(document).on('click','.imagenullboardcontent ,.boardcontent',function(){
	currentBoard = $(this).parents('.boardlist');
	boardNum = $(this).siblings('input[type=hidden]').val();
	var fileInfo;
	var $boardDetailFrom = $('.board-content-form');
	var $boardTitle = $('.board-title');
	var $boardContent = $('.boarddetailcontent');
	var $boardCreator = $('.board-creator');
	var $boardImage = $('#carousel-example-generic');
	var $boardUserImage=$('.boardUserImage');
	var $boardDiv = $('.filediv');
	var $boardFileList = $('.fileListdiv');

	$.ajax({
		url : 'board/'+boardNum,
		type : 'get',
		dataType : 'json',
		success : function(data){
			if(data.code == 1){
				//자기게시물이 아닐경우 수정 삭제버튼 숨김
				$modifyBtn = $('.modifybtn');
				$deleteBtn = $('.deletebtn');
				if(userNum != data.data.userNum){	
					$modifyBtn.attr('style','display : none');
					$deleteBtn.attr('style','display : none');
				} else{
					$modifyBtn.attr('style','display : inline');
					$deleteBtn.attr('style','display : inline');
				}
				
				
			var regDate = data.data.boardRegDate;
			var regDateFormat = regDate.substring(0,4)+"/"+regDate.substring(4,6)+"/"+regDate.substring(6,8);// db값을 형식대로 포맷
			$boardTitle.html(data.data.userNick+'님의 글'); //게시글의 제목
			$boardContent.html(data.data.boardContent); //게시글 내용
			$boardCreator.html('작성자 : '+data.data.userNick+'<br/> 작성날짜 : '+regDateFormat); //게시글날짜
			$boardUserImage.attr('src',href+'/user/'+data.data.userNum+'/image'); //작성자의 이미지 
			$.ajax({
				url : 'board/'+boardNum+'/files',
				type : 'get',
				dataType : 'json',
				async : false,
				success : function(data){
					if(data.code == 1){
						//게시물에 파일정보들이 담긴 객체
						fileInfo = data.data;
						//파일첨부리스트에 현재게시물의 파일을 다운로드 할수 있도록 수록
						var el='';
						el += '<div class = "boardfilelist">▼첨부 파일</div>';
						el += '<div class = "fileListdiv">';
						for(var i = 0; i < fileInfo.length ; i++){
							var fileNum = fileInfo[i].fileNum;
							el += ''+(i+1)+' &nbsp; '+fileInfo[i].fileName+' &nbsp;&nbsp;&nbsp; <img src="resources/download.png" class="filedown" onclick="javascript:fileDown(\''+href+'/board/'+boardNum+'/file/'+fileNum+'?view=down\');"'+'> </br>';
						}
						el += '</div>'
						$boardDiv.html(el);
					}
				}
			})  //파일갯수가 0개 보다 클경우 이미지슬라이드 적용//부트스트랩 케러셀
				if(fileInfo.length > 0){
					var el = '';				
					el += '<div class="carousel-inner" role="listBox">';
					
					for(var i = 0; i < fileInfo.length ; i++){
						//타입이 image인것들만 이미지로 보여줌
						if(fileInfo[i].fileType.match('image')){
							
							//첫번째에만 클래스 active를 추가하기 위해서
							if(i == 0){
								el += '<div class="item active" >';
							} else{
								el += '<div class="item"> ';
							}
							el += '<img class="img-responsive" src=  '+fileInfo[i].fileUrl+' />';
							el += '</div>';
							
							if(i == fileInfo.length-1){
								el += '<div class="filecnt"> 이미지 수 : '+(i+1)+'</div>'
							}
						}
					}
					el += '</div>'
					el += '<a class ="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">'
					el += '<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>'	
					el += '<span class="sr-only">Previous</span> </a>';
					
					el += '<a class ="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">'
					el += '<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>'	
					el += '<span class="sr-only">Next</span> </a>';
					$boardImage.html(el);
					//사진이 있는 경우 슬라이드를 보여주고 게시글사이즈를 줄임
					$boardImage.css('display','inline');
					$('.boarddetailcontent').css('width','400'); 
					$('.boarddetailcontent').css('left','440px'); 
				} else{
					//사진이 없을경우 슬라이드를 숨기고 게시글사이즈를 늘림
					$boardImage.css('display','none');
					$('.boarddetailcontent').css('width','950'); 
					$('.boarddetailcontent').css('left','100px'); 
				}	
			} else{
				
			}
		},
	    error : function(error,status,request){
			$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
			$('#myModal').modal();
			window.location.href = '/home';
	    }
	});
	
	$('#boardDetailModal').modal();
});



//댓글삭제
$(document).on('click','.replydelete',function(){
	var replyNum = $(this).parents('.replylistval').children('input[type=hidden]').val(); // 형제중 hidden type의 값
	var currentReply = $(this).parents().eq(1);
	$.ajax({
		url : 'board/'+boardNum+'/reply/'+replyNum,
		type : 'delete' ,
		dataType : 'json' ,
		success : function(data){
			if(data.code == 1){
				currentReply.remove();
				//삭제된 댓글 동적 적용
				var boardReplyDiv = currentBoard.children('.boardmaker');
				var boardReply = currentBoard.children('.boardmaker').text();
				var boardReplyContent = boardReply.split('댓글 : ')[0];
				var boardCreator = boardReply.split('작성자')[1];
				boardCreator = boardCreator.split(' 댓글 :')[0];
				var boardReplyCnt = boardReply.split('댓글 : ')[1];
				boardReplyCnt = Number(boardReplyCnt);
				boardReplyCnt = parseInt(boardReplyCnt-1);			
				var el = '';
				el += '작성자<br/>';
				el += ''+boardCreator+' <br/>';
				el += '댓글 : '+boardReplyCnt+'';
				boardReplyDiv.html(el);
				$('.my-modal-body').html("선택하신 댓글이 삭제되었습니다.");
				$('#myModal').modal();
				
				replyListView('board/'+boardNum+'/replys');
			}
		},
	    error : function(error){
			$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
			$('#myModal').modal();
			window.location.href = '/home';
	    }
	});
});

$(document).on('click','#boardBackBtn,.close',function(){
	//돌아가기버튼을 눌렀을 때 댓글상세창 닫음 
	$('.replylist').slideUp();
	$('.replyviewbtn').html('댓글보기');
});

//댓글 수정
$(document).on('click','.replymodify',function(){
	var replyNum = $(this).parents('.replylistval').children('input[type=hidden]').val(); // 형제중 hidden type의 값
	var replyContent = $(this).parents('.replylistval').children('.replycontent'); 
	console.dir(replyContent.text());
	$('.replymodifytext').val(replyContent.text());
	$('#replyModifyModal').modal();
	$('.modal-footer > .pull-left').click(function() {
		var replyContentval = $('.replymodifytext').val();
		var data = ({'replyContent':replyContentval});
		$.ajax({
			url : 'board/'+boardNum+'/reply/'+replyNum,
			type : 'post',
			data : data,
			dataType : 'json',
			success : function(data){
				if(data.code == 1){
					replyContent.text(replyContentval);
					$('.my-modal-body').html("댓글이 수정되었습니다.");
					$('#myModal').modal();
				} else{
					$('.my-modal-body').html("댓글 삭제에 실패하였습니다. 재시도해주세요.");
					$('#myModal').modal();
				}
			},
		    error : function(error){
		    	$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
				$('#myModal').modal();
				window.location.href = '/home';
		    }
		});
	});
	$('.modal-footer > .pull-right').click(function() {
		return;
	});
});

//게시물 수정페이지로 이동
$(document).on('click', '.modifybtn', function(){
	   window.location.href = 'board/modify?boardNum='+boardNum;
});

//게시물 삭제
$(document).on('click', '.deletebtn', function(){
	
	$('.modal-body').html("게시물을 삭제하시겠습니까?");
	$('#checkModal').modal();
	$('.modal-footer > .pull-left').click(function() {
		$.ajax({
			url : /board/+boardNum,
			type : 'delete',
			dataType : 'json',
			success : function(data){
				if(data.code==1){
					$('.my-modal-body').html("게시물이 삭제되었습니다.");
					$('#myModal').modal();
					$('.center-block').click(function() {
						window.location.href = "/board";
					});
				}else {
					$('.my-modal-body').html("게시물이 삭제되지 않았습니다. 재시도 시도하여 주세요.");
					$('#myModal').modal();
					$('.center-block').click(function() {
						window.location.href = "/board";
					});
				}		
			},
		    error : function(error){
		    	$('.my-modal-body').html("오류가 발생하였습니다. 재시도 해주세요.");
				$('#myModal').modal();
				window.location.href = '/home';
		    }
		});
		
	});
	$('.modal-footer > .pull-right').click(function() {
		return;
	});
	
	
});


function fileDown(url){
	$.ajax({
		url : url,
		type : 'get',
		success : function(data) {
			if(data != ''){
				window.location.href = url;
			} else{
				$('.my-modal-body').html("파일이 존재하지 않습니다. 재시도하여 주세요.");
				$('#myModal').modal();
			}
		}	
	})
	
}

