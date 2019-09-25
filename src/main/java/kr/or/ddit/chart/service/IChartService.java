package kr.or.ddit.chart.service;

import java.util.List;

import kr.or.ddit.vo.ChartVO;

public interface IChartService {
	/**
	 * 해당하는 유저의 달마다 총수익, 거래량
	 * @param userId
	 * @return
	 */
	public List<ChartVO> retrievechartList(String userId);
}
