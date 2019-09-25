<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>codemirrorTest</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.3.4.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/bootstrap-4.3.1/js/bootstrap.bundle.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/bootstrap-4.3.1/css/bootstrap.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/fontawesome/css/all.css"/>

<script src="${pageContext.request.contextPath }/codemirror/lib/codemirror.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/edit/closebrackets.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/edit/closetag.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/edit/continuelist.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/edit/matchbrackets.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/edit/matchtags.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/edit/trailingspace.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/hint/show-hint.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/hint/javascript-hint.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/hint/xml-hint.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/hint/html-hint.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/hint/css-hint.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/addon/hint/anyword-hint.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/codemirror/lib/codemirror.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/codemirror/theme/blackboard.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/codemirror/addon/hint/show-hint.css">
<script src="${pageContext.request.contextPath }/codemirror/mode/xml/xml.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/mode/javascript/javascript.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/mode/python/python.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/mode/css/css.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/mode/htmlmixed/htmlmixed.js"></script>
<script src="${pageContext.request.contextPath }/codemirror/mode/clike/clike.js"></script>
<style>
	#compiler {width:100%; height:500px; position: relative;}
	#compiler .CodeMirror {height:100%;}
	#result,#preview {background:#ddd; height:500px; position: relative;}
	#contWrap{ position: absolute; right: 0; top: 0; z-index: 999;}
</style>
</head>
<body> 
<c:set var="userId" value="bbbb" />
<div id="compiler">
	<form method="post" action='<c:url value='/compiler' />'>
		<input type="hidden" name="mode" />
		<input type="hidden" name="userId" value="${userId }" />
		<div id="conWrap" class="text-right">
			<select name="editorType" id="editorType">
				<%-- OPTION 비동기 요청 --%>
			</select>
			<button type="submit" class="btn btn-primary" id="editorSend">출력</button> 
		</div>
		<div id="compiler">
			<textarea id="editor" name="editor">
public class <c:out value="${userId }" />{
	public static void main(String[] args){
		
	}
}
<%-- codemirror --%>
			</textarea>
		</div>
	</form>
</div>
<div id="result">
	<h4>컴파일 결과</h4>
	<div id="msgConsole">
	
	</div>
</div>
<iframe id=preview></iframe>
<script>
	$('#preview').hide();
	editorType = $("#editorType");
	function getEditorType(){
		$.ajax({
			url : "${pageContext.request.contextPath}/compiler/editorType",
			dataType : "json",
			method : "get",
			success : function(resp){
				if(resp.result){
					var categoryList = resp.categoryList;
					var text = [];
					$(categoryList).each(function(idx, type){
						if(type.ctgy_name != "LINUX"){
							let option = $("<option>").attr("value",type.ctgy_type).text(type.ctgy_name);
							text.push(option);
						}
					});
					editorType.html(text);
				}else{
					alert(resp.message);
				}
			},
			error : function(resp){
				console.log(resp.status + ", " + resp.responseText);
			}
		});
	}
	getEditorType();
	
	// codemirror 적용
	var textarea = document.getElementById('editor');
	var editor = CodeMirror.fromTextArea(textarea, {
		// 줄 번호를 표시할지 여부 
		lineNumbers : true,
		// 줄 바꿈을 사용할지 여부 
		lineWrapping : true,
		// 일치하는 중괄호를 강조 표시할지 여부 
		matchBrackets : true,
		// 태그가 자동으로 닫히도록할지 여부 
		autoCloseTags : true,
		// 브라켓이 자동으로 닫히도록할지 여부 
		autoCloseBrackets : true,
		// 검색 도구, CTRL + F (찾기), CTRL + SHIFT + F (바꾸기), CTRL + SHIFT + R (모두 바꾸기), CTRL + G (다음 찾기), CTRL + SHIFT + G ( 이전 검색) 
		enableSearchTools : true,
		// 코드 접기 사용 여부 ( 'lineNumbers'를 'true'로 설정해야 함) 
		enableCodeFolding : true,
		// 코드 형식 사용 여부 
		enableCodeFormatting : true,
		// 편집기가로드 될 때 코드를 자동으로 포맷해야하는지 여부 
		autoFormatOnStart : true,
		// 소스 뷰가 열릴 때마다 코드를 자동으로 포맷해야하는지 여부 
		autoFormatOnModeChange : true,
		// 주석 처리 가 해제 된 코드를 자동으로 형식화할지 여부 
		autoFormatOnUncomment : true,
		// (css, xml, javascript), html을 포함하는 PHP 모드의 경우 'application / x-httpd-php'또는 자바 스크립트 전용 
		// 모드 를 사용하는 경우 'text / javascript'를 포함하여 HTML의 언어 별 모드 'htmlmixed'를 정의하십시오 . htmlmixed ',
		// 후행 공백 표시 여부 
		showTrailingSpace : true,
		// 현재 단어 / 선택과 일치하는 항목을 모두 강조 표시할지 여부
		highlightMatches : true,
		// 툴바에 showAutoCompleteButton 버튼을 표시할지 여부 
		showAutoCompleteButton : true,
		// 현재 활성화 된 선을 강조 표시할지 여부 
		ActiveActive : true,
		// 이것을 사용하고자하는 테마 (codemirror 테마) 
		theme : "blackboard",
		// 모드 설정 -> text/x-csrc (C), text/x-c++src (C++), text/x-java (Java), text/x-csharp (C#), text/html
		mode : "text/x-java",
		extraKeys : {
			"Ctrl-Space" : "autocomplete"
		},
		val : textarea.value
	});
	
	$('#editorType').on('change', function(){
		var selectedVal = $('#editorType option:selected').val();
		var flag = true;
		
		if(selectedVal == "java"){
			editor.setValue("public class ${userId } {\n	public static void main(String[] args){\n\n	}\n}");
			editor.setOption("mode", "text/x-java");
		}else if(selectedVal == "cpp"){
			editor.setValue("#include <iostream>\nusing namespace std;\n\nint main() {\n	// your code goes here\n	return 0;\n}");
			editor.setOption("mode", "text/x-c++src");
		}else if(selectedVal == "c"){
			editor.setValue("#include <stdio.h>\n\nint main() {\n	// your code goes here\n	return 0;\n}");
			editor.setOption("mode", "text/x-csrc");
		}else if(selectedVal == "py"){
			editor.setValue("# your code goes here\n");
			editor.setOption("mode", "text/x-python");
		}else if(selectedVal == "html"){
			editor.setValue("<!doctype html>\n<html>\n	<head>\n		<title>Insert title here</title>\n	</head>\n	<body>\n\n	</body>\n</html>");
			editor.setOption("mode", "text/html");
			flag = false;
		}
		
		if(flag){
			$('#result').show();
			$('#preview').hide();
			$('#editorSend').prop("disabled", false);
		}else{
			$('#result').hide();
			$('#preview').show();
			$('#editorSend').prop("disabled", true);
			
			var delay;
			editor.on("change", function() {
		        clearTimeout(delay);
		        delay = setTimeout(updatePreview,  300);
			});
			function updatePreview() {
				var previewFrame = document.getElementById('preview');
				var preview = previewFrame.contentDocument || previewFrame.contentWindow.document;
				preview.open();
				preview.write(editor.getValue());
				preview.close();
			}
			setTimeout(updatePreview, 300);
		}
	});
	
	// 작성 내용을 서버로 전송(비동기)
	var msgConsole = $("#msgConsole");
	$("#compiler form").on("submit", function(event) {
		event.preventDefault();
		var url = $(this).attr("action");
		var data = $(this).serialize();
		var method = $(this).attr("method");
		$.ajax({
			url : url,
			data : data,
			dataType : "json",
			method : method,
			success : function(resp) {
				var message = "";
				if (resp.result) {
					message = resp.logText;
				} else {
					message = resp.message;
				}
				msgConsole.text(message);
			},
			error : function(resp) {
				console.log(resp.status + ", " + resp.responseText);
			}
		});
		return false;
	});
</script>
</body>
</html>
