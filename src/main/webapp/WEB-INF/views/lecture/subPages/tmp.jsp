<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	div.overWrap{
		margin : 5%;
	}
	
	div.left-float{
		display : inline-block;
	}
	

</style>

<div class="overWrap">
	<h4>강의 정보</h4>
	<div style="width : 15%; height : 20%" class="left-float profile">
		<img alt="" src="${pageContext.request.contextPath}/image/star.png">
	</div>
	<div style=" width : 40%; margin-left : 3%" class="left-float">
		<table class="table table-striped" style="margin-top: 1%; margin-bottom: 1%">
			<thead class="thead-light">
				<tr>
					<th style="width: 20%; text-align: center;">과목</th>
					<th>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th style="text-align: center;">강의 제목</th>
					<td >${lecturetutor.lt_date}</td>
				</tr>
				<tr>
					<th style="text-align: center;">튜터</th>
					<td>4</td>
				</tr>
				<tr>
					<th style="text-align: center;">수강 기간</th>
					<td>${lecturetutor.lt_title}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<br><br><br>
	<h4>현재 수강생</h4>
	<div class="left-float">
		<img alt="" src="${pageContext.request.contextPath}/image/star.png">
		<p>inew</p>
	</div>
	<div class="left-float">
		<img alt="" src="${pageContext.request.contextPath}/image/star.png">
		<p>inew</p>
	</div>
	<div class="left-float">
		<img alt="" src="${pageContext.request.contextPath}/image/star.png">
		<p>inew</p>
	</div>
	<br>
	<div class="left-float">
		<h4>강의 진행률</h4>
	</div>
	<div class="left-float">
		<h4>자료 공유</h4>
	</div>
	
	
</div>

<script type="text/javascript">
</script>