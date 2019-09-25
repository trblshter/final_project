package kr.or.ddit.payment.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.payment.dao.IRefundDAO;
import kr.or.ddit.vo.RefundVO;

@Service
public class RefundServiceImpl implements IRefundService {
	@Inject
	IRefundDAO dao;

	@Override
	public ServiceResult createRefund(RefundVO refundVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertRefund(refundVO);
		if(cnt>0) result = ServiceResult.OK;
		
		return result;
	}

}
