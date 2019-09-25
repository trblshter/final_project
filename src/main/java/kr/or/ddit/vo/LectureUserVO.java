package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 학생이 작성하는 강의 신청
 * @author pc05
 *
 */
@Data
@NoArgsConstructor
public class LectureUserVO implements Serializable{
	private Integer rnum;
	private String lu_no; //신청번호
	private String lu_date; //날짜
	private String lu_writer; //작성자
	private String lu_title; //신청제목
	private String lu_content; //신청내용
	private Integer team_no; //팀번호
	private Integer lu_completed; //신청완료유무
	private Integer lu_views; //조회수
	private String lu_tutor; //선택된선생님
}
