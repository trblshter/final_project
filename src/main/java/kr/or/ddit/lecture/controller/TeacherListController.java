package kr.or.ddit.lecture.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.lecture.service.ITeacherService;
import kr.or.ddit.vo.LectureUserVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;

@Controller
@RequestMapping("/teacher")
public class TeacherListController {
	
	@Inject
	ITeacherService service;
	
	@RequestMapping(value="teacherList.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<LectureUserVO> ajaxList(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			@RequestParam(required=false) String searchType,
			@RequestParam(required=false) String searchWord,
 			Model model
			) {
		list(currentPage, searchType, searchWord, model);
		
		return  (PagingVO<LectureUserVO>) model.asMap().get("pagingVO");
	}
	
	@RequestMapping("teacherList.do")
	public String list(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			@RequestParam(required=false) String searchType,
			@RequestParam(required=false) String searchWord,
			Model model
			) {
		
		PagingVO<LectureUserVO> pagingVO = new PagingVO<>();
		pagingVO.setSearchType(searchType);
		pagingVO.setSearchWord(searchWord);
		pagingVO.setCurrentPage(currentPage);
		
		long totalRecord = service.retrieveLectureUserCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		System.out.println(pagingVO.getTotalRecord());
		List<LectureUserVO> list = service.retrieveLectureUserList(pagingVO);
		pagingVO.setDataList(list);
		System.out.println(pagingVO.getStartPage());
		
		model.addAttribute("pagingVO", pagingVO);
		
		return "lecture/lectureList"; 
	}
}
