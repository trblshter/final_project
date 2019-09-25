package kr.or.ddit.lecture.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.service.IReviewService;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReviewVO;

@Controller
public class ReviewController {
	@Inject
	IReviewService service;
	@GetMapping(value="/tutee/mypage/review")
	public String ReviewList() {
		return "tutee/mypage/review";
	}
	@GetMapping(value="/tutor/mypage/review")
	public String ReviewTutorList() {
		return "tutor/mypage/review";
	}
	
	@PostMapping(value="/review/reviewInsert", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> insert(
			ReviewVO reviewVO
			){
		ServiceResult result = service.createReview(reviewVO);
		Map<String, Object> resultMap = new HashMap<>();
		if(ServiceResult.OK.equals(result)) {
			resultMap.put("success", true);
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "실패");
		}
		return resultMap;
	}
	
	@GetMapping(value="/review/reviewList", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<ReviewVO> reviewList(
			@RequestParam(name="page", defaultValue="1", required=false) long currentPage,
			@RequestParam(required=false) String loginId,
			@RequestParam(required=false) String userType
			){
		PagingVO<ReviewVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);
		
		pagingVO.setSearchType(userType);
		pagingVO.setSearchWord(loginId);
		
		long totalRecord = service.retrieveReviewCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<ReviewVO> list = service.retrieveReviewList(pagingVO);
		pagingVO.setDataList(list);
		
		return pagingVO;
	}
	
	@GetMapping(value="/review/reviewView", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ReviewVO reviewView(
			@RequestParam(name="what", required=true) int rv_no
			) {
		ReviewVO reviewVO = service.retrieveReview(rv_no);
		
		return reviewVO;
	}
	
	
	@GetMapping(value="/review/reviewUpdate", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> update(
			ReviewVO reviewVO,
			@RequestParam(name="what", required=true) int rv_no
			){
		reviewVO.setRv_no(rv_no);
		ServiceResult result = service.modifyReview(reviewVO);
		Map<String, Object> resultMap = new HashMap<>();
		if(ServiceResult.OK.equals(result)) {
			resultMap.put("success", true);
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "실패");
		}
		return resultMap;
	}
	
	@GetMapping(value="/review/reviewDelete", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> delete(
			@RequestParam(name="what", required=true) int rv_no
			){
		ServiceResult result = service.removeReview(rv_no);
		Map<String, Object> resultMap = new HashMap<>();
		if(ServiceResult.OK.equals(result)) {
			resultMap.put("success", true);
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "실패");
		}
		return resultMap;
	}
	
}
