package kr.or.ddit.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.UserVO;

@Repository
public interface IUserDAO {
	int selectUserCount(PagingVO<UserVO> pagingVO);
	public List<UserVO> selectUserList(PagingVO pagingVO);
	public UserVO selectUser(String user_id);
	public UserVO selectAdmin(String user_id);
	public UserVO selectFindUser(UserVO user);
	public int insertUser(UserVO user);
    public int updateUser(UserVO user);
    public int deleteUser(UserVO user);
    public int insertTutorUser(UserVO user);
    public int updateTutorUser(UserVO user);
    /**
     * 인증이 필요한 선생님 수
     * @param pagingVO
     * @return long 
     */
    public long selectAuthTutorCount(PagingVO<UserVO> pagingVO);
    /**
     * 인증이 필요한 선생님 리스트
     * @param pagingVO
     * @return UserVO list
     */
    public List<UserVO> selectAuthTutorList(PagingVO<UserVO> pagingVO);
    /**
     * 인증이 필요한 선생님 상세내용
     * @param user_id 선생님id
     * @return
     */
    public UserVO selectAuthTutor(String user_id);
    /**
     * 인증할 선생님 수정
     * @param user_id 선생님아이디
     * @return
     */
    public int authenticateTutor(String user_id);
    /**
     * 인증 거부당한 선생님 수정
     * @param user_id 선생님아이디
     * @return
     */
    public int authCancelTutor(String user_id);
}