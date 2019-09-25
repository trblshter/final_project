package kr.or.ddit.lecture.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.dao.ILectureWeekDAO;
import kr.or.ddit.vo.LectureweekVO;

@Service
public class LectureWeekServiceImpl implements ILectureWeekService{
	
	@Inject
	ILectureWeekDAO dao;

	@Override
	public ServiceResult createLectureWeek(LectureweekVO lectureweekVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertLectureWeek(lectureweekVO);
		if(cnt > 0) result = ServiceResult.OK;
		return result;
	}

}
