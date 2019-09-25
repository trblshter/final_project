package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 결제
 * @author pc22
 *
 */
@Data
@NoArgsConstructor
public class PaymentVO implements Serializable {
	private int rnum; //보여질 번호
	private Integer payment_no; //결제번호
	private Integer partner_order_id; //신청번호
	private String cid;
	private String tid;
	private int total;
	private int tax_free;
	private int vat;
	private String payment_method_type; //결제방법
	private String approved_at;
	private String partner_user_id; //회원아이디
	private Integer status; //환불상태
	
	private String lt_no; //강의신청번호
	private String lt_writer; //결제강의 선생님아이디
	private String lt_title; //결제강의 제목
	private String lt_start_date; //강의 시작날짜
	private String user_name; //결제강의 선생님 이름
	private List<CategoryVO> categoryList; //결제강의의 카테고리들
}
