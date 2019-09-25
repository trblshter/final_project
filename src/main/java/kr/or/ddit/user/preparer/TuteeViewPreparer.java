package kr.or.ddit.user.preparer;

import java.util.Arrays;

import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;

import kr.or.ddit.annotation.Preparer;
import kr.or.ddit.vo.MenuVO;

@Preparer
public class TuteeViewPreparer implements ViewPreparer {

	@Override
	public void execute(Request arg0, AttributeContext arg1) {
		MenuVO menu1 = new MenuVO("내정보", "/tutee/mypage/myinfo", "myinfo");
		MenuVO menu2 = new MenuVO("신청내역", "/tutee/mypage/appl", "appl");
		MenuVO menu3 = new MenuVO("결제", "/tutee/mypage/payment", "payment");
		MenuVO menu4 = new MenuVO("작성리뷰", "/tutee/mypage/review", "review");
		MenuVO menu5 = new MenuVO("작성글목록", "/tutee/mypage/myBoardList.do", "myBoardList"); // 요기 수정
		MenuVO menu6 = new MenuVO("회원탈퇴", "/tutee/mypage/withdrawal", "Withdrawal");
		
		arg0.getContext(Request.REQUEST_SCOPE).put("menuList", Arrays.asList(menu1, menu2, menu3, menu4, menu5, menu6));
	}

}
