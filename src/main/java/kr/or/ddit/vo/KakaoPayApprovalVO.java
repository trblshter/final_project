package kr.or.ddit.vo;

import java.util.Date;

import lombok.Data;

@Data
public class KakaoPayApprovalVO {
	private String aid, tid, cid, sid;
    private String partner_order_id, partner_user_id, payment_method_type;
    private String item_name, item_code, payload;
    private Integer quantity, tax_free_amount, vat_amount;
    private Date created_at, approved_at;
    
    private AmountVO amount;
    private Integer total;
    
    private String issuer_corp;
    private String bin;
    private String card_type;
}
