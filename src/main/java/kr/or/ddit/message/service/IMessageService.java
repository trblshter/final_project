package kr.or.ddit.message.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.MessageVO;
import kr.or.ddit.vo.PagingVO;

public interface IMessageService {
	/**
	 * 페이징할 메시지 개수
	 * @param pagingVO
	 * @return long
	 */
	public long retrieveMessageCount(PagingVO<MessageVO> pagingVO);
	/**
	 * 메시지 리스트
	 * @param pagingVO
	 * @return messageList
	 */
	public List<MessageVO> retrieveMessageList(PagingVO<MessageVO> pagingVO);
	/**
	 * 메시지 상세보기
	 * @param msg_no 메시지 번호
	 * @return
	 */
	public MessageVO retrieveMessage(String msg_no);
	/**
	 * 메시지 보내기
	 * @param msg 메시지VO
	 * @return
	 */
	public ServiceResult createMessage(MessageVO msg);
	/**
	 * 메시지 삭제
	 * @param msg_no 메시지 번호
	 * @return
	 */
	public ServiceResult removeMessage(String msg_no);
	/**
	 * 읽지 않은 메시지 개수
	 * @param user_id
	 * @return
	 */
	public int unreadMessageCount(String user_id);
}
