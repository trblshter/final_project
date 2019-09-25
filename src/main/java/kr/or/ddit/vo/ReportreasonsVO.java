package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReportreasonsVO implements Serializable {
	private Integer rrs_no; // 신고사유번호
	private String rrs_name; // 신고사유이름
}
