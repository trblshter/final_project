package kr.or.ddit.admin.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.dao.IReportReasonDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CommonException;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReportreasonVO;

@Service
public class ReportReasonServiceImpl implements IReportReasonService {
	@Inject
	IReportReasonDAO dao;

	@Override
	public long retrieveRRCount(PagingVO<ReportreasonVO> pagingVO) {
		return dao.selectRRCount(pagingVO);
	}

	@Override
	public List<ReportreasonVO> retrieveRRList(PagingVO<ReportreasonVO> pagingVO) {
		return dao.selectRRList(pagingVO);
	}

	@Override
	public ReportreasonVO RetrieveReportReason(int rr_no) {
		ReportreasonVO rr = dao.selectReportReason(rr_no);
		if(rr==null) throw new CommonException(rr_no + "번 신고게시글이 없습니다.");
		
		return rr;
	}

	@Override
	public ServiceResult removeReport(int rr_no) {
		ReportreasonVO rr = dao.selectReportReason(rr_no);
		if(rr==null) throw new CommonException(rr_no + "번 신고게시글이 없습니다.");
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.deleteReport(rr_no);
		if(cnt>0) result = ServiceResult.OK;
		
		return result;
	}
	@Override
	public ServiceResult createReport(ReportreasonVO rr) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertReport(rr);
		if(cnt>0) result = ServiceResult.OK;
		
		return result;
	}

}
