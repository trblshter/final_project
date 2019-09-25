package kr.or.ddit.appl.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.ApplVO;
import kr.or.ddit.vo.PagingVO;

public interface IApplService {
	public ServiceResult createAppl(ApplVO applVO);
	public ApplVO retrieveAppl(ApplVO applVO);
	/**
	 * 결제신청 개수
	 * @param pagingVO
	 * @return
	 */
	public long retrieveApplCount(PagingVO<ApplVO> pagingVO);
	/**
	 * 결제신청 리스트
	 * @param pagingVO
	 * @return
	 */
	public List<ApplVO> retrieveApplList(PagingVO<ApplVO> pagingVO);
}
