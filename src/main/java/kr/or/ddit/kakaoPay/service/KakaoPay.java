package kr.or.ddit.kakaoPay.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import kr.or.ddit.vo.ApplVO;
import kr.or.ddit.vo.KakaoPayApprovalVO;
import kr.or.ddit.vo.KakaoPayCancelVO;
import kr.or.ddit.vo.KakaoPayReadyVO;
import kr.or.ddit.vo.PaymentVO;
import lombok.extern.java.Log;

@Service
@Log
public class KakaoPay {
private static final String HOST = "https://kapi.kakao.com";
    
    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;
    private KakaoPayCancelVO kakaoPayCancelVO;
    
    public String kakaoPayReady(ApplVO appl) {
 
        RestTemplate restTemplate = new RestTemplate();
 
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "86e0f30185b790228cb4eee96da33cd7");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", Integer.toString(appl.getAppl_no()));
        params.add("partner_user_id", appl.getAppl_user());
        params.add("item_name", appl.getLt_title());
        params.add("quantity", "1");
        params.add("total_amount", Integer.toString(appl.getLt_price()));
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://localhost/helpSem/kakaoPaySuccess?what="+appl.getAppl_no());
        params.add("cancel_url", "http://localhost/helpSem/kakaoPayCancel");
        params.add("fail_url", "http://localhost/helpSem/kakaoPaySuccessFail");
 
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
 
        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
            
            log.info("" + kakaoPayReadyVO);
            
            return kakaoPayReadyVO.getNext_redirect_pc_url();
 
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        
        return "/pay";
        
    }
    
    public KakaoPayApprovalVO kakaoPayInfo(String pg_token, ApplVO appl) {
    	 
        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");
        
        RestTemplate restTemplate = new RestTemplate();
 
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "86e0f30185b790228cb4eee96da33cd7");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
 
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", Integer.toString(appl.getAppl_no()));
        params.add("partner_user_id", appl.getAppl_user());
        params.add("pg_token", pg_token);
        params.add("total_amount", Integer.toString(appl.getLt_price()));
        
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        
        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);
            
            return kakaoPayApprovalVO;
        
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public KakaoPayCancelVO kakaoCancel(PaymentVO pay) {
    	 
        log.info("KakaoPayCancelVO............................................");
        log.info("-----------------------------");
    	 
        RestTemplate restTemplate = new RestTemplate();
 
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "86e0f30185b790228cb4eee96da33cd7");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", pay.getCid());
        params.add("tid", pay.getTid());
        params.add("cancel_amount", Integer.toString(pay.getTotal()));
        params.add("cancel_tax_free_amount", "100");
        
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        
        try {
        	kakaoPayCancelVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/cancel"), body, KakaoPayCancelVO.class);
            log.info("" + kakaoPayCancelVO);
          
            return kakaoPayCancelVO;
        
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    
    
}
