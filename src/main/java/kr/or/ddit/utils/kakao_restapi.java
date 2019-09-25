package kr.or.ddit.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class kakao_restapi {
	public JsonNode getAccessToken(String autorize_code) {
		 
	    final String RequestUrl = "https://kauth.kakao.com/oauth/token";

	    final List<NameValuePair> postParams = new ArrayList<NameValuePair>();

	    postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));

	    postParams.add(new BasicNameValuePair("client_id", "f377b4843432beed4bbe37489851dc69"));

	    postParams.add(new BasicNameValuePair("redirect_uri", "http://localhost/helpSem/login/kakao"));

	    postParams.add(new BasicNameValuePair("code", autorize_code));

	    final HttpClient client = HttpClientBuilder.create().build();

	    final HttpPost post = new HttpPost(RequestUrl);

	    JsonNode returnNode = null;

	    try {

	        post.setEntity(new UrlEncodedFormEntity(postParams));

	        final HttpResponse response = client.execute(post);

	        ObjectMapper mapper = new ObjectMapper();

	        returnNode = mapper.readTree(response.getEntity().getContent());

	    } catch (UnsupportedEncodingException e) {

	        e.printStackTrace();

	    } catch (ClientProtocolException e) {

	        e.printStackTrace();

	    } catch (IOException e) {

	        e.printStackTrace();

	    } finally {

	    }
	    return returnNode;
	}
	public JsonNode getAccessUser(String access_token) {
		final String RequestUrl = "https://kapi.kakao.com/v2/user/me";
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost post = new HttpPost(RequestUrl);
 
        // add header
        post.addHeader("Authorization", "Bearer " + access_token);
 
        JsonNode returnNode = null;
 
        try {
            final HttpResponse response = client.execute(post);
            final int responseCode = response.getStatusLine().getStatusCode();
 
            System.out.println("\nSending 'POST' request to URL : " + RequestUrl);
            System.out.println("Response Code : " + responseCode);
 
            // JSON 형태 반환값 처리
            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent());
 
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // clear resources
        }
 
        return returnNode;

	}
}
