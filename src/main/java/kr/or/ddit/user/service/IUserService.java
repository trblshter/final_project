package kr.or.ddit.user.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.MessageVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.UserVO;

public interface IUserService {
	
	/**
	 * 유저 리스트 페이징을 위한 카운팅
	 * @param pagingVO
	 * @return
	 */
	int retrieveUserCount(PagingVO<UserVO> pagingVO);
	
	public List<UserVO> retrieveUserList(PagingVO pagingVO);
	/**
	 *  상세 조회
	 * @param user_id 조회할 아이디
	 * @return 존재하지  않는 경우, UserNotFoundException 발생.
	 */
	public UserVO retrieveUser(String user_id);
	
	/**
	 * 관리자 조외
	 * @param user_id 조회할 아이디
	 * @return 존재 하지 않는 경우, UserNotFoundException 발생.
	 */
	public UserVO retrieveAdmin(String user_id);
	
	/**
	 * 아이디 찾기/ 비번 찾기 유저가 존재하는지 조회
	 * @param user
	 * @return 존재하지 않는 경우(UserNotFoundException), 존재 하는 경우 UserVO
	 */
	public UserVO retrieveFindUser(UserVO user);
	
	/**
	 * 신규회원 등록
	 * @param user
	 * @return 아이디 중복(PKDUPLICATED), 성공(OK), 실패(FAILED)
	 */
	public ServiceResult createUser(UserVO user);
    /**
        * 회원 수정
     * @param user
     * @return 성공(OK), 실패(FAILED)
     */
    public ServiceResult modifyUser(UserVO user);
    /**
        *  회원 탈퇴
     * @param user
     * @return 존재하지 않는 경우(UserNotFoundException), 
     * 			비번오류(INVALIDPASSWORD), 성공(OK), 실패(FAILED)
     */
    public ServiceResult removeUser(UserVO user);
    
    
    
    
    /**
     * 인증이 필요한 선생님 수
     * @param pagingVO
     * @return long 
     */
    public long retrieveAuthTutorCount(PagingVO<UserVO> pagingVO);
    /**
     * 인증이 필요한 선생님 리스트
     * @param pagingVO
     * @return UserVO list
     */
    public List<UserVO> retrieveAuthTutorList(PagingVO<UserVO> pagingVO);
    /**
     * 인증이 필요한 선생님 상세내용
     * @param user_id 선생님id
     * @return
     */
    public UserVO retrieveAuthTutor(String user_id);
    /**
     * 인증할 선생님 수정
     * @param user_id 선생님아이디
     * @return
     */
    public ServiceResult authenticateTutor(String user_id);
    /**
     * 인증 거부당한 선생님 수정
     * @param msg messageVO
     * @return
     */
    public ServiceResult authCancelTutor(MessageVO msg);
}
