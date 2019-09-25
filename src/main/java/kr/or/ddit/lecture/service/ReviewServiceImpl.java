package kr.or.ddit.lecture.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.messaging.simp.user.UserDestinationMessageHandler;
import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CommonException;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.lecture.dao.IReviewDAO;
import kr.or.ddit.user.dao.IUserDAO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReviewVO;

@Service
public class ReviewServiceImpl implements IReviewService{
	
	@Inject
	IReviewDAO dao;
	@Inject
	IUserDAO userDao;
	
	@Override
	public List<ReviewVO> reviewList(String rv_recipient) {
		return dao.reviewList(rv_recipient);
	}

	@Override
	public ServiceResult createReview(ReviewVO reviewVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertReview(reviewVO);
		if(userDao.selectUser(reviewVO.getRv_recipient())==null) throw new UserNotFoundException(reviewVO.getRv_recipient() + "인 회원이 존재하지 않습니다.");
		if(cnt>0) result = ServiceResult.OK; 
		
		return result;
	}

	@Override
	public ServiceResult modifyReview(ReviewVO reviewVO) {
		ServiceResult result = ServiceResult.FAILED;
		ReviewVO reviewCheckVO = dao.selectReview(reviewVO.getRv_no());
		int cnt = dao.updateReview(reviewVO);
		if(reviewCheckVO==null) throw new CommonException(reviewVO.getRv_no() + "번인 리뷰가 존재하지 않습니다.");
		if(cnt>0) result = ServiceResult.OK;
		
		return result;
	}

	@Override
	public ServiceResult removeReview(int rv_no) {
		ServiceResult result = ServiceResult.FAILED;
		ReviewVO reviewCheckVO = dao.selectReview(rv_no);
		int cnt = dao.deleteReview(rv_no);
		if(reviewCheckVO==null) throw new CommonException(rv_no + "번인 리뷰가 존재하지 않습니다.");
		if(cnt>0) result = ServiceResult.OK;
		
		return result;
	}

	@Override
	public long retrieveReviewCount(PagingVO<ReviewVO> pagingVO) {
		return dao.selectReviewCount(pagingVO);
	}

	@Override
	public List<ReviewVO> retrieveReviewList(PagingVO<ReviewVO> pagingVO) {
		return dao.selectReviewList(pagingVO);
	}

	@Override
	public ReviewVO retrieveReview(int rv_no) {
		return dao.selectReview(rv_no);
	}
	
}
