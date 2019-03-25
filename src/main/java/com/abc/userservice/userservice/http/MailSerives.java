package com.abc.userservice.userservice.http;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class MailSerives {
	 public static void sendEmail(String targetEmail,String link) {
		  try{
	            String host ="smtp.gmail.com" ;
	            String user = "	finalprojectmsi1901@gmail.com";
	            String pass = "qwe123ASD";
	            String to = targetEmail;
	            String from = "	finalprojectmsi1901@gmail.com";
	            String subject = "Password Reset Request";
	            String messageText = "Please  go to this link to reset your password: \n http://localhost:4200/reset/" + link;
	            boolean sessionDebug = false;

	            Properties props = System.getProperties();

	            props.put("mail.smtp.starttls.enable", "true");
	            props.put("mail.smtp.host", host);
	            props.put("mail.smtp.port", "587");
	            props.put("mail.smtp.auth", "true");
	            props.put("mail.smtp.starttls.required", "true");

	          //  java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	            Session mailSession = Session.getDefaultInstance(props, null);
	            mailSession.setDebug(sessionDebug);
	            Message msg = new MimeMessage(mailSession);
	            msg.setFrom(new InternetAddress(from));
	            InternetAddress[] address = {new InternetAddress(to)};
	            msg.setRecipients(Message.RecipientType.TO, address);
	            msg.setSubject(subject); msg.setSentDate(new Date());
	            msg.setText(messageText);

	           Transport transport=mailSession.getTransport("smtp");
	           transport.connect(host, user, pass);
	           transport.sendMessage(msg, msg.getAllRecipients());
	           transport.close();
	           System.out.println("message send successfully");
	        }catch(Exception ex)
	        {
	            System.out.println(ex);
	        }
	  }
	 
	 
	 public static String sh(String s) {
			StringBuilder result = new StringBuilder();
			int spaceIndex = s.indexOf(' ');
			s = s.replaceAll(" ", "");
			result.append((char)rng())
				.append((char)rng())
				.append((char)rng());
			if(spaceIndex!=-1) {
				result
				.append((char)(71+spaceIndex));
			}
			else {
				result.append((char)70);
			}
			result
				.append((char)(70+s.length()));
			
			for(int i =0;i<s.length();i=i+2) {

				try {
					result.append(getChar(s.charAt(i+1)));
				}
				catch(Exception e) {
					result.append(getChar(s.charAt(i)));
				break;
				}
				
				result.append(getChar(s.charAt(i)));
			}
			System.out.println(result.toString());
			for(int i =result.length() ; i<25;i++) {
				result.append((char)rng());
			}
		return result.toString();
		}
		
		
		
		public static String decode(String s) {
			System.out.println(s);
			StringBuilder result = new StringBuilder();
			int length = s.charAt(4)-70;
			System.out.println("string length "+length);
			int spaceIndex =  (s.charAt(3)-71)+5;
			System.out.println("space index: "+spaceIndex);
			for(int i = 5 ; i<length+5;i=i+2) {
		
				
				
				
					if(i==spaceIndex) {
						result.append(' ');
					}
					
					if(i==length+4) {
						
						result.append(getCharBack(s.charAt(i)));
						break;
					}
					
					if(i+1==spaceIndex) {
						result.append(getCharBack(s.charAt(i+1)));
						result.append(' ');
						result.append(getCharBack(s.charAt(i)));
					}
					else {
						result.append(getCharBack(s.charAt(i+1)));
						result.append(getCharBack(s.charAt(i)));
					}
					
					
				}
					
			
				
				
				
				
				
			
			return result.toString();
		}
		
		private static char getCharBack(char c) {
			
			if(c==122) {
				return (char) 122;
			}
			if(c==90) {
				return (char)90;
			}
			return (char) (c-1);
		}
		
		
		
		
		
		private static char getChar(char c) {
		
			if(c==122) {
				return (char) 122;
			}
			if(c==90) {
				return (char)90;
			}
			return (char) (c+1);
		}
		
		

		
		private static int rng() {
			Random r = new Random();
		
			if(Math.random()>0.3&&Math.random()<0.6) {
				return r.nextInt((57 - 48) + 1) + 48;
			}
			else if(Math.random()<0.5) {
				return r.nextInt((122 - 97) + 1) + 97;
			}
			else {
				return  r.nextInt((90 - 65) + 1) + 65;
			}
		
		}
}
