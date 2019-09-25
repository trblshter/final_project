package kr.or.ddit.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReportreasonVO;

@Repository
public interface IReportReasonDAO {
	/**
	 * 신고사유 개수
	 * @param pagingVO
	 * @return long
	 */
	public long selectRRCount(PagingVO<ReportreasonVO> pagingVO);
	/**
	 * 신고사유 리스트
	 * @param pagingVO
	 * @return 신고사유 List
	 */
	public List<ReportreasonVO> selectRRList(PagingVO<ReportreasonVO> pagingVO);
	/**
	 * 신고사유 상세보기
	 * @param rr_no 신고사유 번호
	 * @return ReportreasonVO
	 */
	public ReportreasonVO selectReportReason(int rr_no);
	/**
	 * 신고사유 삭제
	 * @param rr_no 신고사유 번호
	 * @return
	 */
	public int deleteReport(int rr_no);
	/**
	 * 신고사유 추가
	 * @param reportreasonVO
	 * @return
	 */
	public int insertReport(ReportreasonVO rr);
	
}
