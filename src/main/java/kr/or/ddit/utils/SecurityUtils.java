package kr.or.ddit.utils;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.management.RuntimeErrorException;

/**
 * 정보보호 : 기밀성(암호화), 일관성(접근 제어)
 * Encoding/Decoding(부호화) : URLEncode(% encoding), Base64 
 *             전송이나 저장을 목적으로 매체를 이용하는 경우,
 *             매체별 특성에 따라 매체가 인지할 수 있는 형태로 데이터를 변환.
 * Encrypting/Decrypting(암호화) : 데이터 보호를 목적으로 허가받지 않은 사용자가 읽을 수 없도록 데이터를 변환.
 *
 * 암호화 알고리즘의 종류
 * 1. 단방향 암호화(해시 함수) : 복화화를 통해 평문으로 복원이 불가능한 방식.
 * 		hash 함수 : 평문 -> 일정한 길이의 문자(해시코드) 생성
 *    MessageDigest  : MD5(128bit) - 비둘기집의 오류 가능성.
 *                A -> MD5 -> 128 길이 문자
 *                B -> MD5 -> 128 길이 문자
 *                SHA-1(128bit), SHA-2(256, 384, 512)
 * 2. 양방향 암화화 : 복호화를 통해 평문 복원이 가능한 방식
 *     1) 대칭키 방식(동일키,비밀키) : 암/복호화에 동일한 비밀키 하나가 사용.
 *      	  하나의 비밀키를 공유하기 위해 키 분배의 문제가 발생함.
 *      	 AES (블럭 암호화-128bit)
 *     2) 비대칭키 방식(공개키 방식) : 암호화와 복호화에 한쌍(공개키,개인키)! 의 키가 사용.
 *     		전자서명에 개인키 암호화 방식이 사용됨.
 *           RSA
 *     
 * 
 */
public class SecurityUtils {
	public static void main(String[] args) throws Exception{
		String plain = "대덕인재개발원203호";
		Map<String, Object> specs = new HashMap<String, Object>();
		
		generateRSAKeys(specs);
		
		String encoded = encryptRSA2048ForPrivateKey(plain, specs);
		System.out.println(encoded);
		plain = decryptRSA2048ForPublicKey(encoded, specs);
		System.out.println(plain);
		
	}
	
	private static String decryptRSA2048(String encoded, Key key)throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decoded = Base64.getDecoder().decode(encoded);
		byte[] decrypted = cipher.doFinal(decoded);
		return new String(decrypted);
	}
	
	public static String encryptRSA2048ForPublicKey(String plain, Map<String, Object> specs) throws Exception {
		PublicKey publicKey = (PublicKey) specs.get("publicKey");
		return encrypRSA2048(plain, publicKey);
	}
	public static String encryptRSA2048ForPrivateKey(String plain, Map<String, Object> specs) throws Exception {
		PrivateKey privateKey = (PrivateKey) specs.get("privateKey");
		return encrypRSA2048(plain, privateKey);
	}
	public static String decryptRSA2048ForPublicKey(String plain, Map<String, Object> specs) throws Exception {
		PublicKey publicKey = (PublicKey) specs.get("publicKey");
		return decryptRSA2048(plain, publicKey);
	}
	public static String decryptRSA2048ForPrivateKey(String plain, Map<String, Object> specs) throws Exception {
		PrivateKey privateKey = (PrivateKey) specs.get("privateKey");
		return decryptRSA2048(plain, privateKey);
	}
	
	public static void generateRSAKeys(Map<String, Object> specs) throws Exception{
		KeyPairGenerator pairGen = KeyPairGenerator.getInstance("RSA");
		pairGen.initialize(2048); // 1024, 2048
		KeyPair keyPair = pairGen.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		specs.put("privateKey", privateKey);
		specs.put("publicKey", publicKey);
	}
	
	private static String encrypRSA2048(String plain, Key key)throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key); // 전자서명, 부인방지
		byte[] input = plain.getBytes();
		byte[] encrypted = cipher.doFinal(input);
		String encoded = Base64.getEncoder().encodeToString(encrypted);
		System.out.println(encoded);
		
		return encoded;
	}
	
	public static String decryptAES128(String encoded, Map<String, Object> specs) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKey key = (SecretKey) specs.get("key");
		IvParameterSpec iv = (IvParameterSpec) specs.get("iv");
		byte[] decoded = Base64.getDecoder().decode(encoded);
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] decrypted = cipher.doFinal(decoded);
		return new String(decrypted);
	}
	
	public static String encyptAES128(String plain, Map<String, Object> specs) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		SecretKey key = keyGen.generateKey();
		String ivValue = (String) specs.get("ivValue");
		if(ivValue==null) ivValue = UUID.randomUUID().toString();
		MessageDigest md = MessageDigest.getInstance("MD5");
		// 128 bit 의 초기벡터를 생성.
		IvParameterSpec iv = new IvParameterSpec(md.digest(ivValue.getBytes()));
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		byte[] input = plain.getBytes();
		byte[] encrypted = cipher.doFinal(input);
		String encoded = Base64.getEncoder().encodeToString(encrypted);
		System.out.println(encoded);
		specs.put("key", key);
		specs.put("iv", iv);
		return encoded;
	}
	
	public static String sha512(String plain) {
		MessageDigest md;
		String encoded = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			byte[] input = plain.getBytes();
			byte[] hashvalue = md.digest(input); // 단방향 암호문
			encoded = Base64.getEncoder().encodeToString(hashvalue);
			System.out.println(encoded);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("암호화 오류", e);
		}
		return encoded;
	}
}









