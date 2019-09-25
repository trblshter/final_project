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


import kr.or.ddit.admin.service.IBlacklistService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.BlacklistVO;
import kr.or.ddit.vo.PagingVO;

@RestController
@RequestMapping(value="/admin/black", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BlacklistController {
	@Inject
	IBlacklistService service;
	
	@PostMapping
	public Map<String, Object> insert(
		String id, String reason
			){
		BlacklistVO bl = new BlacklistVO();
		bl.setBl_id(id);
		bl.setBl_reason(reason);
		ServiceResult result = service.createBlack(bl);
		Map<String, Object> resultMap = new HashMap<>();
		if(ServiceResult.OK.equals(result)) {
			resultMap.put("success", true);
			resultMap.put("blacklist", bl);
		}else if(ServiceResult.PKDUPLICATED.equals(result)) {
			resultMap.put("success", false);
			resultMap.put("message", "이미 추가된 회원");
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "실패");
		}
		return resultMap;
	} 
	
	@GetMapping
	public PagingVO<BlacklistVO> blacklist(
			Model model,
			@RequestParam(name="page", defaultValue="1", required=false) long currentPage,
			@RequestParam(required=false) String searchWord
			){
		PagingVO<BlacklistVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSearchWord(searchWord);
		
		long totalRecord = service.retrieveBlackCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<BlacklistVO> list = service.retrieveBlackList(pagingVO);
		pagingVO.setDataList(list);
		
		model.addAttribute("pagingVO", pagingVO);
		
		return pagingVO;
		
	}
	
	@DeleteMapping("{bl_id}")
	public Map<String, Object> delete(
			@PathVariable(required=true) String bl_id
			){
		ServiceResult result = service.removeBlack(bl_id);
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
