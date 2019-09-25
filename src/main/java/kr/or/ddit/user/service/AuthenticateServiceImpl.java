package kr.or.ddit.user.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.vo.UserVO;

@Service
public class AuthenticateServiceImpl implements IAuthenticateService {
	
	@Inject
	IUserService service;
	
	@Override
	public Object authenticate(UserVO user) {
		UserVO userCheck = null;
		Object result = ServiceResult.INVALIDPASSWORD;
//		try {
//			// 관리자에 아이디가 있을때
//			userCheck = service.retrieveAdmin(user.getUser_id());
//			userCheck.setUser_type("admin");
//		}catch (NullPointerException e) {
//			 관리자에 아이디가 없을때
			userCheck = service.retrieveUser(user.getUser_id());
//		}finally {
			if(userCheck != null) {
				System.out.println(user.getUser_pass());
				System.out.println(userCheck.getUser_pass());
				if(user.getUser_pass().equals(userCheck.getUser_pass())) {
					result = userCheck;
				}
			}else {
				throw new UserNotFoundException();
			}
//		}
		return result;
		
	}

}




