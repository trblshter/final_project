<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="list-group">
	<ul class="m-0 p-0">
		<c:forEach items="${menuList }" var="menu">
			<li><a href="<c:url value='${menu.menuURL }' />" class="list-group-item list-group-item-action" id="${menu.menuId}"><i class="icon"></i>${menu.menuText }<i class="fas fa-chevron-right"></i></a></li>
		</c:forEach>
	</ul>
</div>
<!-- 비밀번호 체크 Modal -->
<div class="modal fade bd-example-modal-sm" id="passModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">비밀번호 확인</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="passForm">
		  <input type="hidden" required name="user_id" value="${loginUser.user_id }">
		  <div class="form-group mx-sm-3 mb-2">
		    <label for="inputPassword" class="sr-only">비밀번호 확인</label>
		    <input type="password" required class="form-control" id="inputPassword" name="user_pass" placeholder="Password">
		  </div>
		  <div class="text-center">
		  <button type="submit" class="btn btn-primary">확인</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
          </div>
		</form>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
	var passModal = $("#passModal");
	var passForm = $("#passForm");
	$("#myinfo").on("click", function(event){
		event.preventDefault();
		passModal.modal("show");
		var attrHref = $(this).attr("href");
		passForm.attr("action", attrHref+"/passCheck");
		passForm.data("goPage", attrHref);
	});
	$("#Withdrawal").on("click", function(){
		event.preventDefault();
		passModal.modal("show");
		var attrHref = $(this).attr("href");
		passForm.attr("action", attrHref);
		passForm.data("goPage", "${pageContext.request.contextPath}");
	});
	$(passForm).on("submit", function(event){
		event.preventDefault();
		var goPage = $(this).data("goPage");
		var user_id = $(this).find("[name='user_id']").val();
		var user_pass = $(this).find("[name='user_pass']").val();
		$.ajax({
			url : $(this).attr("action"),
			method : "post",
			dataType : "json",
			data : {
				user_id : user_id,
				user_pass : user_pass
			},
			success : function(resp){
				if(resp.result){
					if(resp.removeResult){
						alert(resp.message);					
					}
					location.href = goPage;
				}else{
					alert(resp.message);					
				}
			},
			error : function(resp){
				console.log(resp.status + ", " + resp.responseText);
			}
		});
		
		return false;
	});
	
</script>