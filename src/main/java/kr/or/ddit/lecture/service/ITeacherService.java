package kr.or.ddit.lecture.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.LectureUserVO;
import kr.or.ddit.vo.LectureVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ScheduledVO;

public interface ITeacherService {
//	public List<ScheduledVO> lectureList (String SC_NO);
	public List<LectureUserVO> retrieveLectureUserList (PagingVO<LectureUserVO> pagingVO);
	public LectureUserVO retrieveLectureUser(String lu_no);
//	public ServiceResult createLecture(ScheduledVO scheduledVO);
	public ServiceResult createLecture(LectureUserVO lectureUserVO);
	public ServiceResult updateLecture(LectureUserVO lectureUserVO);
	public ServiceResult deleteLecture(ScheduledVO scheduledVO);
	public long retrieveLectureUserCount(PagingVO<LectureUserVO> pagingVO);
}
