package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 과목 여러 개
 * @author pc22
 *
 */
@Data
@NoArgsConstructor
public class CategoriesVO implements Serializable {
	private Integer ctgys_no; // 강의 묶음 번호
	private String board_no; // 게시판 번호
	private Integer ctgy_no; // 강의 과목 번호
}
