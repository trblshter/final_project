package kr.or.ddit.lecture.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.LectureUserVO;
import kr.or.ddit.vo.LectureVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ScheduledVO;

@Repository
public interface ITeacherDAO {
//	public List<ScheduledVO> lectureList(String SC_NO);
	public List<LectureUserVO> selectLectureUserList(PagingVO<LectureUserVO> pagingVO);
	public long selectLectureUserCount(PagingVO<LectureUserVO> pagingVO);
	public LectureUserVO retrieveLectureUser(String lu_no);
	public int incrementHit(long lt_no);
//	public int insertLecture(ScheduledVO scheduledVO);
	public int insertLecture(LectureUserVO lectureUserVO);
	public int updateLectureUser(LectureUserVO lectureUserVO);
	public int deleteLecture(ScheduledVO scheduledVO);
}
