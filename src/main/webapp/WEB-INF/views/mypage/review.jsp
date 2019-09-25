<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>리뷰관리</h1> 
<input type="hidden" name="loginId" value="${loginUser.user_id }" />
<input type="hidden" name="userType" value="${loginUser.user_type }" />
<table class="table">
	<thead>
		<tr>
			<td>번호</td>
			<c:if test='${loginUser.user_type eq "tutee" }'>
				<td>선생님아이디</td>
			</c:if>
			<c:if test='${loginUser.user_type eq "tutor" }'>
				<td>학생아이디</td>
			</c:if>
			<td>내용</td>
			<td>별점</td>
			<td>작성일자</td>
		</tr>
	</thead>
	<tbody id="listBody">
		
	</tbody>
</table>
<div id='pagingArea'>
	
</div>

<div class="modal fade" id="reviewDetailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ModalCenterTitle">리뷰 상세보기</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<input type="hidden" id="rv_no" />
      	<form id="reviewInsertForm">
	        <input type="hidden" name="rv_writer" value="${loginUser.user_id }" />
	        <input type="hidden" name="rv_recipient" value="${lecture.lt_writer}" />
	        <textarea rows="5" cols="40" name="rv_content" class="form-control"></textarea>
	        <div class="form-group">
			    <label for="controlGrade">별점</label>
			    <select class="form-control" id="controlGrade" name="rv_grade">
			      <option value="1">★</option>
			      <option value="2">★★</option>
			      <option value="3">★★★</option>
			      <option value="4">★★★★</option>
			      <option value="5">★★★★★</option>
			    </select>
			</div>
		</form>
      </div>
      <div class="modal-footer" id="reviewFooter">
        <button type="button" id="reviewModifyBtn" class="btn btn-primary">수정</button>
        <button type="button" id="reviewDeleteBtn" class="btn btn-secondary" data-dismiss="modal">삭제</button>
      </div>
    </div>
  </div>
</div>
<script>
	var loginId = $('input[name=loginId]').val();
	var userType = $('input[name=userType]').val();
	
	var listBody = $('#listBody');
	var pagingArea = $('#pagingArea');
	var reviewDetailModal = $('#reviewDetailModal');
	var reviewModifyBtn = $('#reviewModifyBtn');
	var reviewDeleteBtn = $('#reviewDeleteBtn');
	var reviewInsertForm = $('#reviewInsertForm');
	
	function paging(page){
	      if(page<=0) return;
	      reviewList(page);
	};
	
	function reviewList(page){
		$.ajax({
			url:"${pageContext.request.contextPath}/review/reviewList",
			method:"get",
			data:{"page":page,
				"loginId":loginId,
				"userType":userType},
			dataType:"json",
			success:function(resp){
				let dataList = resp.dataList;
	            var trTags=[];
	            if(dataList.length == 0){
	            	var tr = $("<tr>")
           			.append($("<td>").text("작성 리뷰가 없음").attr({"colspan":"5"}));
	            	trTags.push(tr);
	            }
	            $(dataList).each(function(idx, review){
	            	var user_id = "";
	            	if(userType=="tutee"){
	            		user_id = review.rv_recipient;
	            	}else{
	            		user_id = review.rv_writer;
	            	}
	            	var len = 10;
	            	var lastTxt = "...";
	            	var content = review.rv_content;
	            	if(review.rv_content.length > len){
	            		content = review.rv_content.substr(0, len) + lastTxt;
	            	}
	            	var grade = "";
	            	for(var i=0;i<review.rv_grade;i++){
	            		grade += "★";
	            	}
	            	
	            	var tr = $("<tr>")
		               			.append($("<td>").text(review.rnum))
		                        .append($("<td>").text(user_id))
		                        .append($("<td>").text(content))
		                        .append($("<td>").text(grade))
		                        .append($("<td>").text(review.rv_date))
		                        .attr({"id":review.rv_no,
		                        	"data-toggle" : "modal",
									"data-target" : "#reviewDetailModal"});
             		trTags.push(tr);
	            });
	            listBody.html(trTags);
	            pagingArea.html(resp.pagingHTMLForBS);
			}
		});
	};
	
	reviewList();
	
	listBody.on('click', 'tr', function(){
		let rv_no = $(this).prop('id');
		$('#rv_no').val(rv_no);
		
		$.ajax({
			url:"${pageContext.request.contextPath}/review/reviewView?what="+rv_no,
			method:"get",
			dataType:"json",
			success:function(resp){
				$("textarea[name=rv_content]").val(resp.rv_content);
				var rv_grade = resp.rv_grade;
				
				$('select#controlGrade option[value=' + rv_grade + ']').attr('selected', 'selected');
				
				if(userType=="tutor"){
					$('textarea[name=rv_content]').attr('disabled', 'disabled');
					$('#controlGrade').attr('disabled', 'disabled');
					$('#reviewFooter').hide();
				}
			}
		});
	});
	
	reviewModifyBtn.on('click', function(){
		let rv_no = $('#rv_no').val();
		event.preventDefault();

		var data = reviewInsertForm.serialize();
		
		$.ajax({
			url:"${pageContext.request.contextPath}/review/reviewUpdate?what="+rv_no,
			method:"get",
			data: data,
			dataType:"json",
			success:function(resp){
				if(resp.success){
					reviewDetailModal.modal('hide');
					alert("수정에 성공했습니다.");
					reviewList();
				}else{
					alert(resp.message);
				}
			}
		});
		return false;
	});
	
	reviewDeleteBtn.on('click', function(){
		let rv_no = $('#rv_no').val();
		event.preventDefault();

		var data = reviewInsertForm.serialize();
		
		$.ajax({
			url:"${pageContext.request.contextPath}/review/reviewDelete?what="+rv_no,
			method:"get",
			data: data,
			dataType:"json",
			success:function(resp){
				if(resp.success){
					reviewDetailModal.modal('hide');
					alert("삭제에 성공했습니다.");
					reviewList();
				}else{
					alert(resp.message);
				}
			}
		});
		return false;
	});
	
	
</script>