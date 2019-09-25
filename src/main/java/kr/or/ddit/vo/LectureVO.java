package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 강의(스트리밍)
 * @author pc22
 *
 */
@Data
@NoArgsConstructor
public class LectureVO implements Serializable {
	private Long rnum;
	private String lecture_no; // 강의 번호
	private String lecture_teacher; // 강의 선생님 아이디
	private Integer ctgy_no; // 강의 과목 번호
	private String lecture_title; // 강의 제목
	private String lecture_content; // 강의 내용
	private Integer lecture_turn; // 강의 회차
	private String lt_no; // 강의 모집 번호
	
}
