package kr.or.ddit.admin.service;

import java.util.List;

import kr.or.ddit.vo.ReportreasonsVO;

public interface IReportReasonsService {
	/**
	 * 신고사유들 리스트
	 * @return ReportReasonsVO list
	 */
	public List<ReportreasonsVO> retrieveReportReasonsList();
}
