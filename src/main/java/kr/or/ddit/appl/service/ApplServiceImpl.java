package kr.or.ddit.appl.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.appl.dao.IApplDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.ApplVO;
import kr.or.ddit.vo.PagingVO;

@Service
public class ApplServiceImpl implements IApplService{
	
	@Inject
	IApplDAO dao;

	@Override
	public ServiceResult createAppl(ApplVO applVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertAppl(applVO);
		if(cnt > 0) result = ServiceResult.OK;
		return result;
	}

	@Override
	public ApplVO retrieveAppl(ApplVO applVO) {
		return dao.selectAppl(applVO);
	}

	@Override
	public long retrieveApplCount(PagingVO<ApplVO> pagingVO) {
		return dao.selectApplCount(pagingVO);
	}

	@Override
	public List<ApplVO> retrieveApplList(PagingVO<ApplVO> pagingVO) {
		return dao.selectApplList(pagingVO);
	}
	
	
	
	
}
