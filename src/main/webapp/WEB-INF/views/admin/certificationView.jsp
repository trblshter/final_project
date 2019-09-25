<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	input#searchWord {
	    display: inline-block;
	    width: 200px;
	}
</style>
<h1>회원인증</h1>
<input class="form-control" type="text" id="searchWord" value="${pagingVO.searchWord }" />
<input class="btn btn-secondary" type="button" value="검색" id="searchBtn" />
<table class="table">
	<thead>
		<tr>
			<th>번호</th>
			<th>아이디</th>
			<th>이름</th>
			<th>경력</th>
			<th>학력</th>
		</tr>
	</thead>
	<tbody id="listBody">
	
	</tbody>
</table>
<div id='pagingArea'>
	
</div>
<form id="searchForm">
	<input type="hidden" name="searchWord" value="${pagingVO.searchWord }" />
	<input type="hidden" name="page" />
</form>
<div class="modal fade" id="authModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ModalCenterTitle">회원인증</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<table id="modalTable">
      		
      	</table>
      </div>
      <div class="modal-footer">
        <button type="button" id="authBtn" class="btn btn-primary">확인</button>
        <button type="button" id="authCancelBtn" data-toggle="modal" data-target="#authCancelModal" class="btn btn-secondary">거부</button>
<!--         <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button> -->
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="authCancelModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ModalCenterTitle">사유작성</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<form id="authCancelMsgForm">
	      	<input type="hidden" name="delMethod" value="delete" />
	      	<input type="hidden" name="msg_writer" value="${loginUser.user_id }" />
	      	<input type="hidden" name="msg_title" value="회원인증이 거부되었습니다." />
	      	<input type="hidden" name="msg_recipient" />
	      	<textarea class="form-control" name="msg_content" rows="5" cols="60" style="resize: none;">회원님은 ''의 사유로 인증이 거부되었습니다. 다시 인증해주시기 바랍니다.</textarea>
      	</form>
      </div>
      <div class="modal-footer">
        <button type="button" id="authCancelOkBtn" class="btn btn-primary">확인</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>

<script>
	var listBody = $('#listBody');
	var pagingArea = $('#pagingArea');
	var searchBtn = $('#searchBtn');
	var searchWord = $('#searchWord');
	var searchForm = $('#searchForm');
	var modal = $('#authModal');
	var authBtn = $('#authBtn');
	var modalTable = $('#modalTable');
	var authCancelBtn = $('#authCancelBtn');
	var authCancelModal = $('#authCancelModal');
	var authCancelMsgForm = $('#authCancelMsgForm');
	var authCancelOkBtn = $('#authCancelOkBtn');
	
	function paging(page){
	      if(page<=0) return;
	      blackList(page);
	};
	function certification(page){
		var searchWord = $('input[name=searchWord]').val();
		$.ajax({
			url:"${pageContext.request.contextPath}/admin/authList",
			method:"get",
			data:{"page":page,
				"searchWord":searchWord},
			dataType:"json",
			success:function(resp){
				let dataList = resp.dataList;
				var trTags=[];
				$(dataList).each(function(idx, user){
					var tr = $('<tr>')
								.append($('<td>').text(user.rnum))
								.append($('<td>').text(user.user_id))
								.append($('<td>').text(user.user_name))
								.append($('<td>').text(user.tt_career + "년"))
								.append($('<td>').text(user.tt_edu))
								.append($('<button>').attr({"id": user.user_id,
			                        						"class" : "btn btn-secondary certiBtn",
			                        						"data-toggle": "modal",
			                        						"data-target": "#authModal"
			                        						})
			                        					.text('인증'));
					trTags.push(tr);
				});
				listBody.html(trTags);
	            pagingArea.html(resp.pagingHTMLForBS);
			}
		});
	};
	
	searchBtn.on('click', function(){
		searchForm.find("input[name='searchWord']").val(searchWord.val());
		certification();
	});
	certification();
	
	listBody.on('click', '.certiBtn', function(){
		let user_id = $(this).prop('id');
		$('input[name=msg_recipient]').val(user_id);
		$.ajax({
			url:"${pageContext.request.contextPath}/admin/authView?who=" + user_id,
			method:"get",
			dataType:"json",
			success:function(resp){
				var tags = [];
				tags.push($("<tr>")
							.append($('<th>').text("아이디"))
							.append($('<td>').text(resp.user_id)));
				tags.push($("<tr>")
						.append($('<th>').text("이름"))
						.append($('<td>').text(resp.user_name)));
				tags.push($("<tr>")
						.append($('<th>').text("우편번호"))
						.append($('<td>').text(resp.user_zipcode)));
				tags.push($("<tr>")
						.append($('<th>').text("주소"))
						.append($('<td>').text(resp.user_addr1 + " " + resp.user_addr2)));
				tags.push($("<tr>")
						.append($('<th>').text("이메일"))
						.append($('<td>').text(resp.user_email)));
				tags.push($("<tr>")
						.append($('<th>').text("전화번호"))
						.append($('<td>').text(resp.user_tel)));
				tags.push($("<tr>")
						.append($('<th>').text("생일"))
						.append($('<td>').text(resp.user_birth)));
				tags.push($("<tr>")
						.append($('<th>').text("계좌은행"))
						.append($('<td>').text(resp.user_account_name)));
				tags.push($("<tr>")
						.append($('<th>').text("계좌번호"))
						.append($('<td>').text(resp.user_account_num)));
				tags.push($("<tr>")
							.append($('<th>').text("경력"))
							.append($('<td>').text(resp.tt_career + "년")));
				tags.push($("<tr>")
							.append($('<th>').text("학력"))
							.append($('<td>').text(resp.tt_edu)));
				tags.push($("<tr>")
						.append($('<th>').text("수료증"))
						.append($('<td>')
								.append($("<img>")
                     		   		.attr({src:"data:image/*;base64,"+resp.tt_ctr_imageBase64})
                     		   		.css({width:"200px",heigh:"200px"}))))
				modalTable.html(tags);
				
				$('input[name=tt_id]').val(resp.tt_id);
			}
		});
		
		authBtn.on('click', function(){
			event.preventDefault();
			$.ajax({
				url:"${pageContext.request.contextPath}/admin/authOk?who=" + user_id,
				method:"get",
				dataType:"json",
				success:function(resp){
					modal.modal('hide');
					certification();
				}
			});
			return false;
		});
		
		authCancelBtn.on('click',function(){
			modal.modal('hide');
		});
		
		authCancelOkBtn.on('click', function(){
			authCancelMsgForm.submit();
		});
		
		authCancelMsgForm.on('submit', function(){
			event.preventDefault();
			
			var queryString = $(this).serialize();
			
			$.ajax({
				url:"${pageContext.request.contextPath}/admin/authCancel",
				data: queryString,
				method:"get",
		        dataType : "json",
				success: function(resp){
					if(resp.success){
						authCancelMsgForm[0].reset();
						authCancelModal.modal('hide');
					}else{
						alert(resp.message);
					}
				}
			});
			return false;
		});
	});
	
</script>