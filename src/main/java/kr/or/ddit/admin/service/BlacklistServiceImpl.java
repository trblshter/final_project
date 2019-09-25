package kr.or.ddit.admin.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.dao.IBlacklistDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.vo.BlacklistVO;
import kr.or.ddit.vo.PagingVO;

@Service
public class BlacklistServiceImpl implements IBlacklistService {
	
	@Inject
	IBlacklistDAO dao;
	
	@Override
	public long retrieveBlackCount(PagingVO<BlacklistVO> pagingVO) {
		return dao.selectBlackCount(pagingVO);
	}

	@Override
	public List<BlacklistVO> retrieveBlackList(PagingVO<BlacklistVO> pagingVO) {
		return dao.selectBlackList(pagingVO);
	}
	
	@Override
	public BlacklistVO retrieveBlack(String bl_id) {
		BlacklistVO bl = dao.selectBlack(bl_id);
		if(bl==null) throw new UserNotFoundException(bl_id + "인 회원이 블랙리스트에 없습니다.");
		
		return bl;
	}
	
	@Override
	public ServiceResult createBlack(BlacklistVO bl) {
		ServiceResult result = ServiceResult.FAILED;
		try {
			retrieveBlack(bl.getBl_id());
			result = ServiceResult.PKDUPLICATED;
		}catch(UserNotFoundException e) {
			int cnt = dao.insertBlack(bl);
			if(cnt>0) result = ServiceResult.OK;
		}
		return result;
	}

	@Override
	public ServiceResult removeBlack(String bl_id) {
		BlacklistVO bl = dao.selectBlack(bl_id);
		if(bl==null) throw new UserNotFoundException(bl_id + "인 회원이 블랙리스트에 없습니다.");
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.deleteBlack(bl_id);
		if(cnt>0) result = ServiceResult.OK;
	
		return result;
	}



}
