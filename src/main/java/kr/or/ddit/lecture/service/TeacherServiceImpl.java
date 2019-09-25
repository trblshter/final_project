package kr.or.ddit.lecture.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.dao.ITeacherDAO;
import kr.or.ddit.vo.LectureUserVO;
import kr.or.ddit.vo.LectureVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ScheduledVO;

@Service
public class TeacherServiceImpl implements ITeacherService{

	@Inject
	ITeacherDAO dao;
	
	@Override
	public List<LectureUserVO> retrieveLectureUserList(PagingVO<LectureUserVO> pagingVO) {
		return dao.selectLectureUserList(pagingVO);
	}

	@Override
	public LectureUserVO retrieveLectureUser(String lu_no) {
		return dao.retrieveLectureUser(lu_no);
	}

	@Override
	public ServiceResult createLecture(LectureUserVO lectureUserVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertLecture(lectureUserVO);
		if(cnt > 0) result = ServiceResult.OK;
		return result;
	}

	@Override
	public ServiceResult updateLecture(LectureUserVO lectureUserVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.updateLectureUser(lectureUserVO);
		if(cnt > 0) result = ServiceResult.OK;
		return result;
	}

	@Override
	public ServiceResult deleteLecture(ScheduledVO scheduledVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long retrieveLectureUserCount(PagingVO<LectureUserVO> pagingVO) {
		return dao.selectLectureUserCount(pagingVO);
	}

}
