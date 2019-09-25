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
               <label for = "inputCategory3" class = "col-sm-2 col-form-label">팀</label>
               <div class = "col-sm-10">
                     <button class="btn btn-primary">검색</button>
               </div>
            </div>
            
            <div class = "form-group row">
               <label for = "inputTitle3" class = "col-sm-2 col-form-label">제목</label>
               <div class = "col-sm-10">
                  <input type = "text" class = "form-control" name="lt_title" id = "lt_title" style="width: 920px;">
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
               <div class = "col-sm-10">
                  <button type = "submit" class = "btn btn-primary" style="margin-left: 1010px; width: 100px;">등록</button>
               </div>
            </div>
            
         </form:form>
      </div>
      
   

