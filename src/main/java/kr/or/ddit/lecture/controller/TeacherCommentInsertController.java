package kr.or.ddit.lecture.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.service.ITeacherCommentService;
import kr.or.ddit.lecture.service.ITeacherService;
import kr.or.ddit.vo.LectureCommentVO;
import kr.or.ddit.vo.LectureUserVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;

@Controller
@RequestMapping("/teacher")
public class TeacherCommentInsertController {
	
	@Inject
	ITeacherCommentService service;
	
	@RequestMapping(value="teacherCommentInsert.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> ajaxInsert(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			@ModelAttribute LectureCommentVO lectureCommentVO,
 			Model model
			) {
		String message = null;
		Map<String, Object> map = new LinkedHashMap<>();
		
		PagingVO<LectureCommentVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);
		
		ServiceResult result = service.createLectureComment(lectureCommentVO);
		pagingVO.setSearchVO(lectureCommentVO);
		
		long totalRecord = service.retrieveTeacherCommentCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		if(ServiceResult.OK.equals(result)) {
			List<LectureCommentVO> list = service.retrieveLectureCommentList(pagingVO);
			pagingVO.setDataList(list);
		}else if(ServiceResult.FAILED.equals(result)){
			List<LectureCommentVO> list = service.retrieveLectureCommentList(pagingVO);
			pagingVO.setDataList(list);
			
			message = "이미 등록하셨습니다 선생님";
			map.put("message", message);
		}
		
//		
//		System.out.println(pagingVO.getTotalRecord());
//		List<LecturetutorVO> list = service.retrieveLectureList(pagingVO);
//		pagingVO.setDataList(list);
//		System.out.println(pagingVO.getStartPage());
		
		System.out.println(pagingVO.getDataList());
//		
		model.addAttribute("pagingVO", pagingVO);
		model.addAttribute("teacherComment", lectureCommentVO);
		
		map.put("pagingVO", pagingVO);
		
		return map;
	}
		
}
