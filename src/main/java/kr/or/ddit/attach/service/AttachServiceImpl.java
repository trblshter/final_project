package kr.or.ddit.attach.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.attach.dao.IAttachDAO;
import kr.or.ddit.exception.CommonException;
import kr.or.ddit.vo.AttachmentVO;

@Service
public class AttachServiceImpl implements IAttachService {
	@Inject
	IAttachDAO dao;
	
	@Override
	public AttachmentVO download(int att_no) {
		AttachmentVO attach = dao.selectAttach(att_no);
		if(attach==null) throw new CommonException(att_no + " 파일이 없음");
		
		dao.incrementDownCount(att_no);
		return attach;
	}

	// board 첨부파일 추가
	@Override
	public List<AttachmentVO> selectBonoAttach(String bo_no) {
		
		return dao.selectBoAttach(bo_no);
	}

}
