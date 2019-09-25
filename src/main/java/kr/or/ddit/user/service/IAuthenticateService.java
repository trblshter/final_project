package kr.or.ddit.user.service;

import kr.or.ddit.vo.UserVO;

public interface IAuthenticateService {
	/**
	 * 아이디 패스워드 기반의 인증 처리
	 * @param member
	 * @return 존재하지 않는 경우(UserNotFound), 비번 오류(INVALIDPASSWORD), 
	 * 			인증 성공(UserVO)
	 */
	public Object authenticate(UserVO user);
}
