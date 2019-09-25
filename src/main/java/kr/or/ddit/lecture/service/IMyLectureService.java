package kr.or.ddit.lecture.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.AttachmentVO;
import kr.or.ddit.vo.HwTutorVO;
import kr.or.ddit.vo.HwstudentVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ScheduledVO;
import kr.or.ddit.vo.TeamVO;
import kr.or.ddit.vo.UserVO;

public interface IMyLectureService {
	
	/**
	 * 나의 강의에 대한 리스트 조회
	 * @param pagingVO
	 * @return 내가 신청한 강의 목록 리스트
	 */
	public List<LecturetutorVO> retrieveMyLectureList(PagingVO<LecturetutorVO> pagingVO);
	
	/**
	 * 유저가 신청한 강의 목록 개수.
	 * @param user_id
	 * @return 강의 카운트, user_id가 없을 경우 에러.
	 */
	public long retrieveMyLectureCount(String user_id );
	
	/**
	 * 나의 강의에 대한 항목 조회
	 * @param lecture_no
	 * @return 내가 신청한 강의, 해당 lecture_no 없을 시 에러.
	 */
	public LecturetutorVO retrieveMyLecture(String lt_no);
	
	/**
	 * 나의 강의에 대한 일정 출력.
	 * @param user_id
	 * @return 내가 신청한 강의에 대한 일정 정보(FullCalendar)
	 * 
	 */
	public List<ScheduledVO> retrieveMyLectureSchedule(String user_id);
	
	/**
	 * 선생님 강의에 대한 일정 출력.
	 * @param user_id
	 * @return
	 */
	public List<ScheduledVO> retrieveMyLectureScheduleTutor(String user_id);

	/**
	 * 같이 강의를 듣는 튜티들 조회
	 * @param lt_no
	 * @return 튜티 멤버1, 2, 3.. 조회
	 */
	public TeamVO retrieveMyFriends(String lt_no);
	
	/**
	 * 해당 회원에 대한 정보 조회
	 * @param user_id
	 * @return 유저에 대한 정보. 유저가 없을 시 userNotFoundException 발생.
	 */
	public UserVO retrieveMyFriendInfo(String user_id);
	
	/**
	 * 현재 나의 강의에 대한 진행도 조회
	 * @param lt_no
	 * @return 강의의 최대 회차 수, 현재 진행 회차
	 */
	public LecturetutorVO retrieveMyLectureProgress(String lt_no);
	
	/**
	 * 자료 공유 리스트 조회
	 * @param lt_no
	 * @return 공유할 자료들의 리스트
	 */
	public List<AttachmentVO> retrieveMyLectureSharingList(PagingVO<AttachmentVO> pagingVO);
	
	/**
	 * 자료 공유 카운트.
	 * @param pagingVO
	 * @return
	 */
	public long retrieveMyLectureSharingCount(PagingVO<AttachmentVO> pagingVO);
	
	public long retrieveMyLectureCountForTutor(String user_id);
	
	public List<LecturetutorVO> retrieveMyLectureListForTutor(PagingVO<LecturetutorVO> pagingVO);
	
	/**
	 * 자료공유 파일 업로드
	 * @param attach
	 * @return
	 */
	public ServiceResult uploadSharedFile(AttachmentVO attach);
	
	/**
	 * 자료공유 파일 정보 추출
	 * @param att_no
	 * @return
	 */
	public AttachmentVO readyForDownloadFile(int att_no);
	
	public ServiceResult removeSharedFile(int att_no);
	
	public ScheduledVO getLectureSchedulebyOne(String lt_no);
	
	/**
	 * 과제 업로드 (작성)
	 * @param hwTutorVO
	 * @return
	 */
	public ServiceResult uploadHomeWork(HwTutorVO hwTutorVO);
	
	
	/**
	 * 과제 리스트 출력
	 * @param pagingVO
	 * @return
	 */
	public List<HwTutorVO> retrieveHomeWorkList(PagingVO<HwTutorVO> pagingVO);
	
	public long retrieveHomeWorkCount(String lt_no);
	
	/**
	 * 과제 정보 조회
	 * @param ht_no
	 * @return 해당 과제 정보
	 */
	public HwTutorVO retrieveHomeworkInfo(String ht_no);
	
	/**
	 * 과제 삭제
	 * @param ht_no
	 * @return
	 */
	public ServiceResult removeHomework(String ht_no);
	
	/**
	 * 과제 수정
	 * @param ht_no
	 * @return
	 */
	public ServiceResult modifyHomework(HwTutorVO hwtutorVO);	
	
	/**
	 * 학생 과제 제출.
	 * @param hwstudentVO
	 * @param attach
	 * @return
	 */
	public ServiceResult submitHomework(HwstudentVO hwstudentVO, AttachmentVO attach);
	
	/**
	 * 과제 삭제(튜티)
	 * @param hs_no
	 * @return
	 */
	public ServiceResult removeHomeworkByTutee(String hs_no);
	
	/**
	 * 제출한 과제 확인
	 * @param ht_no
	 * @return
	 */
	public List<HwstudentVO> retrieveSubmittedHomeworkList(String ht_no);
	


}
