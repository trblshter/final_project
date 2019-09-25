package kr.or.ddit.admin.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReportreasonVO;

public interface IReportReasonService {
	/**
	 * 신고사유 개수
	 * @param pagingVO
	 * @return long
	 */
	public long retrieveRRCount(PagingVO<ReportreasonVO> pagingVO);
	/**
	 * 신고사유 리스트
	 * @param pagingVO
	 * @return 신고사유 List
	 */
	public List<ReportreasonVO> retrieveRRList(PagingVO<ReportreasonVO> pagingVO);
	/**
	 * 신고사유 상세보기
	 * @param rr_no 신고사유 번호
	 * @return ReportreasonVO
	 */
	public ReportreasonVO RetrieveReportReason(int rr_no);
	/**
	 * 신고사유 삭제
	 * @param rr_no 신고사유 번호
	 * @return
	 */
	public ServiceResult removeReport(int rr_no);
	/**
	 * 신고사유 추가
	 * @param reportreasonVO
	 * @return
	 */
	public ServiceResult createReport(ReportreasonVO rr);
}
