<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
#content { position: absolute; width: 100%;  height: 100%; top: 0; z-index: 9999;}

/* 화면공유 */
.video { width: 75%; position: absolute; left: 0; top: 0; height: 67%; padding-top: 0;}
.chat { width: 25%; position: absolute; right: 0; top: 0; height: 100%; padding: 22px 30px 0; background: #efefef;}
.compiler { width: 75%; position: absolute;  bottom: 0;  left: 0;  height: 33%;}

#videoArea { height: 100%; position: relative; background: #000; width: 100%;}
#videoArea video { height: 100%; position: absolute; left: 50%; top: 50%; transform: translate(-50%, -50%);}
.video #share-screen {position: absolute;top: 54%;left: 50%;transform: translate(-50%, -50%);background: none;border: none;color: #fff;font-size: 78px;height: auto;}

/* 컴파일러 */
.codeArea { width: 70%; position: relative; float: left; height: 100%;}
#conWrap { position: absolute; z-index: 5; right: 0; right: 26px;top: 18px;}
#conWrap select {background: none; color: #fff;  border: 0 none; margin-right: 11px; border-bottom: 1px solid #fff;}
#conWrap select option {color: #000;}
#result {float: right;width: 30%;height: 100%;padding: 21px;background: #293244;color: #fff;}
#msgConsole{ white-space: pre-line;}
.codeArea form {height: 100%;}
.codeArea #editor_wrap { height: 100%;}
#compiler .CodeMirror { height: 100%;}
#preview {float: right; width: 30%; height: 100%; border: 0 none; box-shadow: none;}

/* 채팅 */
.chat h1{font-size:26px;max-width: 78%;overflow:hidden;white-space: nowrap;word-break: keep-all;text-overflow: ellipsis;margin-bottom: 20px;}
#chatArea { width: 100%; height: 82%; background: #ddd; background: #fff; border-radius: 9px; overflow-y: auto;}
#message { width: 80%; height: 6%;padding: 11px;}
#sendBtn { width: 18%; height: 6%;}
#chatMessageArea { padding: 25px;}
#chatMessageArea div{margin-bottom:5px;}
#chatMessageArea .name { display: block; margin: 4px 5px;}
#chatMessageArea .msg {display: inline-block; word-break: break-word; background: #c3e9f5; padding: 5px 13px; max-width: 80%; border-radius: 6px;}
#chatMessageArea .my {text-align: right;}
#chatMessageArea .my .name {display: none;}
#chatMessageArea .my .msg {background:#a7c8d2;}
#exitBtn { position: absolute; right: 28px; top: 18px;}
#footer { display: none;}

</style>

<!-- codeMirror -->
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


<!-- webRtc -->

<!-- <script src="https://www.webrtc-experiment.com/socket.io.js"> </script> -->
<!-- <script src="https://www.webrtc-experiment.com/DetectRTC.js"></script> -->
<!-- <script src="https://www.webrtc-experiment.com/CodecsHandler.js"></script> -->
<!-- <script src="https://www.webrtc-experiment.com/BandwidthHandler.js"></script> -->
<!-- <script src="https://www.webrtc-experiment.com/IceServersHandler.js"></script> -->
<!-- <script src="https://www.webrtc-experiment.com/Pluginfree-Screen-Sharing/conference.js"> </script> -->


<script src="https://webrtc.github.io/adapter/adapter-latest.js"></script>
<script src="${pageContext.request.contextPath }/webrtc/js/socket.io.js"> </script>
<!--  스피커, 마이크 또는 오디오 시스템, 디스플레이 지원, 오디오 / 비디오 장치 수 등의 WebRTC 기능을 제공 -->
<script src="${pageContext.request.contextPath }/webrtc/js/DetectRTC.js"></script>
<script src="${pageContext.request.contextPath }/webrtc/js/CodecsHandler.js"></script>
<script src="${pageContext.request.contextPath }/webrtc/js/BandwidthHandler.js"></script>
<script src="${pageContext.request.contextPath }/webrtc/js/IceServersHandler.js"></script>
<script src="${pageContext.request.contextPath }/webrtc/js/conference.js"> </script>

<script type="text/javascript">
    
    // 웹소켓으로 쓸 변수 선언
    var wsocket;
    
    // 입장 버튼 클릭시 작동 함수
    function connect() {
        
        // 웹소켓 생성
        // 생성자에 관해서는 이전 포스팅 참고
        // 여기서는 이 페이지로 대화 내용을 보내는 것이므로 소켓 경로가 이 페이지(여기)이다
        wsocket = new WebSocket(
                "wss://${pageContext.request.serverName}:${pageContext.request.serverPort}<c:url value='/ws/chat' />");
        
        // 이렇듯 소켓을 생성하는 단계에서
        // .onopen, onmessage, onclose에 해당하는 함수를 정의
        wsocket.onopen = onOpen;
        wsocket.onmessage = onMessage;
        wsocket.onclose = onClose;
    }
    
    // 나가기 버튼 클릭시 작동 함수
    function disconnect() {
    	var writer = $("#nickname").val();
        var roomId = $("#roomId").val();
    	wsocket.send(JSON.stringify({roomId: roomId, type: 'INFO', writer: writer, message : writer + '님이 나갔습니다.'}));
        wsocket.close();
        location.href = "${pageContext.request.contextPath}/myLecture/list";
    }
    
    /*
    위 connect()에서 wsocket.onopen을 이 함수로 이미 정의해뒀다는 것을 숙지.
    아래의 onMessage(), onClose()도 마찬가지로 connect()에서 정의해놨기 때문에
    작동되는 것이다.
    
    즉, wsocket.onopen = onOpen; => WebSocket 생성시 발동
        wsocket.onmessage = onMessage;  => 메시지 받으면 발동
        wsocket.onclose = onClose;  => WebSocket.close()시 발동
    
    ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  
    작동 시점은 "WebSocket 인터페이스의 연결 상태"가 변화했을 때이고
    리스너가 항상 기다리고 있다는 것을 숙지하자!!
    ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  ※  
    */
    
    // 소켓이 연결되면 자동으로 발동
    function onOpen(evt) {
        appendMessage(null, "연결되었습니다.");
        var writer = $("#nickname").val();
        var roomId = $("#roomId").val();
        wsocket.send(JSON.stringify({roomId: roomId, type: 'JOIN', writer: writer}));
        wsocket.send(JSON.stringify({roomId: roomId, type: 'INFO', writer: writer, message : writer + '님이 접속하였습니다.'}));
    }
    
    // "message" 이름의 MessageEvent 이벤트가 발생하면 처리할 핸들러
    // 이는 서버로부터 메세지가 도착했을 때 호출 
    function onMessage(evt) {
        var data = JSON.parse(evt.data);
        if(data.type == 'INFO' && data.writer == '${loginUser.user_id}'){
        	return;
        }
        if (data.message) {
            appendMessage(data.writer, data.message);
        }
    }
    
    // WebSocket 인터페이스의 연결상태가 readyState 에서 CLOSED 로 바뀌었을 때 호출 이벤트 리스너.
    // 이 이벤트 리스너는 "close"라는 이름의 CloseEvent를 받는다.
    function onClose(evt) {
    	
        appendMessage(null, "연결을 끊었습니다.");
    }
    
    // 전송 버튼 클릭시 발동
    function send() {
        var writer = $("#nickname").val();
        var msg = $("#message").val();
        var roomId = $("#roomId").val();
        wsocket.send(JSON.stringify({roomId: roomId, type: 'CHAT', message: msg, writer: writer}));
        $("#message").val("");
    }

    // onMessage()에 내장된 함수로 받은 메세지를 채팅 내역에 추가시키는 기능을 한다.
    function appendMessage(writer, message) {
        var myMsg = writer == "${loginUser.user_id}" ? "my" : "";
        // 메세지 입력창에 msg를 하고 줄바꿈 처리
        $("#chatMessageArea").append(
        		$("<div>").attr("class", myMsg).append(
        			$("<span>").attr("class", "name").text(writer),
        			$("<span>").attr("class", "msg").text(message)
        		)
        	);
        
        // 채팅창의 heigth를 할당
        var chatAreaHeight = $("#chatArea").height();
        
        // 쌓인 메세지의 height에서 채팅창의 height를 뺀다
        // 이를 이용해서 바로 밑에서 스크롤바의 상단여백을 설정한다
        var maxScroll = $("#chatMessageArea").height() - chatAreaHeight;
        
        /* .scrollTop(int) : Set the current vertical position of the scroll bar
                             for each of the set of matched elements.*/
        // .scrollTop(int) : 파라미터로 들어간 px 만큼 top에 공백을 둔 채
        //                   스크롤바를 위치시킨다
        $("#chatArea").scrollTop(maxScroll);
    }

    $(document).ready(function() {
        
        // 메세지 입력창에 keypress 이벤트가 발생했을때 발동 함수
        // 키 하나하나 입력 하면 그때마다 발동된다
        $('#message').keypress(function(event){
            
        // https://www.w3schools.com/jsref/event_key_keycode.asp 참고
    // 입력 아스키코드 값을 가져오게 된다

  ///* In this example, we use a cross-browser solution,
// because the keyCode property does not work on the onkeypress event in Firefox.
// However, the which property does.

// Explanation of the first line in the function below:
// if the browser supports event.which, then use event.which,
// otherwise use event.keyCode */
var keycode = (event.keyCode ? event.keyCode : event.which);
            
          // enter를 쳤을 때 keycode가 13이다
  // https://blog.lael.be/post/75 <<-를 참고(다양한 키 값이 정리되어 있다)
       if(keycode == '13'){
                send(); 
            }
            
            // 만일의 경우를 대비하여 이벤트 발생 범위를 한정
    // http://ismydream.tistory.com/98 참고
            event.stopPropagation();
        });
        // 바로 접속
        connect();
        $('#sendBtn').click(function() { send(); });
        $('#exitBtn').click(function() { disconnect(); });
    });
</script>

</head>
<body>
<div class="video">
	<div id="videoArea">
		<section id="logs-message" class="experiment" style="display: none;text-align: center;font-size: 1.5em;line-height: 2;color: red;">WebRTC getDisplayMedia API.</section>


            <!-- just copy this <section> and next script -->
            <section class="experiment">
                <section class="hide-after-join" style="text-align: center;">                    
                    <input type="text" id="room-name" placeholder="Enter " style="width: 80%; text-align: center; display: none;">
                    <button id="share-screen" class="setup"><i class="far fa-play-circle"></i><span class="sr-only">Start</span></button>
                </section>
                <!-- local/remote videos container -->
                <div id="videos-container"></div>
			</section>

            <script>
                // Muaz Khan     - https://github.com/muaz-khan
                // MIT License   - https://www.webrtc-experiment.com/licence/
                // Documentation - https://github.com/muaz-khan/WebRTC-Experiment/tree/master/Pluginfree-Screen-Sharing
                var config = {
                    // via: https://github.com/muaz-khan/WebRTC-Experiment/tree/master/socketio-over-nodejs
                    openSocket: function(config) {
                        var SIGNALING_SERVER = 'https://socketio-over-nodejs2.herokuapp.com:443/';
                        config.channel = config.channel || location.href.replace(/\/|:|#|%|\.|\[|\]/g, '');
                        var sender = Math.round(Math.random() * 999999999) + 999999999;
                        io.connect(SIGNALING_SERVER).emit('new-channel', {
                            channel: config.channel,
                            sender: sender
                        });
                        var socket = io.connect(SIGNALING_SERVER + config.channel);
                        socket.channel = config.channel;
                        socket.on('connect', function () {
                            if (config.callback) config.callback(socket);
                        });
                        socket.send = function (message) {
                            socket.emit('message', {
                                sender: sender,
                                data: message
                            });
                        };
                        socket.on('message', config.onmessage);
                    },
                    onRemoteStream: function(media) {
                        if(isbroadcaster) return;
                        var video = media.video;
                        videosContainer.insertBefore(video, videosContainer.firstChild);
                        document.querySelector('.hide-after-join').style.display = 'none';
                    },
                    onRoomFound: function(room) {
                        if(isbroadcaster) return;
                        conferenceUI.joinRoom({
                            roomToken: room.roomToken,
                            joinUser: room.broadcaster
                        });
                        document.querySelector('.hide-after-join').innerHTML = '<img src="https://www.webrtc-experiment.com/images/key-press.gif" style="margint-top:10px; width:50%;" />';
                    },
                    onNewParticipant: function(numberOfParticipants) {
                        var text = numberOfParticipants + ' users are viewing your screen!';
                        
                        if(numberOfParticipants <= 0) {
                            text = 'No one is viewing your screen YET.';
                        }
                        else if(numberOfParticipants == 1) {
                            text = 'Only one user is viewing your screen!';
                        }
                        document.title = text;
                        showErrorMessage(document.title, 'green');
                    },
                    oniceconnectionstatechange: function(state) {
                        var text = '';
                        if(state == 'closed' || state == 'disconnected') {
                            text = 'One of the participants just left.';
                            document.title = text;
                            showErrorMessage(document.title);
                        }
                        if(state == 'failed') {
                            text = 'Failed to bypass Firewall rules. It seems that target user did not receive your screen. Please ask him reload the page and try again.';
                            document.title = text;
                            showErrorMessage(document.title);
                        }
                        if(state == 'connected' || state == 'completed') {
                            text = 'A user successfully received your screen.';
                            document.title = text;
                            showErrorMessage(document.title, 'green');
                        }
                        if(state == 'new' || state == 'checking') {
                            text = 'Someone is trying to join you.';
                            document.title = text;
                            showErrorMessage(document.title, 'green');
                        }
                    }
                };
                function showErrorMessage(error, color) {
                    var errorMessage = document.querySelector('#logs-message');
                    errorMessage.style.color = color || 'red';
                    errorMessage.innerHTML = error;
                    errorMessage.style.display = 'block';
                }
                function getDisplayMediaError(error) {
                    if (location.protocol === 'http:') {
                        showErrorMessage('Please test this WebRTC experiment on HTTPS.');
                    } else {
                        showErrorMessage(error.toString());
                    }
                }
                function captureUserMedia(callback) {
                    var video = document.createElement('video');
                    video.muted = true;
                    video.volume = 0;
                    try {
                        video.setAttributeNode(document.createAttribute('autoplay'));
                        video.setAttributeNode(document.createAttribute('playsinline'));
                        video.setAttributeNode(document.createAttribute('controls'));
                    } catch (e) {
                        video.setAttribute('autoplay', true);
                        video.setAttribute('playsinline', true);
                        video.setAttribute('controls', true);
                    }
                    if(navigator.getDisplayMedia || navigator.mediaDevices.getDisplayMedia) {
                        function onGettingSteam(stream) {
                            video.srcObject = stream;
                            videosContainer.insertBefore(video, videosContainer.firstChild);
                            addStreamStopListener(stream, function() {
                                location.reload();
                            });
                            config.attachStream = stream;
                            callback && callback();
                            addStreamStopListener(stream, function() {
                                location.reload();
                            });
                            document.querySelector('.hide-after-join').style.display = 'none';
                        }
                        if(navigator.mediaDevices.getDisplayMedia) {
                            navigator.mediaDevices.getDisplayMedia({video: true}).then(stream => {
                                onGettingSteam(stream);
                            }, getDisplayMediaError).catch(getDisplayMediaError);
                        }
                        else if(navigator.getDisplayMedia) {
                            navigator.getDisplayMedia({video: true}).then(stream => {
                                onGettingSteam(stream);
                            }, getDisplayMediaError).catch(getDisplayMediaError);
                        }
                    }
                    else {
                        if (DetectRTC.browser.name === 'Chrome') {
                            if (DetectRTC.browser.version == 71) {
                                showErrorMessage('Please enable "Experimental WebPlatform" flag via chrome://flags.');
                            } else if (DetectRTC.browser.version < 71) {
                                showErrorMessage('Please upgrade your Chrome browser.');
                            } else {
                                showErrorMessage('Please make sure that you are not using Chrome on iOS.');
                            }
                        }
                        if (DetectRTC.browser.name === 'Firefox') {
                            showErrorMessage('Please upgrade your Firefox browser.');
                        }
                        if (DetectRTC.browser.name === 'Edge') {
                            showErrorMessage('Please upgrade your Edge browser.');
                        }
                        if (DetectRTC.browser.name === 'Safari') {
                            showErrorMessage('Safari does NOT supports getDisplayMedia API yet.');
                        }
                    }
                }
                /* on page load: get public rooms */
                var conferenceUI = conference(config);
                /* UI specific */
                var videosContainer = document.getElementById("videos-container") || document.body;
                document.getElementById('share-screen').onclick = function() {
                    var roomName = document.getElementById('room-name') || { };
                    roomName.disabled = true;
                    captureUserMedia(function() {
                        conferenceUI.createRoom({
                            roomName: (roomName.value || 'Anonymous') + ' shared his screen with you'
                        });
                    });
                    this.disabled = true;
                };
            </script>
	</div>
</div>


<div class="compiler" id="compiler">
	<c:set var="userId" value="${loginUser.user_id }" />
	<div class="codeArea">
	<form method="post" action='<c:url value='/compiler' />'>
		<input type="hidden" name="mode" />
		<input type="hidden" name="userId" value="${userId }" />
		<div id="conWrap" class="text-right">
			<select name="editorType" id="editorType">
				<%-- OPTION 비동기 요청 --%>
			</select>
			<button type="submit" class="btn btn-primary" id="editorSend">출력</button> 
		</div>
		<div id="editor_wrap">
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
		<h4 class="h5">컴파일 결과</h4>
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
</div>
<div class="chat">
	<input type="hidden" id="roomId" value="${roomId }">
 	<input type="hidden" id="nickname" value="${loginUser.user_id }">
    <h1>${roomTitle}</h1>
    <input type="button" id="exitBtn" class="btn btn-secondary" value="나가기">
    <div id="chatArea"><div id="chatMessageArea"></div></div>
    <br/>
    <input type="text" id="message">
    <input type="button" id="sendBtn" value="전송">
</div>
</body>