package com.lisaeva.email.model;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

public class Attachment {
	
	private MimeBodyPart mbp;
	private String name;
	private String downloadPath;
	
	public Attachment(MimeBodyPart mbp) {
		this.mbp = mbp;
		name = getName();
		
		downloadPath = EmailManager.getDownloadPath() + name;
		
	}

	public MimeBodyPart getMBP() { return mbp; }
	public String getDownloadPath() { return downloadPath; }
	
	public String getName() {
		String name = null;
		try { name = mbp.getFileName(); } 
		catch (MessagingException e) { e.printStackTrace(); }
		return name;
	}

	public void downloadAttachment() {
			Service<Void> service = new Service<>() {

				@Override
				protected Task<Void> createTask() {
					return new Task<Void>() {
						@Override
						protected Void call() throws Exception {
							mbp.saveFile(downloadPath);
							return null;
						}
					};
				}
			};
			service.start();
		
	}
}
