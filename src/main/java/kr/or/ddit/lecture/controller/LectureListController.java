package kr.or.ddit.lecture.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.lecture.service.ILectureService;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;

@Controller
@RequestMapping("/lecture")
public class LectureListController {
	
	@Inject
	ILectureService service;
	
	@RequestMapping(value="lectureList.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<LecturetutorVO> ajaxList(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			@RequestParam(required=false) String searchType,
			@RequestParam(required=false) String searchWord,
 			Model model
			) {
		list(currentPage, searchType, searchWord, "ST", model);  // ST ==> 학생  종범 추가 
		
		return  (PagingVO<LecturetutorVO>) model.asMap().get("pagingVO");
	}
	
	@RequestMapping("lectureList.do")
	public String list(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			@RequestParam(required=false) String searchType,
			@RequestParam(required=false) String searchWord,
			@RequestParam(required=false) String what,
			Model model
			) {
		
		PagingVO<LecturetutorVO> pagingVO = new PagingVO<>(8,5);
		pagingVO.setSearchType(searchType);
		pagingVO.setSearchWord(searchWord);
		pagingVO.setCurrentPage(currentPage);
		
		long totalRecord = service.retrieveLectureCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		System.out.println("total : " + totalRecord);
		
		System.out.println(pagingVO.getTotalRecord());
		List<LecturetutorVO> list = service.retrieveLectureList(pagingVO);
		System.out.println(list);
		pagingVO.setDataList(list);
		System.out.println(pagingVO.getDataList());
		
		model.addAttribute("pagingVO", pagingVO);
		model.addAttribute("what", what); // 종범 추가
		
		return "lecture/lectureList"; 
	}
}
