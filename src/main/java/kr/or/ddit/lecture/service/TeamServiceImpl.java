package kr.or.ddit.lecture.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import kr.or.ddit.appl.dao.IApplDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.dao.ITeamDAO;
import kr.or.ddit.vo.ApplVO;
import kr.or.ddit.vo.TeamVO;

@Service
public class TeamServiceImpl implements ITeamService{

	@Inject
	ITeamDAO dao;
	@Inject
	IApplDAO applDao;
	
	@Override
	public ServiceResult createTeam(TeamVO teamVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertTeam(teamVO);
		if(cnt > 0) result = ServiceResult.OK;
		
		return result;
	}

	@Override
	public TeamVO retrieveTeam(String lt_no) {
		return dao.selectTeam(lt_no);
	}

	@Override
	public TeamVO createLectureUserTeam(TeamVO teamVO) {
		int cnt = dao.insertLectureUserTeam(teamVO);
		if(cnt > 0) {
			return teamVO;
		}else {
			return null;
		}
		
		
	}

	@Override
	public ServiceResult updateTeam(TeamVO teamVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.updateTeam(teamVO);
		if(cnt > 0) result = ServiceResult.OK;
		
		return result;
	}

	@Override
	public ServiceResult updateSelectedTeam(TeamVO teamVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.updateSelectedTeam(teamVO);
		if(cnt > 0) {
			List<String> userId = new ArrayList<>();
			TeamVO teamMemVO = dao.selectTeam(teamVO.getLt_no());
			if(teamMemVO.getTeam_member2()==null) {
				userId.add(teamMemVO.getTeam_member1());
			}else if(teamMemVO.getTeam_member3()==null) {
				userId.add(teamMemVO.getTeam_member1());
				userId.add(teamMemVO.getTeam_member2());
			}else if(teamMemVO.getTeam_member4()==null) {
				userId.add(teamMemVO.getTeam_member1());
				userId.add(teamMemVO.getTeam_member2());
				userId.add(teamMemVO.getTeam_member3());
			}else {
				userId.add(teamMemVO.getTeam_member1());
				userId.add(teamMemVO.getTeam_member2());
				userId.add(teamMemVO.getTeam_member3());
				userId.add(teamMemVO.getTeam_member4());
			}
			for(int i=0; i<userId.size();i++) {
				ApplVO applVO = new ApplVO();
				applVO.setAppl_user(userId.get(i));
				applVO.setLt_no(teamVO.getLt_no());
				applDao.insertAppl(applVO);
			}
			
			result = ServiceResult.OK;
		}
		
		return result;
	}
	
	@Override
	public TeamVO retrieveTeam2(int teamNo) {
		return dao.selectTeam2(teamNo);
	}
	
}
