package kr.or.ddit.message.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.MessageVO;
import kr.or.ddit.vo.PagingVO;

@Repository
public interface IMessageDAO {
	/**
	 * 페이징할 메시지 개수
	 * @param pagingVO
	 * @return long
	 */
	public long selectMessageCount(PagingVO<MessageVO> pagingVO);
	/**
	 * 메시지 리스트
	 * @param pagingVO
	 * @return messageList
	 */
	public List<MessageVO> selectMessageList(PagingVO<MessageVO> pagingVO);
	/**
	 * 메시지 상세보기
	 * @param msg_no 메시지 번호
	 * @return
	 */
	public MessageVO selectMessage(String msg_no);
	/**
	 * 메시지 읽으면 표시할 것
	 * @param msg_no
	 * @return
	 */
	public int readMessage(String msg_no);
	/**
	 * 메시지 보내기
	 * @param msg 메시지VO
	 * @return
	 */
	public int insertMessage(MessageVO msg);
	/**
	 * 메시지 삭제
	 * @param msg_no 메시지 번호
	 * @return
	 */
	public int deleteMessage(String msg_no);
	/**
	 * 읽지 않은 메시지 개수
	 * @param user_id
	 * @return
	 */
	public int unreadMessageCount(String user_id);
}
