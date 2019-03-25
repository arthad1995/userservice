package com.abc.userservice.userservice.services;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abc.userservice.userservice.Daos.UserDao;
import com.abc.userservice.userservice.beans.Notification;
import com.abc.userservice.userservice.beans.User;
import com.abc.userservice.userservice.http.MailSerives;
import com.abc.userservice.userservice.http.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServices {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDao ud;
	   @Autowired
	    private JmsTemplate jmsQueueTemplate;

	@Transactional(propagation = Propagation.REQUIRED)
	public Response<String> register(User user) throws Throwable {
		// checked
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		ud.save(user);
		return new Response<String> (true);
	}

	public Response<User> getUserById(int id) {
		
		Optional<User> u = ud.findById(id);
		System.out.println(u);
		if (!u.isPresent()) {
			return new Response<User>(false,"user not found",null);
		}
		
		return new Response<User>(true,u.get());
	}
	public Response<User> getUserByName(String name) {
		User u = ud.findByUsername(name);
		if(u==null) {
			return new Response<User>(false,"user not found",null);
		}
		
		return new Response<User>(true,"",u);
	}
	
	public Response<List<User>> getAllUser() {
		// checked
		List<User> list = ud.findAll(); 
		return new Response<List<User>>(true,list);
	}
	
	
	public Response<String> updateUser(User user) {
		User u = ud.findByUsername(user.getUsername());
		System.out.println(u + " from data base");
		System.out.println(user + " from para");
		if(u==null) {
			return new Response<String> (false,"user not found");
		}
		if(user.getEmail()!=null) {
			u.setEmail(user.getEmail());
		}
		if(user.getName()!=null) {
			u.setName(user.getName());
		}
		if(user.getPassword()!=null) {
			u.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		ud.save(u);
		return new Response<String> (true);
	}
	
	public Response<String> sendResetEmail(String name){
		User u = ud.findByUsername(name);
		if(u==null) {
			return new Response<>(false);
		}
		String link = MailSerives.sh(name);
		System.out.println(link);
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				MailSerives.sendEmail(u.getEmail(), link);
				
			}
		});
		t.start();
		
		
		return new Response<>(true);
		
	}
	
	
	public Response<String> resetPwd(User user){
		System.out.println(user);
		User u = ud.findByUsername(MailSerives.decode(user.getUsername()));
		u.setPassword(passwordEncoder.encode(user.getPassword()));
		ud.save(u);
		return new Response<>(true);
	}
	
	public Response<String>  delete(int id) {
		try {
			Optional<User> user = ud.findById(id);
	
			if (!user.isPresent()) {
				return new Response<String> (false,"user not found");
			}
			ud.delete(user.get());
		
		return new Response<String> (true);
	}
		catch(Exception e) {
			return new Response<String> (false, e.getMessage());
		}
	
	
}
	
	@JmsListener(destination = "findUsername", containerFactory = "userInfoListener")
	public void receive(Message msg) throws JMSException {
		System.out.println("********  Getting MSG  ***********");
		System.out.println(msg);
		ObjectMessage objectMessage = (ObjectMessage) msg;
		String s = (String) objectMessage.getObject();
		
		
		try {
			Notification notification = new ObjectMapper().readValue(s, Notification.class);
			String content = notification.getContent();
			int userId = getNumbers(content);
			System.out.println("this is user that I want!!!!!!!" + ud.findById(userId).get());
			System.out.println("this is user that I want!!!!!!!" + ud.findById(userId).get().getName());
			String name = ud.findById(userId).get().getName();
			content = name +" "+content.substring(content.indexOf("reply"));
			notification.setContent(content);
			final String notiToSent=  new ObjectMapper().writeValueAsString(notification);
			
			jmsQueueTemplate.send("sendUsernme", new MessageCreator() {
	            @Override
	            public Message createMessage(Session session) throws JMSException {
	                return session.createObjectMessage(notiToSent);
	            }
	        });
			
			
			
			
			
			
			
			
			
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	private int getNumbers(String s) {

	    String[] n = s.split(""); //array of strings
	    StringBuffer f = new StringBuffer(); // buffer to store numbers

	    for (int i = 0; i < n.length; i++) {
	        if((n[i].matches("[0-9]+"))) {// validating numbers
	            f.append(n[i]); //appending
	        }else {
	            //parsing to int and returning value
	            return Integer.parseInt(f.toString()); 
	        }   
	    }
	    return 0;
	 }
	
}
