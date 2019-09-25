package kr.or.ddit.admin.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.payment.service.IPaymentService;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.PaymentVO;

@Controller
public class PaymentAdminController {
	@Inject
	IPaymentService service;
	
	@GetMapping(value="/admin/pay")
	public String applList() {
		return "admin/payView";
	}
	
	@GetMapping(value="/admin/payList",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<PaymentVO> payList(
			Model model,
			@RequestParam(name="page", defaultValue="1", required=false) long currentPage
			) {
		PagingVO<PaymentVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);
		
		long totalRecord = service.retrievePayCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<PaymentVO> list = service.retrievePayList(pagingVO);
		pagingVO.setDataList(list);
		
		return pagingVO;
	}
	
}
