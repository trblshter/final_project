package kr.or.ddit.user.preparer;

import java.util.Arrays;

import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;

import kr.or.ddit.annotation.Preparer;
import kr.or.ddit.vo.MenuVO;

@Preparer
public class TutorViewPreparer implements ViewPreparer {

	@Override
	public void execute(Request arg0, AttributeContext arg1) {
		MenuVO menu1 = new MenuVO("내정보", "/tutor/mypage/myinfo", "myinfo");
		MenuVO menu2 = new MenuVO("수익", "/tutor/mypage/revenue", "revenue");
		MenuVO menu3 = new MenuVO("내리뷰확인", "/tutor/mypage/review", "review");
		MenuVO menu4 = new MenuVO("작성글목록", "/tutor/mypage/myBoardList.do", "myBoardList");
		MenuVO menu5 = new MenuVO("회원탈퇴", "/tutor/mypage/withdrawal", "Withdrawal");
		
		
		arg0.getContext(Request.REQUEST_SCOPE).put("menuList", Arrays.asList(menu1, menu2, menu3, menu4, menu5));
	}

}
