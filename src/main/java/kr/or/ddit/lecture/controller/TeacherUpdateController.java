package kr.or.ddit.lecture.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.service.ILectureService;
import kr.or.ddit.lecture.service.ITeacherService;
import kr.or.ddit.message.service.IMessageService;
import kr.or.ddit.vo.CalendarVO;
import kr.or.ddit.vo.LectureUserVO;
import kr.or.ddit.vo.MessageVO;
import kr.or.ddit.vo.ScheduledVO;

@RestController
@RequestMapping("/teacher")
public class TeacherUpdateController {
	
	@Inject
	ITeacherService service;
	
	@Inject
	IMessageService messageService;
	
	
	
	@RequestMapping(value="/teacherUpdate.do" ,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PostMapping
	public String update(
			@ModelAttribute LectureUserVO lectureUserVO
			) throws ParseException {
		
		
		String message = null;
		
		MessageVO messageVO = new MessageVO();
		messageVO.setMsg_writer(lectureUserVO.getLu_writer());
		messageVO.setMsg_recipient(lectureUserVO.getLu_tutor());
		messageVO.setMsg_title("강의 신청 수락");
		messageVO.setMsg_content(lectureUserVO.getLu_writer() + "의해 선택되셨습니다");
		messageVO.setTeam_no(lectureUserVO.getTeam_no());
		
		ServiceResult result2 = messageService.createMessage(messageVO);
		
		ServiceResult result = service.updateLecture(lectureUserVO);
		if(ServiceResult.OK.equals(result)) {
			message = "선택완료했습니다";
		}
		
		return message;
	}
}
