package kr.or.ddit.lecture.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CommonException;
import kr.or.ddit.lecture.dao.ITeacherCommentDAO;
import kr.or.ddit.user.dao.IUserDAO;
import kr.or.ddit.user.service.IAuthenticateService;
import kr.or.ddit.vo.LectureCommentVO;
import kr.or.ddit.vo.PagingVO;

@Service
public class TeacherCommentServiceImpl implements ITeacherCommentService{
	
	@Inject
	ITeacherCommentDAO dao;

	@Override
	public List<LectureCommentVO> retrieveLectureCommentList(PagingVO<LectureCommentVO> pagingVO) {
		return dao.selectTeacherCommentList(pagingVO);
	}

	@Override
	public ServiceResult createLectureComment(LectureCommentVO commentVO) {
		LectureCommentVO lectureCommentVO = dao.retrieveLectureComment(commentVO);
		ServiceResult result = ServiceResult.FAILED;
		if(lectureCommentVO == null) {
			int cnt = dao.insertTeacherComment(commentVO);
			if(cnt > 0) result = ServiceResult.OK;
		}else {
			return result;
		}
		return result;
	}

	@Override
	public long retrieveTeacherCommentCount(PagingVO<LectureCommentVO> pagingVO) {
		return dao.retrieveTeacherCommentCount(pagingVO);
	}

	@Override
	public LectureCommentVO retrieveLectureComment(LectureCommentVO lectureCommentVO) {
		LectureCommentVO commentVO = dao.retrieveLectureComment(lectureCommentVO);
		if(commentVO == null) {
			throw new CommonException("코멘트 없음");
		}
		
		return commentVO;
	}

}
