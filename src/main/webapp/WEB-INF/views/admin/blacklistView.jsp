<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	input#searchWord {
	    display: inline-block;
	    width: 200px;
	}
</style>
<input type="text" class="form-control" id="searchWord" value="${pagingVO.searchWord }" />
<input type="button" class="btn btn-secondary" value="검색" id="searchBtn" />
<table class="table">
	<thead>
		<tr>
			<th>번호</th>
			<th>아이디</th>
			<th>날짜</th>
			<th>사유</th>
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
<div class="modal fade" id="blackDelModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ModalCenterTitle">신고관리</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<input type="hidden" name="delMethod" value="delete" />
      	<input type="hidden" name="bl_id" />
      	해당 회원을 블랙에서 해제하시겠습니까?
      </div>
      <div class="modal-footer">
      	<span id="error"></span>
        <button type="button" id="delBtn" class="btn btn-primary">확인</button>
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
	var modal = $('#blackDelModal');
	var delBtn = $('#delBtn');
	
	function paging(page){
	      if(page<=0) return;
	      blackList(page);
	};
	function blackList(page){
		var searchWord = $('input[name=searchWord]').val();
		$.ajax({
			url:"${pageContext.request.contextPath}/admin/black",
			method:"get",
			data:{"page":page,
				"searchWord":searchWord},
			dataType:"json",
			success:function(resp){
				let dataList = resp.dataList;
				var trTags=[];
				$(dataList).each(function(idx, black){
					var tr = $('<tr>')
								.append($('<td>').text(black.rnum))
								.append($('<td>').text(black.bl_id))
								.append($('<td>').text(black.bl_date))
								.append($('<td>').text(black.bl_reason))
								.prop("id", black.bl_id)
								.attr("data-toggle", "modal")
			                    .attr("data-target", "#blackDelModal")
					trTags.push(tr);
				});
				listBody.html(trTags);
	            pagingArea.html(resp.pagingHTMLForBS);
			}
		});
	};
	
	searchBtn.on('click', function(){
		searchForm.find("input[name='searchWord']").val(searchWord.val());
		blackList();
	});
	blackList();
	
	listBody.on('click', 'tr', function(){
		let bl_id = $(this).prop('id');
		
		delBtn.on('click', function(){
			event.preventDefault();
			var method = $('input[name=delMethod]').val();
			$.ajax({
				url:"${pageContext.request.contextPath}/admin/black/" + bl_id,
				method:method,
				dataType:"json",
				success:function(resp){
					modal.modal('hide');
					blackList();
				}
			});
		});
	});
</script>