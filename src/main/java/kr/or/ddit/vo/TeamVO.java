package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 팀
 * @author pc22
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamVO implements Serializable {
	private Integer team_no; // 번호
	private String team_member1; // 팀원1
	private String team_member2; // 팀원2
	private String team_member3; // 팀원3
	private String team_member4; // 팀원4
	private String lt_no;
	
	// 실시간 방 접속 가능한 사람(선생님 포함)
	private Set<String> room = new HashSet<>();
	
	// 실시간 방 접속된 회원과 회원의 WebSocketSession
	private Map<String, WebSocketSession> socketMap = new HashMap<>();
	
	public void setTeam_member1(String team_member1) {
		this.team_member1 = team_member1;
		room.add(team_member1);
	}
	public void setTeam_member2(String team_member2) {
		this.team_member2 = team_member2;
		room.add(team_member2);
	}
	public void setTeam_member3(String team_member3) {
		this.team_member3 = team_member3;
		room.add(team_member3);
	}
	public void setTeam_member4(String team_member4) {
		this.team_member4 = team_member4;
		room.add(team_member4);
	}
}
