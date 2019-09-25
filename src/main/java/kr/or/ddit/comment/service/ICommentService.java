package kr.or.ddit.comment.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.CommentVO;
import kr.or.ddit.vo.PagingVO;

public interface ICommentService {
	public ServiceResult createComment(CommentVO comment);
	
	public long retrieveCommentCount(PagingVO<CommentVO> pagingVO);
	public List<CommentVO> retrieveCommentList(PagingVO<CommentVO> pagingVO);
	public CommentVO selectComment(long cm_no);
	
	public ServiceResult modifyComment(CommentVO comment);
	public ServiceResult removeComment(long cm_no);
	
	public List<CommentVO> listAll(String bo_no);
}
