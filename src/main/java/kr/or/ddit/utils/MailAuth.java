package kr.or.ddit.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailAuth {
	final static String user = "choijsa2m@gmail.com"; //발신자의 이메일 아이디를 입력 
	final static String password = "eoejrdlsworoqkfdnjs"; 
	
	public static void sendMail(String user_email, String randomCode){
		Properties prop = new Properties(); 
		prop.put("mail.smtp.host", "smtp.gmail.com"); 
		prop.put("mail.smtp.port", 465);
		prop.put("mail.smtp.auth", "true"); 
		prop.put("mail.smtp.ssl.enable", "true"); 
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(user, password);
		            }
		        });
		try {
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(user));
	
	        //수신자메일주소
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user_email)); 
			
			// Subject
			message.setSubject("도와줘! 선생님 인증 번호입니다."); //메일 제목을 입력
			String content = "인증번호["+ randomCode +"]";
			// Text
			message.setContent(content, "text/html;charset=UTF-8");    //메일 내용을 입력
			
			// send the message
			Transport.send(message); ////전송
			System.out.println("message sent successfully...");
		} catch (AddressException e) {
		    throw new RuntimeException("주소 오류", e);
		} catch (MessagingException e) {
			throw new RuntimeException("메세지 오류",e);
		}
	}
}
