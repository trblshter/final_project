package kr.or.ddit.websocket.handler;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.vo.RoomMessageVO;
import kr.or.ddit.vo.TeamVO;
import kr.or.ddit.websocket.dao.RoomRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EchoWebSocketHandler extends TextWebSocketHandler {
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final RoomRepository repository;
	@Autowired
    public EchoWebSocketHandler(RoomRepository repository) {
        this.repository = repository;
    }
    // 접속한 유저들의 목록을 담기 위한 Map 선언
    // ConcurrentHashMap은 Hashtable과 유사하지만 멀티스래드 환경에서 더 안전하다
    /*
        ConcurrentHashMap에 대한 설명(반드시 읽고 숙지)

	  https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ConcurrentHashMap.html
	  http://blog.leekyoungil.com/?p=159
	  http://limkydev.tistory.com/64
    */
    private Map<String, WebSocketSession> users = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(
            WebSocketSession session) throws Exception {

        // session에서 id를 가져와서 로그에 남긴다(없어도 되는 과정)
        log.info(session.getId() + " 연결 됨");

        // 위에서 선언한 users라는 map에 user를 담는 과정(필수)
        // map에 담는 이유는 메세지를 일괄적으로 뿌려주기 위해서이다
        users.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(
            WebSocketSession session, CloseStatus status) throws Exception {
    	log.info(session.getId() + " 연결 종료됨");

        // map에서 세션에서 연결 종료된 유저를 없애는 이유는
        // 더 이상 메세지를 보낼 필요가 없기 때문에 목록에서 지우는 것이다
        users.remove(session.getId());
    }
    
    @Override
    protected void handleTextMessage(
            WebSocketSession session, TextMessage message) throws Exception {
    	
    	String payload = message.getPayload();
        log.info("payload : {}", payload);
        
        RoomMessageVO roomMsg = objectMapper.readValue(payload, RoomMessageVO.class);
        TeamVO team = repository.getRoomUser(roomMsg.getRoomId());
        team.getSocketMap().put(roomMsg.getWriter(), session);
        
        Set<Entry<String, WebSocketSession>> socketEntry = team.getSocketMap().entrySet();
        
        for (Entry<String, WebSocketSession> entry : socketEntry) { //<-- .values() 로 session들만 가져옴
        	// 여기서 모든 세션들에게 보내지게 된다
        	// 1회전당 현재 회전에 잡힌 session에게 메세지 보낸다
        	System.out.println(roomMsg);
        	entry.getValue().sendMessage(message);;
        }
    }

    @Override
    public void handleTransportError(
            WebSocketSession session, Throwable exception) throws Exception {
    	log.info(session.getId() + " 익셉션 발생: " + exception.getMessage());
    }


}
