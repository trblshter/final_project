package kr.or.ddit.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 쿠키 생성과 핸들링을 지원하는 유틸리티 클래스
 * CookieUtils 는 HttpServletRequest 에 의존해야 함.
 * 의존관계를 형성하는 방법.
 * 1. new 키워드로 의존 객체를 직접 생성(결합력 최상).
 * 2. Factory Object(Method) pattern
 * 3. Strategy Pattern(전략 패턴) : 
 * 		외부에서 전략을 주입할 수 있는 방법(setter, constructor)
 *      DI(Dependency Injection) pattern
 *      (setter injection, constructor injection)
 *      SRP : 단일 책임의 원칙
 *      OCP : 개방 폐쇄의 원칙
 *      LSP : 리스코프 치환의 원칙
 *      ISP : 인터페이스 분리의 원칙
 *      DIP/DRP : 의존성 주입의 원칙(중복 제거의 원칙)
 */
public class CookieUtils {
	private Map<String, Cookie> cookieMap;
	private HttpServletRequest request;
	private static String encoding = "UTF-8";
	public static void setEncoding(String encoding) {
		CookieUtils.encoding = encoding;
	}
	
	/**
	 * 쿠키값의 encoding 방식은 기본 UTF-8을 사용함.
	 * @param request
	 */
	public CookieUtils(HttpServletRequest request) {
		this(request, "UTF-8");
	}
	
	public CookieUtils(HttpServletRequest request, String encoding) {
		super();
		this.request = request;
		this.encoding = encoding;
		cookieMap = new LinkedHashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie tmp : cookies) {
				cookieMap.put(tmp.getName(), tmp);
			}
		}
	}
	
	public Cookie getCookie(String name) {
		return cookieMap.get(name);
	}
	
	/**
	 * 특정 쿠키의 값을 조회
	 * @param name : 조회할 쿠키명
	 * @return 명시적으로 결정된 encoding 방식이 없다면, UTF-8 이 사용됨.
	 * 		존재하지 않는댜면, null 반환.
	 * @throws IOException
	 */
	public String getCookieValue(String name) throws IOException {
		Cookie cookie = getCookie(name);
		String cookieValue = null;
		if(cookie!=null) {
			cookieValue = URLDecoder.decode( cookie.getValue(), encoding);
		}
		return cookieValue;
	}
	
	public boolean isExists(String name) {
		return cookieMap.containsKey(name);
	}
	
	/**
	 * 쿠키 생성
	 * @param name 쿠키명
	 * @param value 쿠키값, 명시적 설정이 없으면, UTF-8 로 인코딩
	 * @return
	 * @throws IOException
	 */
	public static Cookie createCookie(String name, String value) throws IOException {
		value = URLEncoder.encode(value, encoding);
		Cookie cookie = new Cookie(name, value);
		return cookie;
	}
	
	/**
	 * 쿠키 생성
	 * @param name
	 * @param value
	 * @param maxAge 쿠키의 만료시간 설정(초단위)
	 * @return
	 * @throws IOException
	 */
	public static Cookie createCookie(String name, String value, int maxAge) throws IOException{
		Cookie cookie = createCookie(name, value);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
	public static enum TextType {DOMAIN, PATH}
	
	/**
	 * 쿠키 생성
	 * @param name
	 * @param value
	 * @param text
	 * @param flag text 파라미터를 경로로 사용할지 도메인으로 사용할지 결정.
	 * @return
	 * @throws IOException
	 */
	public static Cookie createCookie(String name, String value, 
			String text, TextType flag) throws IOException{
		if(flag==null) throw new IllegalArgumentException("TextType은 null 일수 없음.");
		Cookie cookie = createCookie(name, value);
		if(TextType.PATH.equals(flag)) cookie.setPath(text);
		else cookie.setDomain(text);
		return cookie;
	}
	
	public static Cookie createCookie(String name, String value, 
			String text, TextType flag, int maxAge) throws IOException{
		Cookie cookie = createCookie(name, value, text, flag);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
	
	public static Cookie createCookie(String name, String value, 
								String path, String domain)throws IOException{
		Cookie cookie = createCookie(name, value);
		cookie.setPath(path);
		cookie.setDomain(domain);
		return cookie;
	}
	
	public static Cookie createCookie(String name, String value, 
								String path, String domain, int maxAge) throws IOException{
		Cookie cookie = createCookie(name, value, path, domain);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
}














