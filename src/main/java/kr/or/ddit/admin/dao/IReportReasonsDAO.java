package kr.or.ddit.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.ReportreasonsVO;

@Repository
public interface IReportReasonsDAO {
	/**
	 * 신고사유들 리스트
	 * @return ReportReasonsVO list
	 */
	public List<ReportreasonsVO> selectReportReasonsList();
}
