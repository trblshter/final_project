/*!
 *
 * modify hyjn
 * 
 * @author dodortus (dodortus@gmail.com / codejs.co.kr)
 *
 */

/*!
  간략한 시나리오.
  1. offer가 SDP와 candidate전송
  2. answer는 offer가 보낸 SDP와 cadidate를 Set한다.
  3. answer는 응답할 SDP와 candidate를 얻어서 offer한테 전달한다.
  4. offer는 응답 받은 SDP와 candidate를 Set한다.
*/

/*
TODO
 - 파폭 처리
 - hasWebCam 분기
*/
$(function() {
  console.log('Loaded webrtc');

  // cross browsing
  navigator.getUserMedia = navigator.getUserMedia || navigator.mozGetUserMedia || navigator.webkitGetUserMedia;
  var RTCPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
  var RTCSessionDescription = window.RTCSessionDescription || window.mozRTCSessionDescription || window.webkitRTCSessionDescription;
  var RTCIceCandidate = window.RTCIceCandidate || window.mozRTCIceCandidate || window.webkitRTCIceCandidate;
  var browserVersion = DetectRTC.browser.version;
  var isEdge = DetectRTC.browser.isEdge && browserVersion >= 15063; // 15버전 이상

  // for logic
  //add io(localhost로 대체 가능.)
  var socket = io("http://localhost:3000");
  var userId = Math.round(Math.random() * 999999) + 999999;
  var roomId;
  var remoteUserId; 
  var isOffer;
  var videoStream = null;
  var peer; // offer or answer peer (EndPoint)
  var streams = [];
  var peers = [];//?
  
  //ice TURN, STUN 서버의 정보를 수집한다.
  var iceServers = {
    'iceServers': [
      {'url': 'stun:stun.l.google.com:19302'},
      {'url': 'stun:stun1.l.google.com:19302'},
      {'url': 'stun:stun2.l.google.com:19302'},
      {
      'url': 'turn:107.150.19.220:3478',
      'credential': 'turnserver',
      'username': 'subrosa'
    }]
  };

  //????
  var peerConnectionOptions = {
    'optional': [{
      'DtlsSrtpKeyAgreement': 'true'
    }]
  };
  
  var mediaConstraints = {
    'mandatory': {
    	'OfferToReceiveAudio': false,
    	'OfferToReceiveVideo': true
    }
  };

  // edge is not supported
  if (isEdge) {
    peerConnectionOptions = {};
    mediaConstraints = {};
  }

  // DOM
  var $body = $('body');
  var $videoWrap = $('video');
  var $roomList = $('#room-list');
  var $metaWrap = $('#meta-wrap');
  var $uniqueToken = $('#unique-token');
  var $joinWrap = $('#join-wrap');
  var $stsbtn = $('#btn-start2');
  
  /* ScreenHandling */
  const screenHandler = new ScreenHandler();
  
  /**
   * 비디오 엘리먼트에 재생을 위해 stream 바인팅
   * @param data
   */
  function setVideoStream(data){
	  const video = data.el;
	  video.srcObject = data.stream;
  }

  /**
   * 로컬 스트림 핸들링
   * @param stream
   */
  function onLocalStream(stream){
	  console.log('onLocalSteam', stream);
	  setVideoStream({
		  el: document.querySelector('#local-screen-video'),
		  stream: stream
	  });
	  videoStream = stream;
  };

  /**
   * screenHandler를 통해 캡쳐 API를 호출.
   */
  function startScreenShare(){
	  screenHandler.start((stream) => {
		  onLocalStream(stream);
	  }); 
  };
  
  /**
   * DOM 이벤트 바인딩
   */
  function bindEvent(){
	  document.querySelector('#btn-start').onclick = startScreenShare;
  }
  
  /* ScreenHandler Part End */
  
  
  /**
  * screenViewing
  */
  function screenViewing() {
    console.log('screenViewing');
    if(isOffer){
		  var peer = createPeerConnection('screen');
		  createOffer('screen', peer, videoStream);
	  }
  }

  /**
  * createOffer
  * offer SDP를 생성 한다.
  */
  function createOffer(sessionType, peer, stream) {
    console.log('createOffer', arguments);
    if(stream){
    	peer.addStream(stream); // addStream 제외시 recvonly로 SDP 생성됨
    }
    peer.createOffer(function(SDP) {
      // url parameter codec=h264
      if (location.search.substr(1).match('h264')) {
        SDP.sdp = SDP.sdp.replace("100 101 107", "107 100 101"); // for chrome < 57
        SDP.sdp = SDP.sdp.replace("96 98 100", "100 96 98"); // for chrome 57 <
      }

      peer.setLocalDescription(SDP);
      console.log("Sending offer description", SDP);
      send({
        sender: userId,
        to: 'all',
        sessionType: sessionType,
        sdp: SDP
      });
    }, onSdpError, mediaConstraints);
  }

  /**
  * createAnswer
  * offer에 대한 응답 SDP를 생성 한다.
  * @param {object} msg offer가 보내온 signaling
  */
  function createAnswer(sessionType, peer, msg, stream) {
    console.log('createAnswer', arguments);
    if(stream){
    	peer.addStream(stream);
    }
    peer.setRemoteDescription(new RTCSessionDescription(msg.sdp), function() {
      peer.createAnswer(function(SDP) {
        peer.setLocalDescription(SDP);
        console.log("Sending answer to peer.", SDP);
        send({
          sender: userId,
          to: 'all',
          sessionType: sessionType,
          sdp: SDP
        });
      }, onSdpError, mediaConstraints);
    }, function() {
      console.error('setRemoteDescription', arguments);
    });
  }

  /**
  * createPeerConnection
  * offer, answer 공통 함수로 peer를 생성하고 관련 이벤트를 바인딩 한다.
  */
  function createPeerConnection(type) {
    console.log('createPeerConnection', arguments);

    var peer = {
      type: type,
      pc: null
    };

    //가장 최근에 생성된 RTCPeerConnection을 반환한다.
    peer.pc = new RTCPeerConnection(iceServers, peerConnectionOptions);
    console.log('new Peer', peer);

    peer.pc.onicecandidate = function(event) {
      if (event.candidate) {
        send({
          userId: userId,
          to: 'all',
          label: event.candidate.sdpMLineIndex,
          id: event.candidate.sdpMid,
          candidate: event.candidate.candidate,
          sessionType: type
        });
      } else {
        console.info('Candidate denied', event.candidate);
      }
    };

    peer.pc.onaddstream = function(event) {
      console.log("Adding remote stream", event);
      if(!document.querySelector('#local-screen-video2').srcObject)
    	  document.querySelector('#local-screen-video2').srcObject = event.stream;
      //$body.removeClass('wait').addClass('connected');
    };

    peer.pc.onremovestream = function(event) {
      console.log("Removing remote stream", event);
    };

    peer.pc.onnegotiationneeded = function(event) {
      console.log("onnegotiationneeded", event);
    };

    peer.pc.onsignalingstatechange = function(event) {
      console.log("onsignalingstatechange", event);
    };

    peer.pc.oniceconnectionstatechange = function(event) {
      console.log("oniceconnectionstatechange",
      'iceGatheringState: ' + peer.iceGatheringState,
      '/ iceConnectionState: ' + peer.iceConnectionState);
    }

    // add peers array
    peers.push(peer);

    return peer.pc;
  }

  /**
  * getPeer
  * 다수의 Peer중 해당하는 type과 매칭되는 peer 리턴한다.
  */
  function getPeer(type) {
    console.log('getPeer', arguments);
    var peer = null;

    for(var i=0; i < peers.length; i++) {
      if (peers[i].type === type) {
        peer = peers[i].pc;
      }
    }
    return peer;
  }

  /**
  * onSdpError
  */
  function onSdpError() {
    console.log('onSdpError', arguments);
  }

  /****************************** Below for signaling ************************/

  /**
  * send
  * @param {object} msg data
  */
  function send(data) {
    console.log('send', data);
    data.roomId = roomId;
    socket.send(data);
  }

  /**
  * onmessage
  * @param {object} msg data
  */
  function onmessage(data) {
    console.log('onmessage', data);

    var msg = data;
    var sdp = msg.sdp || null;
    var sessionType = data.sessionType;

    if (!remoteUserId) {
      remoteUserId = data.userId;
    }

    // 접속자가 보내온 offer처리
    if (sdp) {
      if (sdp.type  == 'offer') {
        console.log('Adding local stream...');
        var peer = createPeerConnection(sessionType);
        
        // TODO 개선 필요
        if (sessionType === 'screen') {
          createAnswer(sessionType, peer, msg);
        }
      // offer에 대한 응답 처리
      } else if (sdp.type == 'answer') {
        var peer = getPeer(sessionType);
        peer.setRemoteDescription(new RTCSessionDescription(msg.sdp));
      }

    // offer, answer cadidate처리
    } else if (msg.candidate) {
	      var peer = getPeer(sessionType);
	      var candidate = new RTCIceCandidate({
	        sdpMid: msg.id,
	        sdpMLineIndex: msg.label,
	        candidate: msg.candidate
	      });
	      peer.addIceCandidate(candidate);
    } else {
      //console.log()
    }
  }
  
  /**
   * setRoomToken
   */
  function setRoomToken() {
    //console.log('setRoomToken', arguments);

    if (location.hash.length > 2) {
      $uniqueToken.attr('href', location.href);
    } else {
      location.hash = '#' + (Math.random() * new Date().getTime()).toString(32).toUpperCase().replace(/\./g, '-');
    }
  }

  
  /**
   * onFoundUser
   */
  function onFoundUser() {
	  isOffer = true;
	  screenViewing();
  }

  /**
   * onLeave
   * @param {string} userId
   */
  function onLeave(userId) {
    if (remoteUserId == userId) {
      $('.remote-video').remove();
      $body.removeClass('connected').addClass('wait');
      remoteUserId = null;
    }
  }
  
  function pauseVideo(callback) {
    console.log('pauseVideo', arguments);
    videoStream.getVideoTracks()[0].enabled = false;
    callback && callback();
  }

  function resumeVideo(callback) {
    console.log('resumeVideo', arguments);
    videoStream.getVideoTracks()[0].enabled = true;
    callback && callback();
  }

  function muteAudio(callback) {
    console.log('muteAudio', arguments);
    videoStream.getAudioTracks()[0].enabled = false;
    callback && callback();
  }

  function unmuteAudio(callback) {
    console.log('unmuteAudio', arguments);
    videoStream.getAudioTracks()[0].enabled = true;
    callback && callback();
  }
  
  /**
   * chatting
   */
  const possibleEmojis = [
	  '🐀','🐁','🐭','🐹','🐂','🐃','🐄','🐮','🐅','🐆','🐯','🐇','🐐','🐑','🐏','🐴',
	  '🐎','🐱','🐈','🐰','🐓','🐔','🐤','🐣','🐥','🐦','🐧','🐘','🐩','🐕','🐷','🐖',
	  '🐗','🐫','🐪','🐶','🐺','🐻','🐨','🐼','🐵','🙈','🙉','🙊','🐒','🐉','🐲','🐊',
	  '🐍','🐢','🐸','🐋','🐳','🐬','🐙','🐟','🐠','🐡','🐚','🐌','🐛','🐜','🐝','🐞',
	];
  
	function randomEmoji() {
	  var randomIndex = Math.floor(Math.random() * possibleEmojis.length);
	  return possibleEmojis[randomIndex];
	}
  
	const emoji = randomEmoji();
	// TODO: Replace with your own channel ID
	const drone = new ScaleDrone('Hue92ea33EmJyNXR');
	// Scaledrone room name needs to be prefixed with 'observable-'
	const roomName = 'observable-' + roomId;
	// Scaledrone room used for signaling
	let room;
	
	// RTCPeerConnection	
	let pc;
	
	// RTCDataChannel
	let dataChannel;
	
	// Wait for Scaledrone signalling server to connect
	drone.on('open', error => {
	  if (error) {
	    return console.error(error);
	  }
	  room = drone.subscribe(roomName);
	  room.on('open', error => {
	    if (error) {
	      return console.error(error);
	    }
	    console.log('Connected to signaling server');
	  });
	  
	  // We're connected to the room and received an array of 'members'
	  // connected to the room (including us). Signaling server is ready.
	  room.on('members', members => {
	    if (members.length > 4) {
	      return alert('The room is full');
	    }
	    // If we are the second user to connect to the room we will be creating the offer
	    const isOfferer = members.length === 2;
	    startWebRTC(isOfferer);
	  });
	});
	
	// Send signaling data via Scaledrone
	function sendSignalingMessage(message) {
	  drone.publish({
	    room: roomName,
	    message
	  });
	}
	
	function startWebRTC(isOfferer) {
		  console.log('Starting WebRTC in as', isOfferer ? 'offerer' : 'waiter');
		  pc = new RTCPeerConnection(iceServers);
		  // 'onicecandidate' notifies us whenever an ICE agent needs to deliver a
		  // message to the other peer through the signaling server
		  pc.onicecandidate = event => {
		    if (event.candidate) {
		      sendSignalingMessage({'candidate': event.candidate});
		    }
		  };
		  
		  if (isOfferer) {
			    // If user is offerer let them create a negotiation offer and set up the data channel
			    pc.onnegotiationneeded = () => {
			      pc.createOffer(localDescCreated, error => console.error(error));
			    }
			    dataChannel = pc.createDataChannel('chat');
			    setupDataChannel();
			  } else {
			    // If user is not the offerer let wait for a data channel
				  pc.ondatachannel = event => {
					  dataChannel = event.channel;
					  setupDataChannel();
			    }
			  }
			  startListentingToSignals();
	}
	
	  
	function startListentingToSignals() {
		  // Listen to signaling data from Scaledrone
		  room.on('data', (message, client) => {
		    // Message was sent by us
		    if (client.id === drone.clientId) {
		      return;
		    }
		    if (message.sdp) {
		      // This is called after receiving an offer or answer from another peer
		      pc.setRemoteDescription(new RTCSessionDescription(message.sdp), () => {
		        console.log('pc.remoteDescription.type', pc.remoteDescription.type);
		        // When receiving an offer lets answer it
		        if (pc.remoteDescription.type === 'offer') {
		          console.log('Answering offer');
		          pc.createAnswer(localDescCreated, error => console.error(error));
		        }
		      }, error => console.error(error));
		    } else if (message.candidate) {
		      // Add the new ICE candidate to our connections remote description
		      pc.addIceCandidate(new RTCIceCandidate(message.candidate));
		    }
		  });
		}
	
	function localDescCreated(desc) {
		  pc.setLocalDescription(
		    desc,
		    () => sendSignalingMessage({'sdp': pc.localDescription}),
		    error => console.error(error)
		  );
		}
	
	// Hook up data channel event handlers
	function setupDataChannel() {
	  checkDataChannelState();
	  dataChannel.onopen = checkDataChannelState;
	  dataChannel.onclose = checkDataChannelState;
	  dataChannel.onmessage = event =>
	    insertMessageToDOM(JSON.parse(event.data), false)
	}
	
	function checkDataChannelState() {
		  console.log('WebRTC channel state is:', dataChannel.readyState);
		  if (dataChannel.readyState === 'open') {
		    insertMessageToDOM({content: 'WebRTC data channel is now open'});
		  }
	}
	
	function insertMessageToDOM(options, isFromMe) {
		  const template = document.querySelector('template[data-template="message"]');
		  const nameEl = template.content.querySelector('.message__name');
		  if (options.emoji || options.name) {
		    nameEl.innerText = options.emoji + ' ' + options.name;
		  }
		  template.content.querySelector('.message__bubble').innerText = options.content;
		  const clone = document.importNode(template.content, true);
		  const messageEl = clone.querySelector('.message');
		  if (isFromMe) {
		    messageEl.classList.add('message--mine');
		  } else {
		    messageEl.classList.add('message--theirs');
		  }

		  const messagesEl = document.querySelector('.messages');
		  messagesEl.appendChild(clone);

		  // Scroll to bottom
		  messagesEl.scrollTop = messagesEl.scrollHeight - messagesEl.clientHeight;
	}
	
	const form = document.querySelector('.footer');
	form.addEventListener('submit', () => {
	  const input = document.querySelector('.footer input[type="text"]');
	  const value = input.value;
	  input.value = '';

	  const data = {
	    name,
	    content: value,
	    emoji,
	  };
	  dataChannel.send(JSON.stringify(data));
	  insertMessageToDOM(data, true);
	});
	
	insertMessageToDOM({content: 'Chat URL is ' + location.href});

	
  /**
   * initialize
   */
  function initialize() {
	setRoomToken();
    bindEvent(); // screenHandler bind 이벤트
    roomId = location.href.replace(/\/|:|#|%|\.|\[|\]/g, '');

    $('#btn-mic').click(function() {
      var $this = $(this);
      $this.toggleClass('active');
      if ($this.hasClass('active')) {
        muteAudio();
      } else {
        unmuteAudio();
      }
    });
  }
  
  initialize();

  /**
   * socket handling
   */
  socket.emit('enter', roomId, userId); //해당 room에 user를 enter처리.
  
  socket.on('join', function(roomId, userList) { // room에 user가 들어왔을 때,
    console.log('join', arguments); //상단의 argument들 출력
    if (Object.size(userList) > 1) { // 같은 룸에 유저가 한 명이상 일 때, 
      onFoundUser(); 
    }
  });

  socket.on('leave', function(userId) { // user가 나갔을 때, 
    console.log('leave', arguments);
    onLeave(userId);
  });
  socket.on('message', function(data) { // user가 메시지를 보냈을 때,
    onmessage(data);
  });
  
});

Object.size = function(obj) {
	  var size = 0, key;
	  for (key in obj) {
	    if (obj.hasOwnProperty(key)) {
	      size++;
	    }
	  }
	  return size;
	};

