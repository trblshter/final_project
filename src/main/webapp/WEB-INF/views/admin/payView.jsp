<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<h1>결제관리</h1>
<table class="table">
	<thead>
		<tr>
			<td>번호</td>
			<td>회원아이디</td>
			<td>강의제목</td>
			<td>강의선생님</td>
			<td>가격</td>
			<td>부가가치세</td>
			<td>결제방법</td>
			<td>결제일자</td>
			<td>상태</td>
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
	      paymentList(page);
	};
	function parse(str){
		var y = str.substr(0,4);
		var m = str.substr(5,2);
		var d = str.substr(8,2);
		return new Date(y,m-1,d);
	};
	function paymentList(page){
		$.ajax({
			url:"${pageContext.request.contextPath}/admin/payList",
			method:"get",
			data:{"page":page},
			dataType:"json",
			success:function(resp){
				let dataList = resp.dataList;
	            var trTags=[];
	            $(dataList).each(function(idx, pay){
		            var status = "결제완료";
		            if(pay.status == 1){
		            	status="환불완료"; 
		            }
            		var tr = $("<tr>")
			               			.append($("<td>").text(pay.rnum))
			               			.append($("<td>").text(pay.partner_user_id))
			                        .append($("<td>").text(pay.lt_title))
			                        .append($("<td>").text(pay.lt_writer))
			                        .append($("<td>").text(pay.total))
			                        .append($("<td>").text(pay.vat))
			                        .append($("<td>").text(pay.payment_method_type))
			                        .append($("<td>").text(pay.approved_at))
			                        .append($("<td>").text(status));
		     		trTags.push(tr);
	            });
	            listBody.html(trTags);
	            pagingArea.html(resp.pagingHTMLForBS);
			}
		});
	};
	
	paymentList(1);
	
</script>
