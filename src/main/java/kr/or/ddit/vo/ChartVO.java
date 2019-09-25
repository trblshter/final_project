package kr.or.ddit.vo;

import lombok.Data;

@Data
public class ChartVO {
	private String month; //월
	private int sum; //합계
	private int count; //거래량
}
