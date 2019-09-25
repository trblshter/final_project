package kr.or.ddit.comment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.comment.service.ICommentService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.CommentVO;

// 댓글 컨트롤러

@Controller
@RequestMapping("/comment/*")
public class CommentController {
	@Inject
	ICommentService commentService;
	
	// 댓글 등록
	@RequestMapping(value="commentInsert.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> insert(@ModelAttribute CommentVO vo) throws Exception{
		ServiceResult result = commentService.createComment(vo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(ServiceResult.OK.equals(result)) {
			List<CommentVO> commentList = commentService.listAll(vo.getBo_no());
			resultMap.put("success", true);
			resultMap.put("commentList", commentList);
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "서버 오류");
		}
		
		//return "redirect:/board/view.do?bo_no=" + vo.getBo_no();
		 return resultMap;
	}
	
	
	// 댓글 삭제
	@RequestMapping(value="commentDelete.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> delete(
			@RequestParam("cmNo") long cmNo,
			@RequestParam("boNo") String boNo
			) throws Exception{
		ServiceResult result = commentService.removeComment(cmNo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(ServiceResult.OK.equals(result)) { // 삭제 성공
			List<CommentVO> commentList = commentService.listAll(boNo); // 기존 리스트를 갱신하기 위해
			resultMap.put("success", true); // 
			resultMap.put("commentList", commentList);
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "서버 오류");
		}
		
		return resultMap;
		//return "redirect:/board/view.do?bo_no=" + vo.getBo_no().substring(0, 2);
	}
	
	// 댓글 수정
	@RequestMapping(value="commentUpdate.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> update(@ModelAttribute CommentVO vo) throws Exception{
		ServiceResult result = commentService.modifyComment(vo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(ServiceResult.OK.equals(result)) {
			resultMap.put("success", true);
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "서버 오류");
		}
		
		 return resultMap;
	}
}
