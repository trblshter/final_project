package kr.or.ddit.attach.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.AttachmentVO;

@Repository
public interface IAttachDAO {
	public int insertAttach(AttachmentVO attach);
	public AttachmentVO selectAttach(int att_no);
	
	// 종범 추가
	public List<AttachmentVO> selectBoAttach(String bo_no);
	
	public int incrementDownCount(int att_no);
	public int deleteAttach(int att_no);
	public int deleteAttachByLtNo(String ltOrHs_no);
	public AttachmentVO selectAttachByLtNo(String ltOrHs_no);
}
