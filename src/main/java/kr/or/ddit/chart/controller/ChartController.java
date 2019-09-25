package kr.or.ddit.chart.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.chart.service.IChartService;
import kr.or.ddit.vo.ChartVO;

@Controller
public class ChartController {
	@Inject
	IChartService service;
	
	@GetMapping(value="/tutor/mypage/revenue")
	public String ReviewList() {
		return "tutor/mypage/revenue";
	}
	
	@GetMapping(value="/chart", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<ChartVO> chartList(
			@RequestParam(required=true) String loginId
			){
		List<ChartVO> list = service.retrievechartList(loginId);
		
		return list;
	}
}
