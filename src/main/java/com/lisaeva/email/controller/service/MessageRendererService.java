package com.lisaeva.email.controller.service;

import java.io.IOException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import com.lisaeva.email.model.EmailMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class MessageRendererService extends Service<Object>{
	private EmailMessage emailMessage;
	private StringBuffer stringBuffer;

	public MessageRendererService(StringBuffer stringBuffer) { this.stringBuffer = stringBuffer; }
	
	public MessageRendererService(EmailMessage email) {
		this.emailMessage = email;
		this.stringBuffer = new StringBuffer();
	}

	@Override
	protected Task<Object> createTask() {
		try { loadMessage(); } 
		catch (Exception e) { e.printStackTrace(); }
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception { return null; }
		};
	}
	
	private void loadMessage() throws MessagingException, IOException {
		stringBuffer.setLength(0); 
		Message message = emailMessage.getMessage();
		String contentType = message.getContentType();
		if(isSimpleType(contentType)){
			stringBuffer.append(message.getContent().toString());
		} else if (isMultipartType(contentType)) {
			Multipart multipart = (Multipart) message.getContent();
			loadMultipart(multipart, stringBuffer);
		} emailMessage.setDemoMessage(getDemoMessage());
	}
	
	private void loadMultipart(Multipart multipart, StringBuffer stringBuffer) throws MessagingException, IOException {
		for (int i = multipart.getCount() - 1; i >=0; i--) {
			BodyPart bodyPart = multipart.getBodyPart(i);
			String contentType = bodyPart.getContentType();
			if (isSimpleType(contentType)) {
				stringBuffer.append(bodyPart.getContent().toString());
			} else if (isMultipartType(contentType)){
				Multipart multipart2 = (Multipart) bodyPart.getContent();
				loadMultipart(multipart2,stringBuffer); 
			} else if (!isTextPlain(contentType)) {				
				MimeBodyPart mbp = (MimeBodyPart) bodyPart;
				if (!emailMessage.isAttachmentLoaded())emailMessage.addAttachment(mbp);
			}
		}
	}
	
	private boolean isTextPlain(String contentType) {
		return contentType.contains("TEXT/PLAIN");
	}

	private boolean isSimpleType(String contentType) {
		if(contentType.contains("TEXT/HTML") ||
		   contentType.contains("mixed") ||
		   contentType.contains("text")) {
			return true;
		} else return false;
	}
	
	private boolean isMultipartType(String contentType) {
		if (contentType.contains("multipart"))return true;
		else return false;
	}
	
	private String getDemoMessage() {
		String content = stringBuffer.toString();
		content = content.replaceAll("\\<.*?\\>", "");
		content = content.replaceAll("[.].*[{].*[}]", "");
		content = content.replaceAll("\\&nbsp;", " ");
		content = content.replaceAll("[/]", "");
		content = content.replaceAll("\s+|\\v+|\\h+", " ");
		content = content.replaceAll("[<][!][-][-].*[-][-][>]", "");
		content = content.replaceAll("[@].*[{].*[}]", "");
		content = content.replaceAll("[a-z]+[.][a-zA-Z]+\\h*[{].*[}]", "");
		content = content.replaceAll("[a-z]+[{].*[}]","");
		content = content.replaceAll("[*]\\h*[{].*[}]","");
		content = content.strip();
		if (content.length() > 100)content = content.substring(0, 100);
		if (content == null)content = "";
		return content;
	}
	
	public StringBuffer getStringBuffer() { return stringBuffer; }
	
	public void setEmailMessage(EmailMessage emailMessage) { this.emailMessage = emailMessage; }	
}
