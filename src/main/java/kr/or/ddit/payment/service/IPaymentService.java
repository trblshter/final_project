package kr.or.ddit.payment.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.PaymentVO;

public interface IPaymentService {
	/**
	 * 결제내역 추가
	 * @param paymentVO
	 * @return
	 */
	public ServiceResult createPayment(PaymentVO paymentVO);
	/**
	 * 결제 개수
	 * @param pagingVO
	 * @return
	 */
	public long retrievePayCount(PagingVO<PaymentVO> pagingVO);
	/**
	 * 결제 리스트
	 * @param pagingVO
	 * @return
	 */
	public List<PaymentVO> retrievePayList(PagingVO<PaymentVO> pagingVO);
	/**
	 * 결제 상세보기
	 * @param paymentVO
	 * @return
	 */
	public PaymentVO retrievePay(int payment_no);
}
