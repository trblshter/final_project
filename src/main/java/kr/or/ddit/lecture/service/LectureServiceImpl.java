package kr.or.ddit.lecture.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CommonException;
import kr.or.ddit.lecture.dao.ILectureDAO;
import kr.or.ddit.vo.LectureVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ScheduledVO;

@Service
public class LectureServiceImpl implements ILectureService{

	@Inject
	ILectureDAO dao;
	
//	@Override
//	public List<ScheduledVO> lectureList(String SC_NO) {
//		List<ScheduledVO> list = dao.lectureList(SC_NO);
//		return list;
//	}

//	@Override
//	public ServiceResult createLecture(ScheduledVO scheduledVO) {
//		int cnt = dao.insertLecture(scheduledVO);
//		ServiceResult result = ServiceResult.FAILED;
//		if(cnt > 0) {
//			result = ServiceResult.OK;
//		}
//		return result;
//	}

	@Override
	public ServiceResult updateLecture(LecturetutorVO lecturetutorVO) {
		int cnt = dao.updateLecture(lecturetutorVO);
		ServiceResult result = ServiceResult.FAILED;
		if(cnt > 0) result = ServiceResult.OK;
		return result;
	}

	@Override
	public ServiceResult deleteLecture(ScheduledVO scheduledVO) {
		int cnt = dao.deleteLecture(scheduledVO);
		ServiceResult result = ServiceResult.FAILED;
		if(cnt > 0) result = ServiceResult.OK;
		return result;
	}

	@Override
	public List<LecturetutorVO> retrieveLectureList(PagingVO<LecturetutorVO> pagingVO) {
		return dao.selectLectureList(pagingVO);
	}

	@Override
	public long retrieveLectureCount(PagingVO<LecturetutorVO> pagingVO) {
		return dao.selectLectureCount(pagingVO);
	}

	@Override
	public LecturetutorVO retrieveLectureTutor(String lt_no) {
		LecturetutorVO lecturetutor = dao.retrieveLectureTutor(lt_no);
		if(lecturetutor == null) {
			throw new CommonException("강의 없음");
		}
		dao.incrementHit(lt_no);
		return lecturetutor;
	}

	@Override
	public ServiceResult createLecture(LecturetutorVO lecturetutor) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertLecture(lecturetutor);
		if(cnt > 0) result = ServiceResult.OK;
		return result;
	}

	@Override
	public ServiceResult updateLectureTutor(LecturetutorVO lecturetutorVO) {
		int cnt = dao.updateLectureTutor(lecturetutorVO);
		ServiceResult result = ServiceResult.FAILED;
		if(cnt > 0) result = ServiceResult.OK;
		return result;
	}

	@Override
	public LecturetutorVO selectLectureTutor(String lt_no) {
		LecturetutorVO lecturetutor = dao.selectLectureTutor(lt_no);
		if(lecturetutor == null) {
			throw new CommonException("강의 없음");
		}
		dao.incrementHit(lt_no);
		return lecturetutor;
	}

}
