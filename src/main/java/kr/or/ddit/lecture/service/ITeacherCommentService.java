package kr.or.ddit.lecture.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.LectureCommentVO;
import kr.or.ddit.vo.LectureUserVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;

public interface ITeacherCommentService {
	public List<LectureCommentVO> retrieveLectureCommentList(PagingVO<LectureCommentVO> pagingVO);
	public ServiceResult createLectureComment(LectureCommentVO commentVO);
	public long retrieveTeacherCommentCount(PagingVO<LectureCommentVO> pagingVO); 
	public LectureCommentVO retrieveLectureComment(LectureCommentVO lectureCommentVO);
}
