package kr.or.ddit.lecture.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.dao.IScheduleDAO;
import kr.or.ddit.vo.ScheduledVO;

@Service
public class ScheduleServiceImpl implements IScheduleService{

	@Inject
	IScheduleDAO dao;
	
	@Override
	public ServiceResult createSchedule(ScheduledVO scheduledVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertSchedule(scheduledVO);
		if(cnt > 0) result = ServiceResult.OK;
		return result;
	}

}
