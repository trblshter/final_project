package kr.or.ddit.admin.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.BlacklistVO;
import kr.or.ddit.vo.PagingVO;

public interface IBlacklistService {
	/**
	 * 블랙리스트 개수
	 * @param pagingVO
	 * @return long
	 */
	public long retrieveBlackCount(PagingVO<BlacklistVO> pagingVO);
	/**
	 * 블랙리스트 출력
	 * @param pagingVO
	 * @return list
	 */
	public List<BlacklistVO> retrieveBlackList(PagingVO<BlacklistVO> pagingVO);
	/**
	 * 블랙리스트 상세보기
	 * @param bl_id 회원아이디
	 * @return
	 */
	public BlacklistVO retrieveBlack(String bl_id);
	/**
	 * 블랙리스트 추가
	 * @param bl 블랙리스트VO
	 * @return int
	 */
	public ServiceResult createBlack(BlacklistVO bl);
	/**
	 * 블랙리스트 삭제
	 * @param bl_id 회원아이디
	 * @return int
	 */
	public ServiceResult removeBlack(String bl_id);
}
