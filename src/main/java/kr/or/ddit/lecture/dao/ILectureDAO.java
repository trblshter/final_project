package kr.or.ddit.lecture.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.LectureVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ScheduledVO;

@Repository
public interface ILectureDAO {
	
//	public List<ScheduledVO> lectureList(String SC_NO);
	public List<LecturetutorVO> selectLectureList(PagingVO<LecturetutorVO> pagingVO);
	public long selectLectureCount(PagingVO<LecturetutorVO> pagingVO);
	public LecturetutorVO retrieveLectureTutor(String lt_no);
	public int incrementHit(String lt_no);
//	public int insertLecture(ScheduledVO scheduledVO);
	public int insertLecture(LecturetutorVO lecturetutor);
	public int updateLecture(LecturetutorVO lecturetutorVO);
	public int deleteLecture(ScheduledVO scheduledVO);
	public int updateLectureTutor(LecturetutorVO lecturetutorVO);
	public LecturetutorVO selectLectureTutor(String lt_no);
}
