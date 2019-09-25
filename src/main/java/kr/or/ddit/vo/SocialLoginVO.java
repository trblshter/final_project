package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class SocialLoginVO implements Serializable{
	private String user_id; //유저아이디
	private String sl_id; //소셜아이디
	private String sl_accessToken; //소셜토큰
	private String sl_email; //소셜이메일
}
