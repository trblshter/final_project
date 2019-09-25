package kr.or.ddit.lecture.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.lecture.service.IReviewService;
import kr.or.ddit.lecture.service.ITeacherCommentService;
import kr.or.ddit.lecture.service.ITeacherService;
import kr.or.ddit.vo.LectureCommentVO;
import kr.or.ddit.vo.LectureUserVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;

@Controller
public class TeacherViewController {

	@Inject
	ITeacherService service;
	
	@Inject
	ITeacherCommentService commentService;
	
	@RequestMapping("/lecture/teacherView.do")
	public String view(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			@RequestParam(name="who", required=false) String lu_no, Model model) {
		System.out.println(lu_no);
		LectureUserVO lectureUserVO = service.retrieveLectureUser(lu_no);
		LectureCommentVO searchVO = new LectureCommentVO();
		searchVO.setLu_no(lu_no);
		
		PagingVO<LectureCommentVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);
		
		pagingVO.setSearchVO(searchVO);
		List<LectureCommentVO> list = commentService.retrieveLectureCommentList(pagingVO);
		pagingVO.setDataList(list);
		
		long totalRecord = commentService.retrieveTeacherCommentCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		model.addAttribute("lectureUser", lectureUserVO);
//		
		model.addAttribute("pagingVO", pagingVO);
		
		return "lecture/teacherView";
	}
}
