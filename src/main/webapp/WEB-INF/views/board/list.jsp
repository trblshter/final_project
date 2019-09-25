<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!--
<div id="content"> -->
<div class="container mt-5">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.3.4.1.js"></script>
<script>
	$(document).ready(function(){
		$("#btnWrite").on("click", function(){
			// 페이지 주소 변경(이동)
			if(${not empty sessionScope.loginUser }) {
				location.href = "${pageContext.request.contextPath}/board/write.do?type=${pagingVO.type}";				
			}else{
				confirm("로그인을 해주세요.");
			}
			
		});
	});
</script>

<h2 class="d-inline">	
	<c:choose>
		<c:when test="${pagingVO.type == 'NO' }">
			공지사항
		</c:when>
		<c:when test="${pagingVO.type == 'ID' }">
			정보자료
		</c:when>
		<c:when test="${pagingVO.type == 'LL' }"> 
			강의목록
		</c:when>
		<c:when test="${pagingVO.type == 'NE' }">
			신기술
		</c:when>
		<c:when test="${pagingVO.type == 'FA' }">
			질문게시판
		</c:when>
	</c:choose>
</h2>
<%-- 관리자 --%>
<c:if test="${sessionScope.loginUser.user_auth.toString() == 'A'.toString() }">
<!-- 	<button type="button" id="btnWrite">글쓰기</button> -->
	<input class="btn btn-primary float-right" type="button" id="btnWrite" value="글쓰기" />
</c:if>

<%-- 유저 --%>
<c:if test="${pagingVO.type != 'NO' && sessionScope.loginUser.user_auth.toString() == 'U'.toString() }">
<!-- 	<button type="button" id="btnWrite">글쓰기</button> -->
	<input class="btn btn-primary float-right" type="button" id="btnWrite" value="글쓰기" />
</c:if>
<table class="table mt-4">
	<thead>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>이름</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tbody id="listBody">
	
<%-- 	<c:forEach var="row" items="${list}"> --%>
<!-- 		<tr> -->
<%-- 			<td>${row.bo_no}</td> --%>
<%-- 			<td><a href="${pageContext.request.contextPath }/board/view.do?bo_no=${row.bo_no}">${row.bo_title}</a></td> --%>
<%-- 			<td>${row.bo_writer}</td> --%>
<!-- 			<td> -->
<%-- 				${row.bo_date} --%>
<!-- 				원하는 날짜형식으로 출력하기 위해 fmt태그 사용. -->
<%-- 			<fmt:formatDate value="${row.bo_date}" pattern="yyyy-MM-dd HH:mm:ss"/> --%>
<!-- 			</td> -->
<%-- 			<td>${row.bo_views}</td> --%>
<!-- 		</tr> -->
<%-- 	</c:forEach> --%>
	
	<!-- 검색과 페이징 -->
	
		<c:set var="list" value="${pagingVO.dataList }"/>
		<c:if test="${not empty list }">
			<c:forEach var="board" items="${list }">
				<tr id="${board.bo_no }">
					<td>${board.rnum }</td>
					<td>
<%-- 						<c:if test="${board.bo_depth gt 1 }"> --%>
<%-- 							<c:forEach begin="1" end="${board.bo_depth }" varStatus="vs"> --%>
<%-- 								&nbsp;&nbsp;${vs.last ? "re:" : "" } --%>
<%-- 							</c:forEach> --%>
<%-- 						</c:if> --%>
						${board.bo_title }
					</td>
					<td>${board.bo_writer }</td>
					<td>${board.bo_date }</td>
					<td>${board.bo_views }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list }">
			<tr>
				<td colspan="5" class="text-center"><!-- ㅁㅁ에 해당하는 글이 없습니다. -->
				조건에 맞는 게시글이 없습니다.
				</td>
			</tr>
		</c:if>
	</tbody>
	
	<!-- 검색 -->
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
<script type="text/javascript" src="<c:url value='/js/boardList.js'/>"></script>
<script type="text/javascript">
<!-- 검색  -->
	//boardList("${pageContext.request.contextPath }");
	function ${pagingVO.funcName}(page){
		if(page<=0) return;
		searchForm.find("input[name='page']").val(page);
// 		searchForm.find("input[name='type']").val(type);
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
// 							<c:if test="${board.bo_depth gt 1 }">
// 							<c:forEach begin="1" end="${board.bo_depth }" varStatus="vs">
// 								&nbsp;${vs.last ? "re:" : "" }
// 							</c:forEach>
// 							</c:if>
							var tr = $("<tr>").prop("id", board.bo_no);
// 							if(board.bo_depth > 1){
// 								board.bo_title = board.bo_title.replace(/^\s*/g, function(find){
// 									return find.replace(/\s/g, "&nbsp;&nbsp;")+"re:";
// 								});
// 							}
							tr.append(
								$("<td>").html(board.rnum)		
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
							$("<td>").attr({"colspan" : 5, "class" : "text-center"})
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
			//let regex = /tr_([0-9]+)/igm;
			//let id = $(this).attr("id");
			//let bo_no = regex.exec(id)[1];	
			let bo_no = $(this).attr("id");
			location.href="<c:url value='/board/view.do'/>?bo_no="+bo_no;
		}).css({cursor:"pointer"});
		
// 		$("#newBtn").on("click", function(){
// 			location.href="<c:url value='/board/boardInsert.do'/>";
// 		});
	});
</script>
</div>