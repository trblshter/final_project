package kr.or.ddit.lecture.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.AttachmentVO;
import kr.or.ddit.vo.HwTutorVO;
import kr.or.ddit.vo.HwstudentVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ScheduledVO;
import kr.or.ddit.vo.TeamVO;
import kr.or.ddit.vo.UserVO;

@Repository
public interface IMyLectureDAO {

	public long selectMyLectureCountForTutor(String user_id); 
	public List<LecturetutorVO> selectMyLectureListForTutor(PagingVO<LecturetutorVO> pagingVO);
	public List<LecturetutorVO> selectMyLectureList(PagingVO<LecturetutorVO> pagingVO);
	public long selectMyLectureCount(String user_id);
	public LecturetutorVO selectMyLecture(String lt_no);
	public List<ScheduledVO> selectMyLectureSchedule(String user_id);
	public List<ScheduledVO> selectMyLectureScheduleTutor(String user_id);
	public ScheduledVO selectMyLectureSchedulebyLecture(String lt_no);
	public TeamVO selectMyFriends(String lt_no);
	public UserVO selectMyFriendInfo(String user_id);
	public LecturetutorVO selectMyLectureProgress(String lt_no);
	public List<AttachmentVO> selectMyLectureAttachList(PagingVO<AttachmentVO> pagingVO);
	public long selectMyLectureAttachCount(PagingVO<AttachmentVO> pagingVO);
	public int insertSharedFile(AttachmentVO attach);
	public AttachmentVO selectAttachment(int att_no);
	public long selectHwTutorCount(String lt_no);
	public List<HwTutorVO> selectHwTutorList(PagingVO<HwTutorVO> pagingVO);
	public int insertHomeWorkByTutor(HwTutorVO hwTutorVo); 
	public HwTutorVO selectHomeworkByTutor(String ht_no);
	public int deleteHomeworkByTutor(String ht_no);
	public int updateHomeworkBytutor(HwTutorVO hwtutorVO);
	public int insertHomeworkByTutee(HwstudentVO hwstudentVO);
	public int deleteHomeworkByTutee(String hs_no);
	public List<HwstudentVO> selectSubmittedHomeworkList(String ht_no);
	
	
}
