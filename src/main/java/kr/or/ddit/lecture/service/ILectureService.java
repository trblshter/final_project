package kr.or.ddit.lecture.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.dao.ILectureDAO;
import kr.or.ddit.vo.CalendarVO;
import kr.or.ddit.vo.LectureVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ScheduledVO;


public interface ILectureService {
	
//	public List<ScheduledVO> lectureList (String SC_NO);
	public List<LecturetutorVO> retrieveLectureList (PagingVO<LecturetutorVO> pagingVO);
	public LecturetutorVO retrieveLectureTutor(String lt_no);
//	public ServiceResult createLecture(ScheduledVO scheduledVO);
	public ServiceResult createLecture(LecturetutorVO lecturetutor);
	public ServiceResult updateLecture(LecturetutorVO lecturetutorVO);
	public ServiceResult deleteLecture(ScheduledVO scheduledVO);
	public long retrieveLectureCount(PagingVO<LecturetutorVO> pagingVO);
	public ServiceResult updateLectureTutor(LecturetutorVO lecturetutorVO);
	public LecturetutorVO selectLectureTutor(String lt_no);
	
}
