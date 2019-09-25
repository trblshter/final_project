package kr.or.ddit.comment.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.comment.dao.ICommentDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.CommentVO;
import kr.or.ddit.vo.PagingVO;

@Service
public class CommentServiceImpl implements ICommentService {
	// 싱글턴
	private CommentServiceImpl() {}
	private static CommentServiceImpl service;
	public static CommentServiceImpl getInsetance() {
		if(service == null) service = new CommentServiceImpl();
		return service;
	}
	
	@Inject
	ICommentDAO commentDAO;
	
	// 추가(작성)
	@Override
	public ServiceResult createComment(CommentVO comment) {
		int cnt = commentDAO.insertComment(comment);
		ServiceResult result = ServiceResult.FAILED;
		if(cnt>0) result = ServiceResult.OK;
		return result;
	}

	// 페이징을 위한 카운트
	@Override
	public long retrieveCommentCount(PagingVO<CommentVO> pagingVO) {
		return commentDAO.selectCommentCount(pagingVO);
	}
	// 페이징을 위한 리스트
	@Override
	public List<CommentVO> retrieveCommentList(PagingVO<CommentVO> pagingVO) {
		return commentDAO.selectCommentList(pagingVO);
	}

	// 댓글 수정
	@Override
	public ServiceResult modifyComment(CommentVO comment) {
		int cnt = commentDAO.updateComment(comment);
		ServiceResult result = ServiceResult.FAILED;
		if(cnt>0) result = ServiceResult.OK;
		return result;
	}

	// 댓글 삭제
	@Override
	public ServiceResult removeComment(long cm_no) {
		int cnt = commentDAO.deleteComment(cm_no);
		ServiceResult result = ServiceResult.FAILED;
		if(cnt>0) result = ServiceResult.OK;
		return result;
	}

	@Override
	public List<CommentVO> listAll(String bo_no) {
		return commentDAO.listAll(bo_no);
	}

	@Override
	public CommentVO selectComment(long cm_no) {
		return commentDAO.selectComment(cm_no);
	}
	
}
