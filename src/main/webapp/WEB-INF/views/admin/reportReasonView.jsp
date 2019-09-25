<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="table">
	<thead>
		<tr>
			<th>번호</th>
			<th>신고자</th>
			<th>신고당한사람</th>
			<th>신고날짜</th>
		</tr>
	</thead>
	<tbody id="listBody">
		
	</tbody>
</table>
<div id='pagingArea'>
	
</div>
<div class="modal fade" id="reportReasonModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
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
      	<input type="hidden" name="rr_id" />
      	<input type="hidden" name="rr_reason" />
      	<table id="modalTable">
      		
      	</table>
      </div>
      <div class="modal-footer">
      	<span id="error"></span>
        <button type="button" id="addBtn" class="btn btn-primary">블랙리스트 추가</button>
        <button type="button" id="delBtn" class="btn btn-secondary" data-dismiss="modal">삭제</button>
      </div>
    </div>
  </div>
</div>
<script>
	var listBody = $('#listBody');
	var pagingArea = $('#pagingArea');
	var modalTable = $('#modalTable');
	var delBtn = $('#delBtn');
	var addBtn = $('#addBtn');
	var modal = $('#reportReasonModal');
	
	function paging(page){
	      if(page<=0) return;
	      reportList(page);
	};
	function reportList(page){
		$.ajax({
			url:"${pageContext.request.contextPath}/admin/reportReason",
			method:"get",
			data : {"page" : page},
			dataType:"json",
			success:function(resp){
				let dataList = resp.dataList;
	            var trTags=[];
	            $(dataList).each(function(idx, report){
	            	if(report.rr_ok==0){
	            		var tr = $("<tr>")
			               			.append($("<td>").text(report.rnum))
			                        .append($("<td>").text(report.rr_writer))
			                        .append($("<td>").text(report.rr_recipient))
			                        .append($("<td>").text(report.rr_date))
			                        .prop("id", report.rr_no)
			                        .attr("data-toggle", "modal")
			                        .attr("data-target", "#reportReasonModal")
              			trTags.push(tr);
	            	}
	            });
	            listBody.html(trTags);
	            pagingArea.html(resp.pagingHTMLForBS);
			}
		});
	};
	reportList();
	
	listBody.on('click', 'tr', function(){
		let rr_no = $(this).prop('id');
		$.ajax({
			url:"${pageContext.request.contextPath}/admin/reportReason/" + rr_no,
			method:"get",
			dataType:"json",
			success:function(resp){
				var tags = [];
				tags.push($("<tr>")
							.append($('<th>').text("신고자"))
							.append($('<td>').text(resp.rr_writer)));
				tags.push($("<tr>")
							.append($('<th>').text("신고당한 사람"))
							.append($('<td>').text(resp.rr_recipient)));
				tags.push($("<tr>")
							.append($('<th>').text("신고사유"))
							.append($('<td>').text(resp.rrs_name)));
				if(resp.rr_reason=="기타"){
					tags.push($("<tr>")
							.append($('<th>').text("기타"))
							.append($('<td>').text(resp.rr_content)));
				}
				tags.push($("<tr>")
						.append($('<th>').text("신고날짜"))
						.append($('<td>').text(resp.rr_date)));
				tags.push($("<tr>")
						.append($('<th>').text("이미지"))
						.append($('<td>')
								.append($("<img>")
                     		   		.attr({src:"data:image/*;base64,"+resp.rr_imageBase64})
                     		   		.css({width:"200px",heigh:"200px"}))))
				modalTable.html(tags);
				
				$('input[name=rr_id]').val(resp.rr_recipient);
				if(resp.rr_reason=="기타"){
					$('input[name=rr_reason]').val(resp.rr_content);
				}else{
					$('input[name=rr_reason]').val(resp.rrs_name);
				}
			}
		});
		
		delBtn.on('click', function(){
			event.preventDefault();
			var method = $('input[name=delMethod]').val();
			$.ajax({
				url:"${pageContext.request.contextPath}/admin/reportReason/" + rr_no,
				method:method,
				dataType:"json",
				success:function(resp){
					modal.modal('hide');
					reportList();
				}
			});
		});
		
		addBtn.on('click', function(){
			event.preventDefault();
			var id = $('input[name=rr_id]').val();
			var reason = $('input[name=rr_reason]').val();
			$.ajax({
				url:"${pageContext.request.contextPath}/admin/black",
				method:"post",
				data:{
					id : id,
					reason : reason
				},
				dataType:"json",
				success:function(resp){
					if(resp.success){
						modal.modal('hide');
						reportList();
					}else{
						$('#error').text(resp.message);
					}
				}
				
			});
		});
	});
	
</script>
