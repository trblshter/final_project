package kr.or.ddit.payment.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.dao.ITeamDAO;
import kr.or.ddit.payment.dao.IPaymentDAO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.PaymentVO;
import kr.or.ddit.vo.TeamVO;

@Service
public class PaymentServiceImpl implements IPaymentService {
	@Inject
	IPaymentDAO dao;
	@Inject
	ITeamDAO teamDao;

	@Override
	public ServiceResult createPayment(PaymentVO paymentVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertPayment(paymentVO);
		if(cnt>0) {
//			String lt_no = paymentVO.getLt_no();
//			String user_id = paymentVO.getPartner_user_id();
//			TeamVO teamVO = teamDao.selectTeam(lt_no);
//			List<String> teamList = new ArrayList<>();
//			if(teamVO.getTeam_member1()==null&&teamVO.getTeam_member2()==null&&
//					teamVO.getTeam_member3()==null&&teamVO.getTeam_member4()==null) {
//				for(int i=0; i<4; i++) {
//					teamList.add(null);
//				}
//			}
//			else {
//				if(teamVO.getTeam_member1()!=null) {
//					teamList.add(teamVO.getTeam_member1());
//					for(int i=0; i<3; i++) {
//						teamList.add(null);
//					}
//				}else if(teamVO.getTeam_member2()!=null) {
//					teamList.add(teamVO.getTeam_member2());
//					for(int i=0; i<2; i++) {
//						teamList.add(null);
//					}
//				}else if(teamVO.getTeam_member3()!=null) {
//					teamList.add(teamVO.getTeam_member3());
//					teamList.add(null);
//				}else if(teamVO.getTeam_member4()!=null) {
//					teamList.add(teamVO.getTeam_member4());
//				}
//			}
//			
//			for(int i=0; i<teamList.size(); i++) {
//				if(teamList.get(i)==null) {
//					if(i==0) {
//						teamVO.setTeam_member1(user_id);
//						break;
//					}else if(i==1) {
//						teamVO.setTeam_member2(user_id);
//						break;
//					}else if(i==2) {
//						teamVO.setTeam_member3(user_id);
//						break;
//					}else {
//						teamVO.setTeam_member4(user_id);
//						break;
//					}
//				}
//			}
//			teamVO.setLt_no(lt_no);
//			int teamCnt = teamDao.updateTeam(teamVO);
//			
//			if(teamCnt>0) result = ServiceResult.OK;
			result = ServiceResult.OK;
		}
		
		return result;
	}

	@Override
	public long retrievePayCount(PagingVO<PaymentVO> pagingVO) {
		return dao.selectPayCount(pagingVO);
	}

	@Override
	public List<PaymentVO> retrievePayList(PagingVO<PaymentVO> pagingVO) {
		return dao.selectPayList(pagingVO);
	}

	@Override
	public PaymentVO retrievePay(int payment_no) {
		return dao.selectPay(payment_no);
	}

}
