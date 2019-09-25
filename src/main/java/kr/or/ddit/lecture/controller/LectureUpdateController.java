package kr.or.ddit.lecture.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.lecture.service.ILectureService;
import kr.or.ddit.vo.CalendarVO;
import kr.or.ddit.vo.ScheduledVO;

@RestController
@RequestMapping(value="/lectureUpdate.do" ,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LectureUpdateController {
	
	@Inject
	ILectureService service;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@GetMapping
	public String update(
			@RequestParam(name="_id") String _id, 
			@RequestParam(name="title") String title,
			@RequestParam(name="start") String start,
			@RequestParam(name="end") String end,
			@RequestParam(name="type") String type,
			@RequestParam(name="backgroundColor") String backgroundColor,
			@RequestParam(name="description") String description
			
			) throws ParseException {
		
		ScheduledVO scheduledVO = new ScheduledVO();
		scheduledVO.setLt_no(type);
		scheduledVO.setSc_bgcolor(backgroundColor);
		scheduledVO.setSc_content(description);
		Date endDate = (Date) format.parse(end);
		scheduledVO.setSc_endday(endDate);
		scheduledVO.setSc_no(_id);
		Date startDate = (Date) format.parse(start);
		scheduledVO.setSc_startday(startDate);
		scheduledVO.setSc_title(title);
		scheduledVO.setSc_turn(0);
//		scheduledVO.setSc_writer(calendarVO.getUsername());
		
//		service.updateLecture(scheduledVO);
		return "lecture/werwe";
	}
}
