package kr.or.ddit.lecture.dao;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.ScheduledVO;

@Repository
public interface IScheduleDAO {
	public int insertSchedule(ScheduledVO scheduledVO);
}
