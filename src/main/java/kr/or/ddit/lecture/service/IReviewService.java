package kr.or.ddit.lecture.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReviewVO;

public interface IReviewService {
	public List<ReviewVO> reviewList(String rv_recipient);
	
	/**
	 * 리뷰 작성
	 * @param reviewVO 리뷰VO
	 * @return
	 */
	public ServiceResult createReview(ReviewVO reviewVO);
	/**
	 * 리뷰 수정
	 * @param reviewVO
	 * @return
	 */
	public ServiceResult modifyReview(ReviewVO reviewVO);
	/**
	 * 리뷰 삭제
	 * @param rv_no
	 * @return
	 */
	public ServiceResult removeReview(int rv_no);
	/**
	 * 페이징할 리뷰 개수
	 * @param pagingVO
	 * @return
	 */
	public long retrieveReviewCount(PagingVO<ReviewVO> pagingVO);
	/**
	 * 페이징할 리뷰 리스트
	 * @param pagingVO
	 * @return
	 */
	public List<ReviewVO> retrieveReviewList(PagingVO<ReviewVO> pagingVO);
	/**
	 * 리뷰 상세보기
	 * @param rv_no
	 * @return
	 */
	public ReviewVO retrieveReview(int rv_no);
}
