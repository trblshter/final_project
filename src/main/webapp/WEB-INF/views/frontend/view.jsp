<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <link href="${pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css"> --%>
<%-- <link href="${pageContext.request.contextPath }/css/multistream/main.css" rel="stylesheet" type="text/css"> --%>
<%-- <script src="${pageContext.request.contextPath }/js/jquery.min.3.4.1.js"></script> --%>
<script type='text/javascript' src='https://cdn.scaledrone.com/scaledrone.min.js'></script>
<script src="${pageContext.request.contextPath }/js/common/lib/inherit.js"></script>
<script src="${pageContext.request.contextPath }/js/common/lib/eventemitter.js"></script>
<script src="${pageContext.request.contextPath }/js/common/lib/DetectRTC.js"></script>
<script src="${pageContext.request.contextPath }/js/common/utils.js"></script>
<style>
	div.title{
		margin: 1% 0 0 1%;
	}
 	div.row{ 
 		float :left;
/*  		display: inline-block;  */
		margin: 1%;
 	}
 	div#videoArea{
 		box-shadow: rgba(156, 172, 172, 0.2) 0px 2px 2px, 
		 	      rgba(156, 172, 172, 0.2) 0px 4px 4px, 
		 	      rgba(156, 172, 172, 0.2) 0px 8px 8px, 
		 	      rgba(156, 172, 172, 0.2) 0px 16px 16px, 
		 	      rgba(156, 172, 172, 0.2) 0px 32px 32px, 
		 	      rgba(156, 172, 172, 0.2) 0px 64px 64px; 
 		width: 70%;
 		height: 50%;
 	} 
    div#chat{
     	width: 25%; 
		height: 500px;
    }
    
	div.content {
 	      box-shadow: rgba(156, 172, 172, 0.2) 0px 2px 2px, 
	 	      rgba(156, 172, 172, 0.2) 0px 4px 4px, 
	 	      rgba(156, 172, 172, 0.2) 0px 8px 8px, 
	 	      rgba(156, 172, 172, 0.2) 0px 16px 16px, 
	 	      rgba(156, 172, 172, 0.2) 0px 32px 32px, 
	 	      rgba(156, 172, 172, 0.2) 0px 64px 64px; 
	      border-radius: 3px;
 	      height: 100%; 
	      width: 100%;
  	      display: flex;  
 	      flex-direction: column; 
	    }
    .messages {
      flex-grow: 1;
      padding: 20px 30px;
      overflow: auto;
    }
    .message {
      display: flex;
      flex-direction: column;
    }
    .message--mine {
      align-items: flex-end;
    }
    .message--theirs {
      align-items: flex-start;
    }
    .message__name {
      padding: 10px 0;
    }
    .message__bubble {
      padding: 20px;
      border-radius: 3px;
    }
    .message--theirs .message__bubble {
      background: #6363bf;
      color: white;
    }
    .message--mine .message__bubble {
      background: rgba(156, 172, 172, 0.2);
    }
    
    .footer {
      line-height: 10%;
      border-top: 1px solid rgba(156, 172, 172, 0.2);
      display: flex;
      flex-shrink: 0;
    }
    input {
      height: 76px;
      border: none;
      flex-grow: 1;
      padding: 0 30px;
      font-size: 16px;
      background: transparent;
    }
    
    button {
      border: none;
      background: transparent;
      padding: 0 30px;
      font-size: 16px;
      cursor: pointer;
    }
    
	video {
	    --video-width: 100%;
	    width: var(--video-width);
	    height: calc(var(--video-width) * (16 / 9));
	}
</style>
<div class="title">
	<h3>LECTURE TITLE</h3>
</div>
<div id="videoArea" class="row">
	<video controls id="local-screen-video" autoplay loop muted></video>
<!-- 		<video controls id="local-screen-video2" autoplay loop muted></video> -->
</div>
<!-- 	<button id="btn-start2">Start2</button>	 -->
<div id="chat" class="row">
	<div class="content">
	    <div class="messages">
	    </div>
	    <form class="footer" onsubmit="return false;">
	      <input type="text" placeholder="Your message..">
	      <button type="submit">Send</button>
	    </form>
	  </div>
	  <template data-template="message">
	    <div class="message">
	      <div class="message__name"></div>
	      <div class="message__bubble"></div>
	    </div>
 	 </template>
</div>
<br>
<button id="btn-start">Start</button>	
<!-- 	<div id="compile"> -->
<!-- 	</div> -->

<script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/2.1.1/socket.io.js"></script>
<!-- <script src="http://localhost:3000/socket.io/socket.io.js"></script> -->
<script src="${pageContext.request.contextPath }/js/porte/screen-handler.js"></script>
<script src="${pageContext.request.contextPath }/js/porte/main.js"></script>
