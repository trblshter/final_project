package kr.or.ddit.lecture.service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.ScheduledVO;

public interface IScheduleService {
	public ServiceResult createSchedule(ScheduledVO scheduledVO);
}
