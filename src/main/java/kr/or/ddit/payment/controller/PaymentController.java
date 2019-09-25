package kr.or.ddit.payment.controller;

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
public class PaymentController {
	@Inject
	IPaymentService service;
	
	@GetMapping(value="/tutee/mypage/payment")
	public String applList() {
		return "tutee/mypage/payment";
	}
	
	@GetMapping(value="/pay/payList.do",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<PaymentVO> payList(
			Model model,
			@RequestParam(name="page", defaultValue="1", required=false) long currentPage,
			@RequestParam(required=false) String searchWord
			) {
		PagingVO<PaymentVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSearchWord(searchWord);
		
		long totalRecord = service.retrievePayCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<PaymentVO> list = service.retrievePayList(pagingVO);
		pagingVO.setDataList(list);
		
		model.addAttribute("pagingVO", pagingVO);
		
		return pagingVO;
	}
	
	@GetMapping(value="/pay/payView.do",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaymentVO payView(
			@RequestParam(name="what", required=true) int payment_no
			) {
		
		PaymentVO paymentVO = service.retrievePay(payment_no);
		
		return paymentVO;
	}
	
}
