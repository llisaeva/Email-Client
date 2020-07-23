package com.lisaeva.email.model;

import java.io.IOException;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmailMessage {
	private SimpleStringProperty sender;
    private SimpleObjectProperty<Date> date;
    private SimpleStringProperty title;
    private boolean selected = false;
    private boolean hasAttachment;
    private Message message;
    
	public EmailMessage(String sender, Date date, String title, Message message) {
		this.sender = new SimpleStringProperty(sender);
		this.date = new SimpleObjectProperty<Date>(date);
		this.title = new SimpleStringProperty(title);
		this.message = message;
	}

	public String getSender() {
		return sender.get();
	}

	public Date getDate() {
		return date.get();
	}

	public String getTitle() {
		return title.get();
	}

	public Message getMessage() {
		return message;
	}

	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean b) {
		selected = b;
	}

	public boolean isHasAttachment() {
		return hasAttachment;
	}
	
	public String getDemoMessage() throws IOException, MessagingException {
		return message.getContent().toString();
	}
	
	
	
	
	
    
    
}
