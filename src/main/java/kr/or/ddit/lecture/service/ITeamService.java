package kr.or.ddit.lecture.service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.TeamVO;

public interface ITeamService {
	/**
	 * 선생님이 학생 모집할 때 생성되는 팀
	 * @param teamVO
	 * @return
	 */
	public ServiceResult createTeam(TeamVO teamVO);
	
	/**
	 * 학생이 선생님 모집할 때 생성되는 팀
	 * @param teamVO
	 * @return
	 */
	public TeamVO createLectureUserTeam(TeamVO teamVO);
	/**
	 * 팀 상세보기
	 * @param team_no 팀 번호
	 * @return
	 */
	public TeamVO retrieveTeam(String lt_no);
	
	public ServiceResult updateTeam(TeamVO teamVO);
	
	public ServiceResult updateSelectedTeam(TeamVO teamVO);
	
	public TeamVO retrieveTeam2(int teamNo);
	
	
}
