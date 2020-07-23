package com.lisaeva.email.controller.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.lisaeva.email.model.EmailAccount;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class EmailSendingService extends Service<Void> {

	private EmailAccount emailAccount;
	private String subject;
	private String recipient;
	private String content; 
	private List<File> attachments = new ArrayList<File>();
	
	public EmailSendingService(EmailAccount emailAccount, String subject, String recipient, String content, List<File> list) {
		this.emailAccount = emailAccount;
		this.subject = subject;
		this.recipient = recipient;
		this.content = content;
		this.attachments = list;
		
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					// Create the message
					MimeMessage mimeMessage = new MimeMessage(emailAccount.getSession());
					mimeMessage.setFrom(emailAccount.getAddress());
					mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
					mimeMessage.setSubject(subject);
					// Set the content
					Multipart multipart = new MimeMultipart();
					BodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setContent(content, "text/html");
					multipart.addBodyPart(messageBodyPart);
					mimeMessage.setContent(multipart);
					// Adding the attachments:
					if(attachments.size()>0) {
						for (File file: attachments) {
							MimeBodyPart mimeBodyPart = new MimeBodyPart();
							DataSource source = new FileDataSource(file.getAbsolutePath());
							mimeBodyPart.setDataHandler(new DataHandler(source));
							mimeBodyPart.setFileName(file.getName());
							multipart.addBodyPart(mimeBodyPart);
						}
					}
					// Sending the message:
					Transport transport = emailAccount.getSession().getTransport();
					transport.connect(
							emailAccount.getProperties().getProperty("outgoingHost"),
							emailAccount.getAddress(),
							emailAccount.getPassword());
					transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
					transport.close();
				} catch(MessagingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();

				}
				return null; 
			}
		};
	}
	
}
