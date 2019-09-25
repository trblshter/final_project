package kr.or.ddit.lecture.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import kr.or.ddit.vo.CalendarVO;
import kr.or.ddit.vo.ScheduledVO;

@RestController
@RequestMapping(value="/calendarInsert.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CalendarInsertController {
	
	@Inject
	ILectureService service;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@GetMapping
	public String insert(@ModelAttribute CalendarVO calendarVO) throws ParseException {
//		System.out.println(calendarVO.get_id());
//		ScheduledVO scheduledVO = new ScheduledVO();
//		scheduledVO.setLt_no(calendarVO.getType());
//		scheduledVO.setSc_bgcolor(calendarVO.getBackgroundColor());
//		scheduledVO.setSc_content(calendarVO.getDescription());
//		Date end = (Date) format.parse(calendarVO.getEnd());
//		scheduledVO.setSc_endday(end);
////		System.out.println(calendarVO.get_id());
////		scheduledVO.setSc_no(String.valueOf(calendarVO.get_id()));
//		Date start = (Date) format.parse(calendarVO.getStart());
//		scheduledVO.setSc_startday(start);
//		scheduledVO.setSc_tcolor(calendarVO.getTextColor());
//		scheduledVO.setSc_title(calendarVO.getTitle());
//		scheduledVO.setSc_turn(0);
//		scheduledVO.setSc_writer(calendarVO.getUsername());
		
		
//		ServiceResult result = service.createLecture(scheduledVO);
		
		
		
		
		
		return null;
	}

}
