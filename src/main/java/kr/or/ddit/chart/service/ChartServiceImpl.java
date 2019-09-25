package kr.or.ddit.chart.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;
import org.springframework.stereotype.Service;

import kr.or.ddit.chart.dao.IChartDAO;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.user.dao.IUserDAO;
import kr.or.ddit.vo.ChartVO;
import kr.or.ddit.vo.UserVO;

@Service
public class ChartServiceImpl implements IChartService {
	
	@Inject
	IChartDAO dao;
	@Inject
	IUserDAO userDao;

	@Override
	public List<ChartVO> retrievechartList(String userId) {
		UserVO userVO = userDao.selectUser(userId);
		if(userVO==null) throw new UserNotFoundException(userId + "인 회원이 존재하지 않습니다.");
		
		return dao.selectChartList(userId);
	}

}
