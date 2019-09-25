<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	button.refundBtn {
	    margin-left: 5px;
	}
</style>
<h1>결제</h1>
<input type="hidden" name="searchWord" value="${loginUser.user_id }" />
<table class="table">
	<thead>
		<tr>
			<td>번호</td>
			<td>강의제목</td>
			<td>선생님</td>
			<td>가격</td>
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
<div class="modal fade" id="refundModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ModalCenterTitle">환불</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	해당 과목을 환불하시겠습니까?
      </div>
      <div class="modal-footer">
        <button type="button" id="okBtn" class="btn btn-primary">확인</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="paymentDetailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ModalCenterTitle">결제상세내역</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<table id="paymentDetailTable">
      		
      	</table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>
<script>
	var listBody = $('#listBody');
	var pagingArea = $('#pagingArea');
	var okBtn = $("#okBtn");
	var paymentDetailTable = $('#paymentDetailTable');
	
	function paging(page){
	      if(page<=0) return;
	      paymentList(page);
	};
	function parse(str){
		var str = ''+str;
		var y = str.substr(0,4);
		var m = str.substr(5,2);
		var d = str.substr(8,2);
		return new Date(y,m-1,d);
	};
	function paymentList(page){
		var searchWord = $('input[name=searchWord]').val();
		
		$.ajax({
			url:"${pageContext.request.contextPath}/pay/payList.do",
			method:"get",
			data:{"page":page,
				"searchWord":searchWord},
			dataType:"json",
			success:function(resp){
				let dataList = resp.dataList;
	            var trTags=[];
	            if(dataList.length == 0){
	            	var tr = $("<tr>")
           			.append($("<td>").text("결제내역이 없음").attr({"colspan":"7"}));
	            	trTags.push(tr);
	            }
	            $(dataList).each(function(idx, pay){
		            var status = "결제완료";
		            if(pay.status == 1){
		            	status="환불완료"; 
		            }
            		var tr = $("<tr>")
			               			.append($("<td>").text(pay.rnum))
			                        .append($("<td>").text(pay.lt_title))
			                        .append($("<td>").text(pay.user_name))
			                        .append($("<td>").text(pay.total))
			                        .append($("<td>").text(pay.payment_method_type))
			                        .append($("<td>").text(pay.approved_at))
			                        .append($("<td>").text(status));
			        if(pay.status==0){
			        	tr.append($('<button>').attr({"id" : pay.payment_no,
    						"class" : "btn btn-secondary detailBtn",
    						"data-toggle" : "modal",
							"data-target" : "#paymentDetailModal"
						}).text('상세보기'));
			        }
		    		var date = parse(pay.lt_start_date);
		    		var now = new Date();
		    		if(now<date&&pay.status==0){
		    			tr.append($('<button>').attr({
		    				"id" : pay.payment_no,
							"class" : "btn btn-secondary refundBtn",
							"data-toggle" : "modal",
							"data-target" : "#refundModal"
		    			}).text('환불'));
		    		}      
		     		trTags.push(tr);
	            });
	            listBody.html(trTags);
	            pagingArea.html(resp.pagingHTMLForBS);
			}
		});
	};
	
	paymentList(1);
	
	listBody.on('click', '.detailBtn', function(){
		let payment_no = $(this).prop('id');
		
		$.ajax({
			url:"${pageContext.request.contextPath}/pay/payView.do?what="+payment_no,
			method:"get",
			dataType:"json",
			success:function(resp){
				var subject = "";
				$(resp.categoryList).each(function(idx, category){
					if(idx < resp.categoryList.length-1){
						subject += category.ctgy_name + ", ";
					}else{
						subject += category.ctgy_name;
					}
				});
				var tags = [];
				tags.push($("<tr>")
						.append($('<th>').text("결제번호"))
						.append($('<td>').text(resp.payment_no)));
				tags.push($("<tr>")
							.append($('<th>').text("결제금액"))
							.append($('<td>').text(resp.total)));
				tags.push($("<tr>")
							.append($('<th>').text("결제방법"))
							.append($('<td>').text(resp.payment_method_type)));
				tags.push($("<tr>")
						.append($('<th>').text("결제날짜"))
						.append($('<td>').text(resp.approved_at)));
				tags.push($("<tr>")
						.append($('<th>').text("강의선생님"))
						.append($('<td>').text(resp.lt_writer+"("+resp.user_name+")")));
				tags.push($("<tr>")
						.append($('<th>').text("강의제목"))
						.append($('<td>').text(resp.lt_title)));
				tags.push($("<tr>")
						.append($('<th>').text("강의과목"))
						.append($('<td>').text(subject)));
				paymentDetailTable.html(tags);
			}
		});
	});
	listBody.on('click', '.refundBtn', function(){
		let payment_no = $(this).prop('id');
		okBtn.on('click', function(){
			location.href="${pageContext.request.contextPath }/kakaoPayRefund?what="+payment_no;
		});
	});
</script>