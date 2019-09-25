package kr.or.ddit.payment.service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.RefundVO;

public interface IRefundService {
	/**
	 * 환불내용 추가
	 * @param refundVO
	 * @return
	 */
	public ServiceResult createRefund(RefundVO refundVO);
}
