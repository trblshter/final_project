package kr.or.ddit.websocket.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.TeamVO;

@Repository
public class RoomRepository {
	
	// 실시간 수업 방목록 관리
	private final Map<String, TeamVO> roomMap = new HashMap<>();
	
	public RoomRepository() {
		// 테스트용 기본값
//		roomMap.put("aaaagg", new HashSet(Arrays.asList("aaaa", "asdf", "aaaagg")));
//		roomMap.put("tutor", new HashSet(Arrays.asList("bbbb", "gggg", "tutor")));
	}
	
	public void setRoom(String lt_no, TeamVO userList) {
		roomMap.put(lt_no, userList);
	}
	
    public TeamVO getRoomUser(String tutor_id) {
        return roomMap.get(tutor_id);
    }
    
}
