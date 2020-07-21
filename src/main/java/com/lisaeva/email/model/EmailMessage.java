package com.lisaeva.email.model;

import java.util.Date;

import javax.mail.Message;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmailMessage {
	private SimpleStringProperty sender;
    private SimpleObjectProperty<Date> date;
    private SimpleStringProperty title;
    private boolean selected;
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

	public boolean isHasAttachment() {
		return hasAttachment;
	}
	
	
	
	
	
    
    
}
