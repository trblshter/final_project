package kr.or.ddit.lecture.controller;

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
@RequestMapping(value="/lectureDelete.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LectureDeleteController {
	
	@Inject
	ILectureService service;
	
	@GetMapping
	public String delete(@RequestParam(name="_id") String _id) {
		ScheduledVO scheduledVO = new ScheduledVO();
		scheduledVO.setSc_no(_id);
		service.deleteLecture(scheduledVO);
		
		return null;
	}

}
