package kr.or.ddit.lecture.service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.LectureweekVO;

public interface ILectureWeekService {
	public ServiceResult createLectureWeek(LectureweekVO lectureweekVO);
}
