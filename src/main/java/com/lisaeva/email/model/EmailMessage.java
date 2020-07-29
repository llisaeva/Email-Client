package com.lisaeva.email.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.Message;
import javax.mail.internet.MimeBodyPart;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmailMessage {
	private SimpleStringProperty sender;
    private SimpleObjectProperty<Date> date;
    private SimpleStringProperty title;
    private boolean selected = false;
    private Message message;
    private String demoMessage;
    private List<Attachment> attachmentList = new ArrayList<Attachment>();
	private boolean attachmentLoaded = false;
	private boolean isRead;
    
	public EmailMessage(String sender, Date date, String title, Message message, boolean isRead) {
		this.sender = new SimpleStringProperty(sender);
		this.date = new SimpleObjectProperty<Date>(date);
		this.title = new SimpleStringProperty(title);
		this.message = message;
		this.isRead = isRead;
	}

	public String getSender() { return sender.get(); }
	public Date getDate() { return date.get(); }
	public String getTitle() { return title.get(); }
	public Message getMessage() { return message; }
	public String getDemoMessage() { return demoMessage; }
	public List<Attachment> getAttachments() { return attachmentList; }
	public boolean isAttachmentLoaded() { return attachmentLoaded; }
	public void setAttachmentLoaded() { attachmentLoaded = true; }
	public void setDemoMessage(String demo) { this.demoMessage = demo; }
	public void setSelected(boolean b) { selected = b; }
	public void setRead(boolean b) { isRead = b; }
	public boolean isRead() { return isRead; }
	public boolean isSelected() { return selected; }
	public boolean hasAttachment() { return !attachmentList.isEmpty(); }
	
	public synchronized void addAttachment(MimeBodyPart mbp) {
		Attachment attachment = new Attachment(mbp);
		if (attachment.getName() != null) {
			if (!attachmentList.isEmpty()) {
				for (Attachment a : attachmentList) {
					if (a == null || !a.getName().equals(attachment.getName()))continue;
					else {return;}
				}
			}
			attachmentList.add(attachment);
		}
	}
}
