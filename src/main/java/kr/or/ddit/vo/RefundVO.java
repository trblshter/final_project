package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 환불 관리
 * @author pc22
 *
 */
@Data
@NoArgsConstructor
public class RefundVO implements Serializable {
	private Integer rf_no; //환불번호
	private Integer payment_no; //결제변호
	private String canceled_at; //환불날짜
}
