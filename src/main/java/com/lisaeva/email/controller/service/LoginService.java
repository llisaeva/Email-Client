package com.lisaeva.email.controller.service;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import com.lisaeva.email.model.EmailAccount;

public class LoginService {
	
	private EmailAccount emailAccount;
	
	public LoginService(EmailAccount emailAccount) {
		this.emailAccount = emailAccount;
		login();
	}
	
	private void login() {
		
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
			}
		};
		
		Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
		emailAccount.setSession(session);
		
		try {
			Store store = session.getStore("imaps");
			store.connect(emailAccount.getProperties().getProperty("incomingHost"), 
						  emailAccount.getAddress(), 
						  emailAccount.getPassword());
			
			emailAccount.setStore(store);
			
			

			
			
//			Folder inbox = store.getFolder("INBOX");
//			 // open the INBOX folder
//			 inbox.open(Folder.READ_WRITE);
//			 Message m = inbox.getMessage(1); // get Message # 1
//			 String subject = m.getSubject(); // get Subject
//			 System.out.println(subject);
//			  m = inbox.getMessage(2);
//			 subject = m.getSubject(); 
//			 System.out.println(subject);
//			 m = inbox.getMessage(3);
//			 subject = m.getSubject(); 
//			 System.out.println(subject);
//			 m = inbox.getMessage(4);
//			 subject = m.getSubject(); 
//			 
//			 System.out.println(subject);
		} catch (NoSuchProviderException e) {

			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
	}
}
