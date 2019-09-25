// Generate random room name if needed
if (!location.hash) {
 location.hash = Math.floor(Math.random() * 0xFFFFFF).toString(16);
}

const roomHash = location.hash.substring(1);

const configuration = {
         iceServers: [{
           urls: 'stun:stun.l.google.com:19302' // Google's public STUN server
         }]
        };

function onSuccess() {};
function onError(error) {
  console.error(error);
};

var localVideo = document.querySelector('#local-screen-video');
var remoteVideo = document.querySelector('#remote-screen-video');
var isBroadCaster = false;
var castedStream;

/**
 * screen handling
 */
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
      stream.getTracks().forEach(function(track){
          pc.addTrack(track,stream);
      });
};

/**
 * screenHandler를 통해 캡쳐 API를 호출.
 */
function startScreenShare(){
//    if(!isBroadCaster) return;
      screenHandler.start((stream) => {
          onLocalStream(stream);
      });
};



/**
 * DOM 이벤트 바인딩
 */
//function bindEvent(){
//      document.querySelector('#btn-start').onclick = startScreenShare;
//}

/**
 * chatting Emoji
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

let membersArray = [];

//Room name needs to be prefixed with 'observable-'
const roomName = 'observable-' + roomHash;

let room;

// RTCDataChannel
let dataChannel;

const drone = new ScaleDrone('Hue92ea33EmJyNXR');
 
drone.on('open', error => {
 if (error) {
   return onError(error);
 }
 
 room = drone.subscribe(roomName);
 room.on('open', error => {
   if (error) {
     onError(error);
   }
   console.log('Connected to signaling server');
 });
 
 // We're connected to the room and received an array of 'members'
 // connected to the room (including us). Signaling server is ready.
 //List of currently online members, emitted once
 room.on('members', members => {
     membersArray = members;
   if (members.length >= 6) {
     return alert('The room is full');
   }
   // If we are the second user to connect to the room we will be creating the offer
   const isOfferer = members.length >= 2;
   if(!isOfferer) isBroadCaster = true;
   startWebRTC(isOfferer);
   startListentingToSignals();
 });
 
//User joined the room
 room.on('member_join', member => {
  membersArray.push(member);
  // updateMembersDOM(); uncomment later
 });
 
//User left the room
 room.on('member_leave', ({id}) => {
  const index = membersArray.findIndex(member => member.id === id);
  members.splice(index, 1);
  // updateMembersDOM(); uncomment later
 });
 
});



//Send signaling data via Scaledrone
function sendMessage(message) {
     drone.publish({
       room: roomName,
       message
     });
}

//Send signaling data via Scaledrone (Chat-data)
function sendMessageForChat(message) {
    drone.publish({
        room: roomName,
        message
    });
}

let pc;

function startWebRTC(isOfferer) {
     console.log('Starting WebRTC in as', isOfferer ? 'offerer' : 'waiter');
     pc = new RTCPeerConnection(configuration);
//     if(!isOfferer) 
    	 startScreenShare();
//     if(isBroadCaster) captureUserMedia();
        
     // 'onicecandidate' notifies us whenever an ICE agent needs to deliver a
     // message to the other peer through the signaling server
     pc.onicecandidate = event => {
       if (event.candidate) {
         sendMessage({'candidate': event.candidate});
       }
     };
    
     // If user is offerer let the 'negotiationneeded' event create the offer
     if (isOfferer) {
         pc.onnegotiationneeded = () => {
             pc.createOffer().then(localDescCreated).catch(onError);
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
    
    // When a remote stream arrives display it in the #remoteVideo element
     pc.ontrack = function(event){
         event.stream = event.streams[event.streams.length-1];
         castedStream = event.stream;
         remoteVideo.srcObject = castedStream;
     }
}
    
function startListentingToSignals() {
     // Listen to signaling data from Scaledrone
     room.on('data', (message, client) => {
       // Message was sent by us
       if (!client || client.id === drone.clientId) {
         return;
       }
       if (message.sdp) {
         // This is called after receiving an offer or answer from another peer
         pc.setRemoteDescription(new RTCSessionDescription(message.sdp), () => {
             console.log('pc.remoteDescription.type', pc.remoteDescription.type);
           // When receiving an offer lets answer it
           if (pc.remoteDescription.type === 'offer') {
             console.log('Answering offer');
             pc.createAnswer().then(localDescCreated).catch(onError);
           }
         }, error => console.error(error));
       } else if (message.candidate) {
         // Add the new ICE candidate to our connections remote description
         pc.addIceCandidate(
           new RTCIceCandidate(message.candidate), onSuccess, onError
         );
       }
     });
}

function localDescCreated(desc) {
     pc.setLocalDescription(
       desc,
       () => sendMessage({'sdp': pc.localDescription}),
       onError
     );
}

//Hook up data channel event handlers
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
