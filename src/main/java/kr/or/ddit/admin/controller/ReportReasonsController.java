package kr.or.ddit.admin.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.service.IReportReasonsService;
import kr.or.ddit.vo.ReportreasonsVO;

@RestController
@RequestMapping(value="/admin/reportReasons", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReportReasonsController {
	@Inject
	IReportReasonsService service;
	
	@GetMapping
	public List<ReportreasonsVO> selectReportList(
			Model model
			) {
		List<ReportreasonsVO> list = service.retrieveReportReasonsList();

		model.addAttribute("list", list);
		
		return list;
	}
}
