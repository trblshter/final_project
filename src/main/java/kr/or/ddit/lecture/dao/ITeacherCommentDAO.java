package kr.or.ddit.lecture.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.LectureCommentVO;
import kr.or.ddit.vo.LectureUserVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;

@Repository
public interface ITeacherCommentDAO {
	
	public List<LectureCommentVO> selectTeacherCommentList(PagingVO<LectureCommentVO> pagingVO);
	public int insertTeacherComment(LectureCommentVO commentVO);
	public long retrieveTeacherCommentCount(PagingVO<LectureCommentVO> pagingVO);
	public LectureCommentVO retrieveLectureComment(LectureCommentVO lectureCommentVO);
}
