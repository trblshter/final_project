package kr.or.ddit.admin.preparer;

import java.util.Arrays;

import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;

import kr.or.ddit.annotation.Preparer;
import kr.or.ddit.vo.MenuVO;

@Preparer
public class AdminViewPreparer implements ViewPreparer {

	@Override
	public void execute(Request arg0, AttributeContext arg1) {
		MenuVO menu1 = new MenuVO("신고목록", "/admin/report", "report");
		MenuVO menu2 = new MenuVO("블랙관리", "/admin/blacklist", "blacklist");
		MenuVO menu3 = new MenuVO("회원인증", "/admin/certification", "certification");
		MenuVO menu4 = new MenuVO("신청관리", "/admin/appl", "appl");
		MenuVO menu5 = new MenuVO("결제관리", "/admin/pay", "pay");
		
		arg0.getContext(Request.REQUEST_SCOPE).put("menuList", Arrays.asList(menu1, menu2, menu3, menu4, menu5));
	}

}
