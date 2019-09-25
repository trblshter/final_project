package kr.or.ddit.vo;

import lombok.Data;

@Data
public class KakaoPayCancelVO {
	private String aid, tid, cid, status;
	private String partner_order_id, partner_user_id;
	private String payment_method_type, payload;
	private String item_name, item_code;
	private Integer quantity;
	private AmountVO amount, canceled_amount, cancel_available_amount;
}
