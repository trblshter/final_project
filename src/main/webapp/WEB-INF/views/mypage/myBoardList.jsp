<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.3.4.1.js"></script>
<h1>작성글 관리</h1>

<c:if test="${not empty sessionscope.loninUser.user_id }">
	<table  class="table">
		<thead>
			<tr>
				<th>게시판 이름</th>
				<th>제목</th>
				<th>이름</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
		</thead>
	</table>
</c:if>

<table class="table">
	<thead>
		<tr>
			<th>게시판 이름</th>
			<th>제목</th>
			<th>이름</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tbody id="listBody">
	
	<!-- 검색과 페이징 -->
	
		<c:set var="list" value="${pagingVO.dataList }"/>
		<c:if test="${not empty list }">
			<c:forEach var="board" items="${list }">
				<tr id="${board.bo_no }">
				
					<td>
						<c:choose>
							<c:when test="${fn:indexOf(board.bo_no,'NO') eq 0 }">
								공지사항
							</c:when>
							<c:when test="${fn:indexOf(board.bo_no,'ID') eq 0 }">
								정보자료
							</c:when>
							<c:when test="${fn:indexOf(board.bo_no,'LL') eq 0 }">
								강의목록
							</c:when>
							<c:when test="${fn:indexOf(board.bo_no,'NE') eq 0 }">
								신기술
							</c:when>
							<c:when test="${fn:indexOf(board.bo_no,'FA') eq 0 }">
								질문
							</c:when>
						</c:choose>
					</td>
					<td>${board.bo_title }</td>
					<td>${board.bo_writer }</td>
					<td>${board.bo_date }</td>
					<td>${board.bo_views }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list }">
			<tr>
				<td colspan="5" class="text-center" onclick="event.cancelBubble=true"><!-- 클릭 이벤트 막기 -->
					<label>작성된 게시글이 없습니다.</label>
				</td>
			</tr>
		</c:if>
	</tbody>
		
	<tfoot>
		<tr>
			<td class="form-inline text-center" colspan="5" style="display:table-cell;">
				<select id="searchType" class="form-control">
					<option value="">전체</option>
					<option value="title" ${pagingVO.searchType eq 'title' ? 'selected' : '' }>제목</option>
					<option value="writer" ${pagingVO.searchType eq 'writer' ? 'selected' : '' }>작성자</option>
					<option value="content" ${pagingVO.searchType eq 'content' ? 'selected' : '' }>내용</option>
				</select>
				<input class="form-control" type="text" id="searchWord" value="${pagingVO.searchWord }"/>
				<input class="btn btn-primary" type="button" value="검색" id="searchBtn"/>
				<div id="pagingArea" class="text-conter">
					${pagingVO.pagingHTMLForBS }
				</div>
			</td>
		</tr>
	</tfoot>
	
</table>
<form id="searchForm">
	<input type="hidden" name="searchType" value="${pagingVO.searchType }"/>
	<input type="hidden" name="searchWord" value="${pagingVO.searchWord }"/>
	<input type="hidden" name="page"/>
<!-- 	<input type="hidden" name="type"/> -->
</form>
<!-- js파일로 빼놓은 Ajax 불러오기. -->
<%-- <script type="text/javascript" src="<c:url value='/js/boardList.js'/>"></script> --%>
<script type="text/javascript">
<!-- 검색  -->
	//boardList("${pageContext.request.contextPath }");
	function ${pagingVO.funcName}(page){
		if(page<=0) return;
		searchForm.find("input[name='page']").val(page);
		searchForm.submit();
		searchForm.find("input[name='page']").val("");
	}
	
	$(function(){
		searchForm = $("#searchForm");
		var searchType = $("#searchType");
		var searchWord = $("#searchWord");
		$("#searchBtn").on("click", function(){
			searchForm.find("input[name='searchType']").val(searchType.val());
			searchForm.find("input[name='searchWord']").val(searchWord.val());
			searchForm.submit();
		});
		
		var listBody = $("tbody#listBody");
		var pagingArea = $("#pagingArea");
		
		searchForm.on("submit", function(event){
			var queryString = $(this).serialize();
			event.preventDefault();
			$.ajax({
				data : queryString,
				dataType : "json",
				success : function(resp) {
					let boardList = resp.dataList;
					var trTags = [];
					if(boardList && boardList.length > 0){
						$(boardList).each(function(idx, board){
							var tr = $("<tr>").prop("id", board.bo_no);
							var boardType = "";
							switch(board.bo_no.substring(0,2)){ // 게시판 번호의 앞글자 2자리 // 0부터 2번 전까지
								case "NO": boardType = "공지사항"; break; 
								case "ID": boardType = "정보자료"; break; 
								case "LL": boardType = "강의목록"; break; 
								case "NE": boardType = "신기술"; break; 
								case "FA": boardType = "질문"; break; 
							}
							tr.append(
								$("<td>").html(boardType)		
								, $("<td>").html(board.bo_title)		
								, $("<td>").html(board.bo_writer)		
								, $("<td>").html(board.bo_date)		
								, $("<td>").html(board.bo_views)		
							);
							trTags.push(tr);
						});
					}else{
						trTags.push(
						  $("<tr>").append(
							$("<td>").attr({"colspan" : 5, "class" : "text-center", "onclick" : "event.cancelBubble=true"})
								     .text("조건에 맞는 게시글이 없습니다.")
						  )
						);
					}
					listBody.html(trTags);
					pagingArea.html(resp.pagingHTMLForBS);
				},
				error : function(errorResp) {
					console.log(errorResp.status + ", "
							+ errorResp.responseText)
				}
			});
			return false;
		});
		
		listBody.on("click", "tr", function(){
			let bo_no = $(this).attr("id");
			location.href="<c:url value='/board/view.do'/>?bo_no="+bo_no;
		}).css({cursor:"pointer"});
	});
</script>