<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>신청내역</h1>
<input type="hidden" name="searchWord" value="${loginUser.user_id }" />
<table class="table">
	<thead>
		<tr>
			<td>번호</td>
			<td>강의제목</td>
			<td>선생님</td>
			<td>가격</td>
			<td>신청일자</td>
			<td>결제</td>
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
		var searchWord = $('input[name=searchWord]').val();
		
		$.ajax({
			url:"${pageContext.request.contextPath}/appl/applList.do",
			method:"get",
			data:{"page":page,
				"searchWord":searchWord},
			dataType:"json",
			success:function(resp){
				let dataList = resp.dataList;
	            var trTags=[];
	            if(dataList.length == 0){
	            	var tr = $("<tr>")
           			.append($("<td>").text("신청내역이 없음").attr({"colspan":"6"}));
	            	trTags.push(tr);
	            }
	            $(dataList).each(function(idx, appl){
	            	if(appl.payment_ok==0){
	            		var tr = $("<tr>")
			               			.append($("<td>").text(appl.rnum))
			                        .append($("<td>").text(appl.lt_title))
			                        .append($("<td>").text(appl.user_name))
			                        .append($("<td>").text(appl.lt_price))
			                        .append($("<td>").text(appl.appl_date));
	            		
			                        
			                        
	            		if(appl.lt_completed==1){
	            			tr.append($('<button>').attr({
		            				"id" : appl.appl_no,
									"class" : "btn btn-secondary buyBtn"
								})
	        					.text('결제'));	
	            		}else if(appl.lt_completed==0){
	            			tr.append($('<button>').attr({
		            				"id" : appl.appl_no,
									"class" : "btn btn-secondary buyBtn",
									"disabled" : true
								})
	        					.text('결제'));
	            		}
	            		
	            		
// 	            		if(check){
// 	            			tr.append($('<button>').attr({"id" : appl.appl_no,
//     							"class" : "btn btn-secondary notAvailableBtn"})
//             					.text('결제'));
// 	            		}else{
// 	            			tr.append($('<button>').attr({
// 	            				"id" : appl.appl_no,
//     							"class" : "btn btn-secondary buyBtn"
//     							})
//             					.text('결제'));
// 	            		}
// 	            		var check = false;
// 	            		if(appl.lt_recruit==1){
// 	            			if(appl.team_member1!=null) check = true;
// 	            		}else if(appl.lt_recruit==2){
// 	            			if(appl.team_member1!=null&&appl.team_member2!=null) check = true;
// 	            		}else if(appl.lt_recruit==3){
// 	            			if(appl.team_member1!=null&&appl.team_member2!=null&&appl.team_member3!=null) check = true;
// 	            		}else if(appl.lt_recruit==4){
// 	            			if(appl.team_member1!=null&&appl.team_member2!=null&&appl.team_member3!=null&&appl.team_member4!=null) check = true;
// 	            		}
	            		
// 	            		if(check){
// 	            			tr.append($('<button>').attr({"id" : appl.appl_no,
//     							"class" : "btn btn-secondary notAvailableBtn"})
//             					.text('결제'));
// 	            		}else{
// 	            			tr.append($('<button>').attr({"id" : appl.appl_no,
//     							"class" : "btn btn-secondary buyBtn"})
//             					.text('결제'));
// 	            		}
// 	            		if(appl.team_member1!=null&&appl.team_member2!=null&&
// 	            				appl.team_member3!=null&&appl.team_member4!=null){
// 	            			tr.append($('<button>').attr({"id" : appl.appl_no,
//     							"class" : "notAvailableBtn"})
//             					.text('결제'));
// 	            		}else{
// 	            			tr.append($('<button>').attr({"id" : appl.appl_no,
//     							"class" : "buyBtn"})
//             					.text('결제'));
// 	            		}
			                        
	             		trTags.push(tr);
	            	}
	            });
	            listBody.html(trTags);
	            pagingArea.html(resp.pagingHTMLForBS);
			}
		});
	};
	
	applList(1);
	
	function popup(appl_no){
        var url = "${pageContext.request.contextPath}/kakaoPay?what="+appl_no;
        var name = "pop up";
        var option = "width = 500, height = 500, top = 100, left = 200, location = no"
        kakaoWindow = window.open(url, name, option);
        
        kakaoInterval = window.setInterval(function() {
            try {
                // 창이 꺼졌는지 판단
                if( kakaoWindow == null || kakaoWindow.closed ) {
                    applList(1);
                    window.clearInterval(kakaoInterval);
                    kakaoWindow = null;
                }
            } catch (e) { }
        }, 1000);
    }
	
	listBody.on('click', '.buyBtn', function(){
		let appl_no = $(this).prop('id');
		popup(appl_no);
	});
// 	listBody.on('click', '.notAvailableBtn', function(){
// 		alert("이미 꽉 찼습니다.");
// 	});
	
	
	
</script>