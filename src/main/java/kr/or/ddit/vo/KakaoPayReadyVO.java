package kr.or.ddit.vo;

import java.util.Date;

import lombok.Data;

@Data
public class KakaoPayReadyVO {
	private String tid, next_redirect_pc_url;
    private Date created_at; //결제준비 요청시간
}
