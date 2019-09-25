package kr.or.ddit.board.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.attach.service.IAttachService;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.comment.service.ICommentService;
import kr.or.ddit.vo.AttachmentVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.UserVO;

@Controller
public class BoardController {
	// 의존관계 주입 => BoardServiceImpl 생성
	// IoC 의존 관계 역전
	@Inject	
	IBoardService boardService;
	@Inject
	ICommentService commentService;
	@Inject
	IAttachService attachService;
	
	// 01. 게시글 목록
	/*
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam String type) throws Exception{
		List<BoardVO> list = boardService.listAll(type);
		// ModelAndView - 모델과 뷰
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/list"); // 뷰를 list.jsp로 설정
		mav.addObject("list", list); // 데이터를 저장
		mav.addObject("type", type); // 게시판 구분 값
		return mav;
	}
	*/
	
	@RequestMapping(value="/board/list.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<BoardVO> ajaxList (
		@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
		@RequestParam(required=false) String type,
		@RequestParam(required=false) String searchType, // required = false필수가 아니다.
		@RequestParam(required=false) String searchWord,
		@RequestHeader(name="Accept", required=false) String accept,
		@CookieValue(name="JSESSIONID", required=false) String sessionId,
		Model model
	){
		list(currentPage, type, searchType, searchWord, accept, sessionId, model);
		return (PagingVO<BoardVO>)model.asMap().get("pagingVO");
	}
		
	@RequestMapping("/board/list.do")
	public String list(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			@RequestParam(required=false) String type,
			@RequestParam(required=false) String searchType,
			@RequestParam(required=false) String searchWord,
			@RequestHeader(name="Accept", required=false) String accept, // 헤더
			@CookieValue(name="JSESSIONID", required=false) String sessionId, // 옵션 일때			
			Model model
//			@CookieValue("JSESSIONID") String sessionId // 필수 일때
		) {				
		PagingVO<BoardVO> pagingVO = new PagingVO<>();
		pagingVO.setSearchType(searchType);
		pagingVO.setSearchWord(searchWord);
		pagingVO.setType(type);
		// 1. 
		pagingVO.setCurrentPage(currentPage);
		
		long totalRecord = boardService.retrieveBoardCount(pagingVO);
		// 2. 
		pagingVO.setTotalRecord(totalRecord);
		
		List<BoardVO> boardList = boardService.retrieveBoardList(pagingVO);
		pagingVO.setDataList(boardList);
		
		model.addAttribute("pagingVO", pagingVO);
		
		return "board/list";		
	}
	
	
	//===================================================
	
	// 02_01. 게시글 작성 화면
	// @RequestMapping("board/write.do");
	// value="", method="전송방식"
	@RequestMapping(value="/board/write.do", method=RequestMethod.GET)
	public ModelAndView write(@RequestParam String type) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/write"); // 뷰를 write.jsp로 설정
		mav.addObject("type", type); // 게시판 구분 값
		return mav; // write.jsp로 이동
	}
	
	// 02_02. 게시글 작성 처리
	@RequestMapping(value="/board/insert.do", method=RequestMethod.POST)
	public String insert(@ModelAttribute BoardVO vo) throws Exception{
		boardService.create(vo);
		return "redirect:list.do?type=" + vo.getType();
	}
	
	// 03. 게시글 상세내용 조회, 게시글 조회수 증가 처리
	//@RequestParam : get/post방식으로 전달된 변수 1개
	// HttpSession 세션객체
	@RequestMapping(value="/board/view.do", method=RequestMethod.GET)
	public ModelAndView view(@RequestParam String bo_no, HttpSession session) throws Exception{
		// 조회수 증가 처리
		boardService.increaseViewcnt(bo_no, session);
		// 모델(데이터)+뷰(화면)를 함계 전달하는 객체
		ModelAndView mav = new ModelAndView();
		// 뷰의 이름
		mav.setViewName("board/view");
		// 뷰에 전달할 데이터
		BoardVO boardVO = boardService.read(bo_no);
		List<AttachmentVO> attachList = attachService.selectBonoAttach(bo_no);
		boardVO.setAttachList(attachList);
		mav.addObject("dto", boardVO);
		mav.addObject("comment", commentService.listAll(bo_no));
		
		return mav;
	}
	
	// 04. 게시글 수정
	// 폼에서 입력한 내용들은 @ModelAttribute BoardVO vo 로 전달됨
	@RequestMapping(value="/board/update.do", method=RequestMethod.POST)
	public String update(@ModelAttribute BoardVO vo) throws Exception{
		boardService.update(vo);
		return "redirect:list.do?type="+vo.getBo_no().substring(0,2); // 앞 두글자 때어내서 VO에 넘겨줌
	}
	
	// 05. 게시글 삭제
	@RequestMapping("/board/delete.do")
	public String delete(@ModelAttribute BoardVO vo) throws Exception{
		boardService.delete(vo.getBo_no());
		return "redirect:list.do?type="+vo.getBo_no().substring(0,2);
	}
	
	// 페이징
	@GetMapping(value="/board/boardList.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<BoardVO> ajaxList(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			@RequestParam(required=false) String searchType,
			@RequestParam(required=false) String searchWord,
			Model model
			){
		list(currentPage, searchType, searchWord, model);
		return (PagingVO<BoardVO>) model.asMap().get("pagingVO");
	}
	@GetMapping("/board/boardList.do")
	public String list(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			@RequestParam(required=false) String searchType,
			@RequestParam(required=false) String searchWord,
			Model model
			) {
		PagingVO<BoardVO> pagingVO = new PagingVO<>();
		pagingVO.setSearchType(searchType);
		pagingVO.setSearchWord(searchWord);
		pagingVO.setCurrentPage(currentPage);
		long totalRecord = boardService.retrieveBoardCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<BoardVO> boardList = boardService.retrieveBoardList(pagingVO);
		pagingVO.setDataList(boardList);
		model.addAttribute("pagingVO", pagingVO);
		return "board/boardList";
	}
	
	
//	@GetMapping(value="myBoard.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE) // Ajax 사용하기
//	@ResponseBody // 결과를 받을 것.
//	public List<BoardVO> userBoardList(HttpSession session) {
//		UserVO loninUser = (UserVO)session.getAttribute("loginUser"); // 로그인 한사람의 객체
//		List<BoardVO> myBoardList = boardService.selectUserBoard(loninUser.getUser_id());
//		return myBoardList;
//	}
	
	// 사용자가 쓴 글목록 페이징
		@GetMapping(value="/{user_type}/mypage/myBoardList.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
		@ResponseBody
		public PagingVO<BoardVO> ajaxMyBoardList(
				@PathVariable("user_type") String user_type,
				@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
				@RequestParam(required=false) String searchType,
				@RequestParam(required=false) String searchWord,
				HttpSession session,
				Model model
				){
			myBoardList(user_type, currentPage, searchType, searchWord, session, model);
			return (PagingVO<BoardVO>) model.asMap().get("pagingVO");
		}
		
		@GetMapping("/{user_type}/mypage/myBoardList.do")
		public String myBoardList(
				@PathVariable("user_type") String user_type,
				@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
				@RequestParam(required=false) String searchType,
				@RequestParam(required=false) String searchWord,
				HttpSession session,
				Model model
				) {
			UserVO loginUser = (UserVO)session.getAttribute("loginUser"); // 로그인 한사람의 객체
			PagingVO<BoardVO> pagingVO = new PagingVO<>();
			pagingVO.setSearchType(searchType);
			pagingVO.setSearchWord(searchWord);
			pagingVO.setCurrentPage(currentPage);
			pagingVO.setBo_writer(loginUser.getUser_id());
			
			long totalRecord = boardService.retrieveMyBoardCount(pagingVO);
			pagingVO.setTotalRecord(totalRecord);
			List<BoardVO> boardList = boardService.retrieveMyBoardList(pagingVO);
			pagingVO.setDataList(boardList);
			model.addAttribute("pagingVO", pagingVO);
			return user_type +"/mypage/myBoardList";
		}
	
}