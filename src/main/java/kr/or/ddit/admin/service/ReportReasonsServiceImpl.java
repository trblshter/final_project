package kr.or.ddit.admin.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.dao.IReportReasonsDAO;
import kr.or.ddit.vo.ReportreasonsVO;

@Service
public class ReportReasonsServiceImpl implements IReportReasonsService {
	@Inject
	IReportReasonsDAO dao;

	@Override
	public List<ReportreasonsVO> retrieveReportReasonsList() {
		return dao.selectReportReasonsList();
	}

}
