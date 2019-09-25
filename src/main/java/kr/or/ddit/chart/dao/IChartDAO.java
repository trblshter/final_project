package kr.or.ddit.chart.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.ChartVO;

@Repository
public interface IChartDAO {
	/**
	 * 해당하는 유저의 달마다 총수익, 거래량
	 * @param userId
	 * @return
	 */
	public List<ChartVO> selectChartList(String userId);
}
