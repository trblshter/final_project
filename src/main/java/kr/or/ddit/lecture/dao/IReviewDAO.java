package kr.or.ddit.lecture.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReviewVO;

@Repository
public interface IReviewDAO {
	public List<ReviewVO> reviewList(String rv_recipient);
	
	/**
	 * 리뷰 작성
	 * @param reviewVO 리뷰VO
	 * @return
	 */
	public int insertReview(ReviewVO reviewVO);
	/**
	 * 리뷰 수정
	 * @param reviewVO
	 * @return
	 */
	public int updateReview(ReviewVO reviewVO);
	/**
	 * 리뷰 삭제
	 * @param rv_no
	 * @return
	 */
	public int deleteReview(int rv_no);
	/**
	 * 페이징할 리뷰 개수
	 * @param pagingVO
	 * @return
	 */
	public long selectReviewCount(PagingVO<ReviewVO> pagingVO);
	/**
	 * 페이징할 리뷰 리스트
	 * @param pagingVO
	 * @return
	 */
	public List<ReviewVO> selectReviewList(PagingVO<ReviewVO> pagingVO);
	/**
	 * 리뷰 상세보기
	 * @param rv_no
	 * @return
	 */
	public ReviewVO selectReview(int rv_no);
}
