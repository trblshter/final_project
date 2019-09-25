package kr.or.ddit.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.user.service.IUserService;
import kr.or.ddit.vo.MessageVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.UserVO;

@Controller
public class CertificationController {
	@Inject
	IUserService service;
	
	@GetMapping(value="/admin/certification")
	public String certificationList() {
		return "admin/certificationView";
	}
	
	@GetMapping(value="/admin/authList",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<UserVO> authList(
			Model model,
			@RequestParam(name="page", defaultValue="1", required=false) long currentPage,
			@RequestParam(required=false) String searchWord
			){
		PagingVO<UserVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);;
		pagingVO.setSearchWord(searchWord);
		
		long totalRecord = service.retrieveAuthTutorCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<UserVO> list = service.retrieveAuthTutorList(pagingVO);
		pagingVO.setDataList(list);
		
		model.addAttribute("pagingVO", pagingVO);
		
		return pagingVO;
	}
	
	@GetMapping(value="/admin/authView",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public UserVO selectTutor(
			@RequestParam(name="who", required=true) String user_id
			) {
		return service.retrieveAuthTutor(user_id);
	}
	
	@GetMapping(value="/admin/authOk",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> auth(
			@RequestParam(name="who", required=true) String user_id
			){
		ServiceResult result = service.authenticateTutor(user_id);
		Map<String, Object> resultMap = new HashMap<>();
		if(ServiceResult.OK.equals(result)) {
			resultMap.put("success", true);
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "실패");
		}
		return resultMap;
	}
	
	@GetMapping(value="/admin/authCancel",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> delete(
			MessageVO msg
			){
		ServiceResult result = service.authCancelTutor(msg);
		
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
