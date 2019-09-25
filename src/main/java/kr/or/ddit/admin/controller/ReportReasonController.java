package kr.or.ddit.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.service.IReportReasonService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReportreasonVO;

@RestController
@RequestMapping(value="/admin/reportReason", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReportReasonController {
	@Inject
	IReportReasonService service;
	
	@PostMapping
	public Map<String, Object> insert(ReportreasonVO rr){
		ServiceResult result = service.createReport(rr);
		Map<String, Object> resultMap = new HashMap<>();
		if(ServiceResult.OK.equals(result)) {
			resultMap.put("success", true);
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "실패");
		}
		System.out.println(rr.getRr_image());
		
		return resultMap;
	}
	
	@GetMapping
	public PagingVO<ReportreasonVO> selectReportList(
			Model model,
			@RequestParam(name="page", defaultValue="1", required=false) long currentPage
			) {
		PagingVO<ReportreasonVO> pagingVO = new PagingVO<>();
		
		pagingVO.setCurrentPage(currentPage);
		
		long totalRecord = service.retrieveRRCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<ReportreasonVO> list = service.retrieveRRList(pagingVO);
		pagingVO.setDataList(list);
		
		model.addAttribute("pagingVO", pagingVO);
		
		return pagingVO;
	}
	
	@GetMapping("{rr_no}")
	public ReportreasonVO selectReport(
			@PathVariable(required=true) int rr_no
			) {
		return service.RetrieveReportReason(rr_no);
	}
	
	@DeleteMapping("{rr_no}")
	public Map<String, Object> deleteReport(
			@PathVariable(required=true) int rr_no
			){
		ServiceResult result = service.removeReport(rr_no);
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
