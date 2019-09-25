package kr.or.ddit.attach.service;

import java.util.List;

import kr.or.ddit.vo.AttachmentVO;

public interface IAttachService {
	public AttachmentVO download(int att_no);
	
	// board 첨부파일 추가
	public List<AttachmentVO> selectBonoAttach(String bo_no);
}
