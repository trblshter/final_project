package kr.or.ddit.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of= {"rep_no", "bo_no"})
public class ReplyVO {

	public ReplyVO(String bo_no) {
		super();
		this.bo_no = bo_no;
	}
	
	private Long rnum;
	private Long rep_no;
	private String bo_no;
	private String rep_writer;
	private String rep_pass;
	private String rep_date;
	private String rep_ip;
	private String rep_mail;
	private String rep_content;
}
