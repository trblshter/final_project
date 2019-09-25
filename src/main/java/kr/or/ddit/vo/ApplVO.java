package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 신청
 * @author pc22
 *
 */
@Data
@EqualsAndHashCode(of= {"appl_no"})
@NoArgsConstructor
public class ApplVO implements Serializable {
	private int rnum; //보여질 번호
	private Integer appl_no; // 신청 번호
	private String appl_user; // 아이디
	private String lt_no; // 강의 번호
	private String appl_date; // 신청날짜
	private Integer payment_ok; //결제완료
	private Integer lt_recruit; //학생인원수
	private Integer lt_completed; //모집완료 여부
	
	private String lt_title; //강의제목
	private Integer lt_price; //강의가격
	private String user_name; //선생님이름
	
	private String team_member1;
	private String team_member2;
	private String team_member3;
	private String team_member4;
}
