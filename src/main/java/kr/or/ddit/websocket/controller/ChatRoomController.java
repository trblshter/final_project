package kr.or.ddit.websocket.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.WebSocketSession;

import kr.or.ddit.lecture.service.ILectureService;
import kr.or.ddit.lecture.service.ITeamService;
import kr.or.ddit.vo.LectureVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.TeamVO;
import kr.or.ddit.vo.UserVO;
import kr.or.ddit.websocket.dao.RoomRepository;

@Controller
@RequestMapping("/room")
public class ChatRoomController {
	
	private final RoomRepository repository;
	
	@Inject
	ITeamService teamService;
	
	@Inject
	ILectureService lectureService;
	
    @Autowired
    public ChatRoomController(RoomRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping("{lt_no}")
    public String room(@PathVariable String lt_no, HttpSession session, Model model) {
    	// 선생님 접속일 경우 스터디룸 생성
    	LecturetutorVO lecture= lectureService.retrieveLectureTutor(lt_no);
    	
    	String tutor = lecture.getLt_writer();
    	String lectureTitle = lecture.getLt_title();
    	UserVO loginUser = (UserVO) session.getAttribute("loginUser");
    	if(tutor.length() > 0 && loginUser.getUser_id().equals(tutor)) {
    		TeamVO team = teamService.retrieveTeam(lt_no);
    		team.getRoom().add(loginUser.getUser_id());
    		repository.setRoom(lt_no, team);
    	}
    	
    	String message = null;
    	String goPage = null;
    	// 스터디룸에 포함된 유저만 접속
    	Set<String> roomUsers = null;
    	try {
    		roomUsers = repository.getRoomUser(lt_no).getRoom();
    		if(roomUsers == null || !roomUsers.contains(loginUser.getUser_id())) {
    			message = "해당 강의에 참여할 수 없습니다.";
    			goPage = "redirect:/myLecture/list";
    		}else {
    			model.addAttribute("roomId", lt_no);
    			model.addAttribute("roomTitle", lectureTitle);
    			System.out.println("오류2구간");
    			goPage = "room/room";
    		}
    	}catch(NullPointerException e) {
    		message = "강의 오픈 전 입니다. ";
    		goPage = "redirect:/myLecture/list";
    	}
    	session.setAttribute("message", message);
        return goPage;
    }
}
