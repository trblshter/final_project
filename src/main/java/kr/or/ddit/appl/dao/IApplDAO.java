package kr.or.ddit.appl.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.ApplVO;
import kr.or.ddit.vo.PagingVO;

@Repository
public interface IApplDAO {
	public int insertAppl(ApplVO applVO);
	public ApplVO selectAppl(ApplVO applVO);
	/**
	 * 결제신청 개수
	 * @param pagingVO
	 * @return
	 */
	public long selectApplCount(PagingVO<ApplVO> pagingVO);
	/**
	 * 결제신청 리스트
	 * @param pagingVO
	 * @return
	 */
	public List<ApplVO> selectApplList(PagingVO<ApplVO> pagingVO);
}
