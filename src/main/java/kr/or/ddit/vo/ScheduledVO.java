package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 강의 일정
 * @author pc22
 *
 */
@Data
@NoArgsConstructor
public class ScheduledVO implements Serializable {
	
	private String sc_no; // 스케줄 번호
	private String sc_writer; // 스케줄 작성자
	private String lt_no; // 강의 번호
	private Integer sc_turn; // 강의 회자
	private Date sc_startday; // 시작 날짜
	private Date sc_endday; // 끝나는 날짜
	private String sc_content; // 내용
	private String sc_title;
	private String sc_bgcolor;
	private String sc_tcolor;
	
}
