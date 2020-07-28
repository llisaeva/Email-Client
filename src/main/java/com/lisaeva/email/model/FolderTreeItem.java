package com.lisaeva.email.model;

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
	
	public Folder getFolder() { return folder; }
	public ObservableList<EmailMessage> getEmailMessages() { return emails; }
	public String toString() { return name; }
	
	private void addEmail(EmailMessage email) { emails.add(email); }
	
	public void addEmail(Message message) {
//		boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
		EmailMessage email = fetchMessage(message);
//		System.out.println(email.getTitle());
		addEmail(email);
//		if(!messageIsRead)
//			incrementMessagesCount();
//		return emailMessage;
	}
	
	private EmailMessage fetchMessage(Message message) {
		EmailMessage email = null;
		try {
			email = new EmailMessage(
				message.getFrom()[0].toString(),
				message.getSentDate(),
				message.getSubject(),
				message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return email;
	}
	
	public void addEmailToTop(Message message) {
		EmailMessage emailMessage = fetchMessage(message);
		emails.add(0, emailMessage);
		
	}
	
}
//message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
//message.getSize(),
//
//messageIsRead,
//message





