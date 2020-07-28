package com.lisaeva.email.model;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

public class Attachment {

	MimeBodyPart mbp;
	Image thumbnail;
	
	public Attachment(MimeBodyPart mbp) {
		this.mbp = mbp;
		
		try {
			if (mbp.getContentType().contains("IMAGE")) {
				
				Service<Void> addImgService = new Service<Void>() {
					@Override
					protected Task<Void> createTask() {
						return new Task<Void>() {
							@Override
							protected Void call() throws Exception {
								Image img = new Image(mbp.getInputStream());
								thumbnail = img;
								return null;
					}};}};
					
					addImgService.start();	
			}
		} catch (MessagingException e) { e.printStackTrace(); }
	}

	public String getName() {
		String name = null;
		try { name = mbp.getFileName(); } 
		catch (MessagingException e) { e.printStackTrace(); }
		return name;
	}
	
	public Image getThumbnail() { return thumbnail; }
}
