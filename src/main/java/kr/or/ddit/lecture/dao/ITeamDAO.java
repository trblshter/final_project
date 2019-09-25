package kr.or.ddit.lecture.dao;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.TeamVO;

@Repository
public interface ITeamDAO {
	public int insertTeam(TeamVO teamVO);
	
	public int insertLectureUserTeam(TeamVO teamVO);
	/**
	 * 팀 수정
	 * @param teamVO 팀VO
	 * @return
	 */
	public int updateTeam(TeamVO teamVO);
	/**
	 * 팀 상세보기
	 * @param team_no 팀 번호
	 * @return
	 */
	public TeamVO selectTeam(String lt_no);
	
	public int updateSelectedTeam(TeamVO teamVO);
	
	public TeamVO selectTeam2(int teamNo);
}
