<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<link rel="stylesheet" href="<c:url value='/vendor/css/bootstrap.min.css'/>">
<link rel="stylesheet" href='<c:url value="/vendor/css/bootstrap-datetimepicker.min.css"/>'/>
<script src="<c:url value='/vendor/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/vendor/js/moment.min.js'/>"></script>
<script src="<c:url value='/vendor/js/bootstrap-datetimepicker.min.js'/>"></script>


<style>
   input[type=radio] {
      margin-right: 10px;
   }
</style>
<script type="text/javascript" src="<c:url value='/js/ckeditor/ckeditor.js' />"></script>
   <c:if test="${not empty message }">
      <div class="alert alert-danger alert-dismissible" role="alert">
         <button type="button" class="close" data-dismiss="alert"
            aria-label="Close">
            <span aria-hidden="true">&times;</span>
         </button>
         <strong>${message }</strong>
      </div>
   </c:if>
   
   <%-- <c:choose>
      <c:when test="${not empty board.bo_no and board.bo_no gt 0}">
         <h4>${board.bo_no }번글 수정</h4>
      </c:when>
      <c:otherwise>
         <h4>새글 쓰기</h4>
      </c:otherwise>
   </c:choose> --%>
   
   <div class = "container" style="margin-top: 50px;">
         <form:form commandName="lecture" id="lectureForm" method="post" enctype="multipart/form-data">
            <input type="hidden" name="lt_writer" value="${loginUser.user_id }"/>
            
            <div class = "form-group row">
               <label for = "inputCategory3" class = "col-sm-2 col-form-label">과목</label>
               <div class = "col-sm-10">
                     <select class="form-control" id="category" name="category">
                  <option selected="selected">자바</option>
                  <option>C++</option>
                  <option>C#</option>
                  <option>Phython</option>
                  <option>LINUX</option>
                  <option>HTML</option>
               </select>
               </div>
            </div>
            
            <div class = "form-group row">
               <label for = "inputTitle3" class = "col-sm-2 col-form-label">제목</label>
               <div class = "col-sm-10">
                  <input type = "text" class = "form-control" name="lt_title" id = "lt_title" style="width: 920px;">
               </div>
            </div>
            
            <div class = "form-group row">
               <label for = "inputPrice3" class = "col-sm-2 col-form-label">가격</label>
               <div class = "col-sm-10">
                  <input type = "text" class = "form-control" name="lt_price" id = "lt_price" style="width: 920px;">
               </div>
            </div>
            <c:if test="${empty team }">
				<div class="form-group row">
					<label for="inputCategory3" class="col-sm-2 col-form-label">모집인원</label>
					<div class="col-sm-10">
						<select class="form-control" id="lt_recruit" name="lt_recruit">
							<option selected="selected">선택</option>
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
						</select>
					</div>
				</div>
			</c:if>
            <c:if test="${not empty team}">
				<input type="hidden" value="${team.room.size() } " name="lt_recruit">	                
				<input type="hidden" value="1" name="lt_completed">	                
            </c:if>
             <div class = "form-group row">
               <label for = "inputPrice3" class = "col-sm-2 col-form-label">강의 회차</label>
               <div class = "col-sm-10">
                  <input type = "text" class = "form-control" name="lt_turn" id = "lt_turn" style="width: 90px;">
               </div>
            </div>
            
            <div class = "form-group row">
               <label for = "inputContent3" class = "col-sm-2 col-form-label">내용</label>
               <div class = "col-sm-10">
                 <textarea name="lt_content" id="lt_content"
                  class="form-control" rows= "5"></textarea>
               </div>
            </div>
            
            <div class = "form-group row">
               <label for = "inputImage3" class = "col-sm-2 col-form-label" style="margin-right: 14px;">이미지</label>
               <div style="padding: 2px;">
                 <input type="file" class="form-control" name="lt_img" style="width: 920px;"/>
               </div>
            </div>
            
            <div class = "form-group row">
               <label for = "inputStartDate3" class = "col-sm-2 col-form-label">시작하는 날짜</label>
               <div class = "col-sm-10">
                 <input type="date" class="form-control" name="lt_start_date" style="width: 920px;"/>
               </div>
            </div>
            
             <div class = "form-group row">
               <label for = "inputEndDate3" class = "col-sm-2 col-form-label">끝나는 날짜</label>
               <div class = "col-sm-10">
                 <input type="date" class="form-control" name="lt_end_date" style="width: 920px;"/>
               </div>
            </div>
            
            <div class = "form-group row" >
               <label for = "inputDay3" class = "col-sm-2 col-form-label" style="margin-right: 13px;">요일</label>
               <div class="form-control" style="width: auto;">
                 <input type="checkbox" name="lw_day" style="vertical-align: middle;" value="1"/>&nbsp;월요일
               </div>
               <div class="form-control" style="width: auto; margin-left: 5px;">
                 <input type="checkbox" name="lw_day" style="vertical-align: middle;" value="2"/>&nbsp;화요일
               </div>  
               <div class="form-control" style="width: auto; margin-left: 5px;">
                 <input type="checkbox" name="lw_day" style="vertical-align: middle;" value="3"/>&nbsp;수요일
               </div>
               <div class="form-control" style="width: auto; margin-left: 5px;">
                 <input type="checkbox" name="lw_day" style="vertical-align: middle;" value="4"/>&nbsp;목요일
               </div>
               <div class="form-control" style="width: auto; margin-left: 5px;">
                 <input type="checkbox" name="lw_day" style="vertical-align: middle;" value="5"/>&nbsp;금요일
               </div>
               <div class="form-control" style="width: auto; margin-left: 5px;">
                 <input type="checkbox" name="lw_day" style="vertical-align: middle;" value="6"/>&nbsp;토요일
               </div>
               <div class="form-control" style="width: auto; margin-left: 5px;">
                 <input type="checkbox" name="lw_day" style="vertical-align: middle;" value="7"/>&nbsp;일요일
               </div>
            </div>
            
            <div class = "form-group row" style="height: 34px;">
               <label for = "inputStartTime3" class = "col-sm-2 col-form-label" style="margin-right: -15px;">시작 시간</label>
               <div class = "col-sm-10">
                   <div class="container">
                   <div class="row">
                       <div class='col-sm-6'>
                           <div class="form-group">
                               <div class='input-group date' id='datetimepicker1'>
                                   <input type='text' name="lw_starttime" class="form-control" style="width: 520px;"/>
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-time"></span>
                                   </span>
                               </div>
                           </div>
                       </div>
                       <script type="text/javascript">
                           $(function () {
                               $('#datetimepicker1').datetimepicker({
                                   format: 'LT'
                               });
                           });
                       </script>
                   </div>
               </div>
               </div>
            </div>
            
            <div class = "form-group row">
               <label for = "inputEndTime3" class = "col-sm-2 col-form-label" style="margin-right: -15px;">끝나는 시간</label>
               <div class = "col-sm-10">
                   <div class="container">
                   <div class="row">
                       <div class='col-sm-6'>
                           <div class="form-group">
                               <div class='input-group date' id='datetimepicker2'>
                                   <input type='text' name="lw_endtime" class="form-control" style="width: 520px;"/>
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-time"></span>
                                   </span>
                               </div>
                           </div>
                       </div>
                       <script type="text/javascript">
                           $(function () {
                               $('#datetimepicker2').datetimepicker({
                                   format: 'LT'
                               });
                           });
                         </script>
                   </div>
               </div>
               </div>
            </div>
            
            
            <div class = "form-group row">
               <div class = "col-sm-10">
                  <button type = "submit" class = "btn btn-primary" style="margin-left: 1010px; width: 100px;">등록</button>
               </div>
            </div>
            
         </form:form>
      </div>
      
   <c:url var="uploadImageUrl" value='/board/uploadImage.do'>
      <c:param name="type" value="Images"></c:param>
   </c:url>
   <script type="text/javascript">
      CKEDITOR
            .replace(
                  'lt_content',
                  {
                     extraPlugins : 'image',
                     filebrowserImageUploadUrl : "${uploadImageUrl}"
                  });

      var boardForm = $("#boardForm");
      // 삭제할 첨부파일들에 대한 정보를 서버로 전송하기 위해 동적 UI 추가
      var delFileInput = "<input type='text' name='delFiles' value='%V' />";
      $(".delFileBtn").on("click", function() {
         var att_no = $(this).data("attno");
         boardForm.prepend(delFileInput.replace("%V", att_no));
         console.log(att_no);
         $(this).closest("span").remove();
      });
      
      /* $(function () {
            $('#datetimepicker1').datetimepicker({
               format : 'LT'
            });
        }); */
   </script>

