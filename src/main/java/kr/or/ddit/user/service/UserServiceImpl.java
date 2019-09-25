package kr.or.ddit.user.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CommonException;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.message.dao.IMessageDAO;
import kr.or.ddit.user.dao.IUserDAO;
import kr.or.ddit.vo.MessageVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.UserVO;

@Service
public class UserServiceImpl implements IUserService {

	@Inject
	private IUserDAO userDao;
	@Inject
	private IMessageDAO messageDao;
	
	@Override
	public int retrieveUserCount(PagingVO<UserVO> pagingVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserVO> retrieveUserList(PagingVO pagingVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVO retrieveUser(String user_id) {
		UserVO user = userDao.selectUser(user_id);
		if(user == null) {
			throw new UserNotFoundException(user_id + "은 없는 회원입니다.");
		}
		return user;
	}
	
	@Override
	public UserVO retrieveAdmin(String user_id) {
		UserVO user = userDao.selectAdmin(user_id);
		if(user.getUser_id().length() == 0) {
			throw new UserNotFoundException();
		}
		return user;
	}

	@Override
	public ServiceResult createUser(UserVO user) {
		ServiceResult result = ServiceResult.FAILED;
		try {
			retrieveUser(user.getUser_id());
			result = ServiceResult.PKDUPLICATED;
		}catch (UserNotFoundException e) {
			int cnt = userDao.insertUser(user);
			if(user.isTutorCheck()) {
				int cnt2 = userDao.insertTutorUser(user);
				if(cnt > 0 && cnt2 > 0) {
					result = ServiceResult.OK;
				}
			}
		}				
		return result;
	}
	
	@Transactional
	@Override
	public ServiceResult modifyUser(UserVO user) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = userDao.updateUser(user);
		int cnt2 = -1;
		if(cnt > 0) {
			if("tutor".equals(user.getUser_type())) {
				cnt2 = userDao.updateTutorUser(user);
//				throw new CommonException();
			}
			result = ServiceResult.OK;
		}
		return result;
	}

	@Override
	public ServiceResult removeUser(UserVO user) {
		
		ServiceResult result = ServiceResult.FAILED;
		int cnt = userDao.deleteUser(user);
		if(cnt > 0) {
			result = ServiceResult.OK;
		}
		return result;
	}

	
	@Override
	public long retrieveAuthTutorCount(PagingVO<UserVO> pagingVO) {
		return userDao.selectAuthTutorCount(pagingVO);
	}

	@Override
	public List<UserVO> retrieveAuthTutorList(PagingVO<UserVO> pagingVO) {
		return userDao.selectAuthTutorList(pagingVO);
	}
	
	@Override
	public UserVO retrieveAuthTutor(String user_id) {
		UserVO user = userDao.selectUser(user_id);
		if(user==null) throw new UserNotFoundException(user_id + "인 회원이 존재하지 않습니다.");
		
		return userDao.selectAuthTutor(user_id);
	}

	@Override
	public UserVO retrieveFindUser(UserVO findUser) {
		UserVO user = userDao.selectFindUser(findUser);
		if(user==null) throw new UserNotFoundException("해당 회원이 없습니다.");
		return user;
		
	}
	
	@Override
	public ServiceResult authenticateTutor(String user_id) {
		UserVO user = userDao.selectUser(user_id);
		if(user==null) throw new UserNotFoundException(user_id + "인 회원이 존재하지 않습니다.");
		ServiceResult result = ServiceResult.FAILED;
		int cnt = userDao.authenticateTutor(user_id);
		if(cnt>0) result = ServiceResult.OK;
		
		return result;
	}

	@Override
	public ServiceResult authCancelTutor(MessageVO msg) {
		UserVO user = userDao.selectUser(msg.getMsg_recipient());
		if(user==null) throw new UserNotFoundException(msg.getMsg_recipient() + "인 회원이 존재하지 않습니다.");
		ServiceResult result = ServiceResult.FAILED;
		int cnt = userDao.authCancelTutor(msg.getMsg_recipient());
		if(cnt>0) {
			messageDao.insertMessage(msg);
			
			result = ServiceResult.OK;
		}
		return result;
	}

	

}
