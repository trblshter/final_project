package kr.or.ddit.admin.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.appl.service.IApplService;
import kr.or.ddit.vo.ApplVO;
import kr.or.ddit.vo.PagingVO;

@Controller
public class ApplAdminController {
	@Inject
	IApplService service;
	
	@GetMapping(value="/admin/appl")
	public String applList() {
		return "admin/applView";
	}
	
	@GetMapping(value="/admin/applList",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<ApplVO> applList(
			Model model,
			@RequestParam(name="page", defaultValue="1", required=false) long currentPage
			) {
		PagingVO<ApplVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);
		
		long totalRecord = service.retrieveApplCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<ApplVO> list = service.retrieveApplList(pagingVO);
		pagingVO.setDataList(list);
		
		return pagingVO;
	}
	
	
	
}
