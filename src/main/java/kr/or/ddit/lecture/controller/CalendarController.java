package kr.or.ddit.lecture.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.lecture.service.ILectureService;
import kr.or.ddit.vo.CalendarVO;
import kr.or.ddit.vo.ScheduledVO;

@RestController
@RequestMapping(value="/calendar.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CalendarController {
	
	@Inject
	ILectureService service;
	
	@GetMapping
	public CalendarVO[] process(@RequestParam(required=false) String SC_WRITER) {
		
//		System.out.println(SC_WRITER);
//		List<ScheduledVO> list = service.lectureList(SC_WRITER);
////		List<CalendarVO> calList = new ArrayList<>();
//		
//		
//		for(ScheduledVO vo : list) {
//			CalendarVO calvo = new CalendarVO();
//			calvo.set_id(vo.getSc_no());
//			calvo.setTitle(vo.getSc_title());
//			calvo.setDescription(vo.getSc_content());
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			String start = format.format(vo.getSc_startday());
////			start = start.substring(0, 10) + "T" + start.substring(10, start.length());
//			System.out.println(start);
//			calvo.setStart(start);
//			String end = format.format(vo.getSc_endday());
////			end = end.substring(0, 10) + "T" + end.substring(10, end.length());
//			calvo.setEnd(end);
//			calvo.setType(vo.getLt_no());
//			calvo.setUsername(vo.getSc_writer());
//			calvo.setBackgroundColor(vo.getSc_bgcolor());
//			calvo.setTextColor(vo.getSc_tcolor());
//			calvo.setAllDay(false);
//			
//			calList.add(calvo);
//		}
//		
//		CalendarVO[] array = new CalendarVO[calList.size()];
//		int size = 0;
//		for(CalendarVO vo : calList) {
//			array[size++] = vo;
//		}
//		System.out.println(Arrays.toString(array));
//		
		return null;
	}
}
