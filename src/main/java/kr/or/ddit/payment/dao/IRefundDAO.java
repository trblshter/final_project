package kr.or.ddit.payment.dao;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.RefundVO;

@Repository
public interface IRefundDAO {
	/**
	 * 환불내용 추가
	 * @param refundVO
	 * @return
	 */
	public int insertRefund(RefundVO refundVO);
	
}