package kr.or.ddit.payment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.PaymentVO;

@Repository
public interface IPaymentDAO {
	/**
	 * 결제내역 추가
	 * @param paymentVO
	 * @return
	 */
	public int insertPayment(PaymentVO paymentVO);
	/**
	 * 결제 개수
	 * @param pagingVO
	 * @return
	 */
	public long selectPayCount(PagingVO<PaymentVO> pagingVO);
	/**
	 * 결제 리스트
	 * @param pagingVO
	 * @return
	 */
	public List<PaymentVO> selectPayList(PagingVO<PaymentVO> pagingVO);
	/**
	 * 결제 상세보기
	 * @param paymentVO
	 * @return
	 */
	public PaymentVO selectPay(int payment_no);
}
