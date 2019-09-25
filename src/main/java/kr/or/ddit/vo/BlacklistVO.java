package kr.or.ddit.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 블랙리스트
 * @author pc05
 *
 */
@Data
@EqualsAndHashCode(of= {"bl_id"})
@NoArgsConstructor
public class BlacklistVO implements Serializable {
	private int rnum; //보일 번호
	private String bl_id; // 블랙리스트 아이디
	private String bl_date; // 추가 날짜
	private String bl_reason; // 사유
}

