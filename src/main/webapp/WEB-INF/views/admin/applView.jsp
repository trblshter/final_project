<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<h1>신청관리</h1>
<table class="table">
	<thead>
		<tr>
			<td>번호</td>
			<td>회원아이디</td>
			<td>강의제목</td>
			<td>선생님</td>
			<td>가격</td>
			<td>신청일자</td>
			<td>결제상태</td>
		</tr>
	</thead>
	<tbody id="listBody">
		
	</tbody>
</table>
<div id='pagingArea'>
	
</div>
<script>
	var listBody = $('#listBody');
	var pagingArea = $('#pagingArea');
	
	function paging(page){
	      if(page<=0) return;
	      applList(page);
	};
	
	function applList(page){
		$.ajax({
			url:"${pageContext.request.contextPath}/admin/applList",
			method:"get",
			data:{"page":page},
			dataType:"json",
			success:function(resp){
				let dataList = resp.dataList;
	            var trTags=[];
	            $(dataList).each(function(idx, appl){
	            	var payment_ok = "";
	            	if(appl.payment_ok==0){
	            		payment_ok = "미완료";
	            	}else{
	            		payment_ok = "완료";
	            	}
	            	var tr = $("<tr>")
			               			.append($("<td>").text(appl.rnum))
			               			.append($("<td>").text(appl.appl_user))
			                        .append($("<td>").text(appl.lt_title))
			                        .append($("<td>").text(appl.user_name))
			                        .append($("<td>").text(appl.lt_price))
			                        .append($("<td>").text(appl.appl_date))
			                        .append($("<td>").text(payment_ok));
	             	trTags.push(tr);
	            });
	            listBody.html(trTags);
	            pagingArea.html(resp.pagingHTMLForBS);
			}
		});
	};
	
	applList(1);
	
</script>