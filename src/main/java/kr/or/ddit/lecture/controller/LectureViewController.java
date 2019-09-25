package kr.or.ddit.lecture.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.lecture.service.ILectureService;
import kr.or.ddit.lecture.service.IReviewService;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.ReviewVO;

@Controller
public class LectureViewController {

	@Inject
	ILectureService service;
	
	@Inject
	IReviewService reviewService;
	
	@RequestMapping("/lecture/lectureView.do")
	public String view(@RequestParam(name="who", required=true) String lt_no, Model model) {
		LecturetutorVO lecturetutorVO = service.retrieveLectureTutor(lt_no);
		System.out.println(lecturetutorVO.getLt_date());
		String rv_recipient = lecturetutorVO.getLt_writer(); 
		
		List<ReviewVO> reviewVO = reviewService.reviewList(rv_recipient);
		
		System.out.println(reviewVO);
		
		model.addAttribute("lecturetutor", lecturetutorVO);
		
		model.addAttribute("review", reviewVO);
		
		return "lecture/lectureView";
	}
}
