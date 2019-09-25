package kr.or.ddit.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.BlacklistVO;
import kr.or.ddit.vo.PagingVO;

@Repository
public interface IBlacklistDAO {
	/**
	 * 블랙리스트 개수
	 * @param pagingVO
	 * @return long
	 */
	public long selectBlackCount(PagingVO<BlacklistVO> pagingVO);
	/**
	 * 블랙리스트 출력
	 * @param pagingVO
	 * @return list
	 */
	public List<BlacklistVO> selectBlackList(PagingVO<BlacklistVO> pagingVO);
	/**
	 * 블랙리스트 상세보기
	 * @param bl_id 회원아이디
	 * @return 블랙리스트VO
	 */
	public BlacklistVO selectBlack(String bl_id);
	/**
	 * 블랙리스트 추가
	 * @param bl 블랙리스트VO
	 * @return int
	 */
	public int insertBlack(BlacklistVO bl);
	/**
	 * 블랙리스트 삭제
	 * @param bl_id 회원아이디
	 * @return int
	 */
	public int deleteBlack(String bl_id);
}
