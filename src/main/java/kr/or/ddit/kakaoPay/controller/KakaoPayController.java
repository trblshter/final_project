package kr.or.ddit.kakaoPay.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.appl.service.IApplService;
import kr.or.ddit.kakaoPay.service.KakaoPay;
import kr.or.ddit.payment.service.IPaymentService;
import kr.or.ddit.payment.service.IRefundService;
import kr.or.ddit.vo.ApplVO;
import kr.or.ddit.vo.KakaoPayApprovalVO;
import kr.or.ddit.vo.KakaoPayCancelVO;
import kr.or.ddit.vo.PaymentVO;
import kr.or.ddit.vo.RefundVO;
import lombok.extern.java.Log;

@Log
@Controller
public class KakaoPayController {
	@Inject
    private KakaoPay kakaopay;
	@Inject
	private IApplService applService;
	@Inject
	private IPaymentService paymentService;
	@Inject
	private IRefundService refundService;
    
    
    @GetMapping("/kakaoPay")
    public String kakaoPayGet(
    		@RequestParam("what") int appl_no
    		) {
    	ApplVO applSearchVO = new ApplVO();
    	applSearchVO.setAppl_no(appl_no);
    	ApplVO applVO = applService.retrieveAppl(applSearchVO);
    	
    	return "redirect:" + kakaopay.kakaoPayReady(applVO);
    }
    
    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(
    		@RequestParam("pg_token") String pg_token, Model model,
    		@RequestParam("what") int appl_no
    		) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        
        ApplVO applSearchVO = new ApplVO();
    	applSearchVO.setAppl_no(appl_no);
    	ApplVO applVO = applService.retrieveAppl(applSearchVO);
    	
    	KakaoPayApprovalVO approvalVO = kakaopay.kakaoPayInfo(pg_token, applVO);
    	
    	PaymentVO paymentVO = new PaymentVO();
    	paymentVO.setCid(approvalVO.getCid());
    	paymentVO.setPartner_order_id(Integer.parseInt(approvalVO.getPartner_order_id()));
    	paymentVO.setPartner_user_id(approvalVO.getPartner_user_id());
    	paymentVO.setPayment_method_type(approvalVO.getPayment_method_type());
    	paymentVO.setTax_free(approvalVO.getAmount().getTax_free());
    	paymentVO.setTid(approvalVO.getTid());
    	paymentVO.setTotal(approvalVO.getAmount().getTotal());
    	paymentVO.setVat(approvalVO.getAmount().getVat());
    	
    	ApplVO searchApplVO = new ApplVO();
    	searchApplVO.setAppl_no(paymentVO.getPartner_order_id());
    	ApplVO appl = applService.retrieveAppl(searchApplVO);
    	
    	paymentVO.setLt_no(appl.getLt_no());
    	
    	paymentService.createPayment(paymentVO);
    	
        model.addAttribute("paymentVO", paymentVO);
        
        return "kakaoPay/kakaoPaySuccess";
        
    }
    
    @GetMapping("/kakaoPayRefund")
    public String kakaoPayRefund(
    		Model model,
    		@RequestParam("what") int payment_no
    		) {
    	
    	PaymentVO paymentVO = paymentService.retrievePay(payment_no);
    	kakaopay.kakaoCancel(paymentVO);
    	
    	RefundVO refundVO = new RefundVO();
    	refundVO.setPayment_no(payment_no);
    	
    	refundService.createRefund(refundVO);
    	
        model.addAttribute("refundVO", refundVO);
        
        return "tutee/mypage/payment";
        
    }
    
    
}
