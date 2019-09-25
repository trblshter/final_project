package kr.or.ddit.comment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.CommentVO;
import kr.or.ddit.vo.PagingVO;

@Repository
public interface ICommentDAO {
	// 댓글 작성
	public int insertComment(CommentVO comment);
	
	// 댓글 선택
	public CommentVO selectComment(long cm_no);
	
	// 댓글 수정
	public int updateComment(CommentVO comment);
	
	// 댓글 삭제
	public int deleteComment(long comment);
	
	// 댓글 조회(출력)
	public List<CommentVO> listAll(String bo_no);
	
	// 페이징
	public List<CommentVO> selectCommentList(PagingVO<CommentVO> pagingVO);
	public long selectCommentCount(PagingVO<CommentVO> pagingVO);

}
