package kr.or.ddit.message.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.attach.dao.IAttachDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CommonException;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.message.dao.IMessageDAO;
import kr.or.ddit.user.dao.IUserDAO;
import kr.or.ddit.vo.AttachmentVO;
import kr.or.ddit.vo.MessageVO;
import kr.or.ddit.vo.PagingVO;

@Service
public class MessageServiceImpl implements IMessageService {
	@Inject
	IMessageDAO dao;
	@Inject
	IUserDAO userDao;
	@Inject
	IAttachDAO attachDao;
	
	@Value("#{appInfo.attachPath}")
	private File saveFolder;
	
	private void processFiles(MessageVO message) {
		// 업로드된 파일 처리
		List<AttachmentVO> attachList= message.getAttachList();
		if(attachList==null || attachList.size()==0) return;
		
		for(AttachmentVO attach : attachList) {
			attach.setMsg_no(message.getMsg_no());
			attachDao.insertAttach(attach);
			File saveFile = new File(saveFolder, attach.getAtt_savename());
			try(
				InputStream is = attach.getItem().getInputStream();
			){
				FileUtils.copyInputStreamToFile(is, saveFile);
			}catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public long retrieveMessageCount(PagingVO<MessageVO> pagingVO) {
		return dao.selectMessageCount(pagingVO);
	}

	@Override
	public List<MessageVO> retrieveMessageList(PagingVO<MessageVO> pagingVO) {
		return dao.selectMessageList(pagingVO);
	}

	@Override
	public MessageVO retrieveMessage(String msg_no) {
		MessageVO msg = dao.selectMessage(msg_no);
		if(msg==null) throw new CommonException(msg_no +"번인 메시지가 존재하지 않습니다.");
		
		if(msg.getMsg_ok()!=1) {
			dao.readMessage(msg_no);
		}
		return msg;
	}

	@Override
	@Transactional
	public ServiceResult createMessage(MessageVO msg) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertMessage(msg);
		if(userDao.selectUser(msg.getMsg_recipient())==null) throw new UserNotFoundException(msg.getMsg_recipient() + "인 회원이 존재하지 않습니다.");
		if(cnt>0) {
			processFiles(msg);
			result = ServiceResult.OK;
		}
		
		return result;
	}

	@Override
	@Transactional
	public ServiceResult removeMessage(String msg_no) {
		ServiceResult result = ServiceResult.FAILED;
		MessageVO msg = dao.selectMessage(msg_no);
		if(msg==null) throw new CommonException(msg_no +"번인 메시지가 존재하지 않습니다.");
		
		int cnt = dao.deleteMessage(msg_no);
		if(cnt>0) {
			List<AttachmentVO> attachList = msg.getAttachList();
			if(attachList!=null) {
				for(AttachmentVO attach : attachList) {
					FileUtils.deleteQuietly(new File("d:/savefiles/" + attach.getAtt_savename()));
				}
			}
			
			result = ServiceResult.OK;
		}
		
		return result;
	}

	@Override
	public int unreadMessageCount(String user_id) {
		return dao.unreadMessageCount(user_id);
	}

}
