package kr.or.ddit.lecture.dao;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.LectureweekVO;

@Repository
public interface ILectureWeekDAO {
	public int insertLectureWeek(LectureweekVO lectureweekVO);
}
