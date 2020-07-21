package com.lisaeva.email.model;

import java.util.ArrayList;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class FolderTreeItem extends TreeItem<String>{
	
	private String name;
	private Folder folder;
	private ObservableList<EmailMessage> emails;
	
	public FolderTreeItem() {
		super("");
		this.name = "";
	}
	
	public FolderTreeItem(Folder folder) {
		super(folder.getName());
		emails = FXCollections.observableArrayList();
		this.name = folder.getName();
		this.folder = folder;
	}
	
	public Folder getFolder() {return folder;}
	public String toString() {return name;}
	
	private void addEmail(EmailMessage email) {
		
		emails.add(email);
		
	}
	
	public void addEmail(Message message) throws MessagingException {
//		boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
		EmailMessage email = new EmailMessage(
				message.getFrom()[0].toString(),
				message.getSentDate(),
				message.getSubject(),
				message);
//		System.out.println(email.getTitle());
		addEmail(email);
//		if(!messageIsRead)
//			incrementMessagesCount();
//		return emailMessage;
	}
	
	public ObservableList<EmailMessage> getEmailMessages() {
		return emails;
	}
	
}


//message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
//message.getSize(),
//
//messageIsRead,
//message





