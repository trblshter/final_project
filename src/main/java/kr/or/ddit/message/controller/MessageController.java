package kr.or.ddit.message.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.message.service.IMessageService;
import kr.or.ddit.vo.MessageVO;
import kr.or.ddit.vo.PagingVO;

@Controller
@RequestMapping(value="/message")
public class MessageController {
	@Inject
	IMessageService service;
	
	@GetMapping(value="messageList", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<MessageVO> messageList(
			Model model,
			@RequestParam(name="page", defaultValue="1", required=false) long currentPage,
			@RequestParam(required=true) String loginId
			){
		PagingVO<MessageVO> pagingVO = new PagingVO<>(5,5);
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSearchWord(loginId);
		
		long totalRecord = service.retrieveMessageCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<MessageVO> list = service.retrieveMessageList(pagingVO);
		pagingVO.setDataList(list);
		pagingVO.setFuncName("messagePaging");
		
		model.addAttribute("pagingVO", pagingVO);
		return pagingVO;
	}
	
	@GetMapping(value="messageView", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public MessageVO selectMessage(
			@RequestParam(name="what", required=true) String msg_no
			){
		
		return service.retrieveMessage(msg_no);
	}
	
	@PostMapping(value="messageInsert",  produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> insert(
			MessageVO msg
			){
		ServiceResult result = service.createMessage(msg);
		Map<String, Object> resultMap = new HashMap<>();
		if(ServiceResult.OK.equals(result)) {
			resultMap.put("success", true);
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "실패");  
		}
		return resultMap;
	}
	
	@GetMapping(value="messageDelete", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> delete(
			@RequestParam(name="what",required=true) String msg_no
			){
		ServiceResult result = service.removeMessage(msg_no);
		Map<String, Object> resultMap = new HashMap<>();
		if(ServiceResult.OK.equals(result)) {
			resultMap.put("success", true);
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "실패");
		}
		
		return resultMap;
	}
	
	@GetMapping(value="messageCount", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public int unreadCount(@RequestParam(required=true) String loginId) {
		return service.unreadMessageCount(loginId);
	}
	
}
