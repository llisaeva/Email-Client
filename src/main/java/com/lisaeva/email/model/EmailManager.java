package com.lisaeva.email.model;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import com.lisaeva.email.controller.service.FetchFoldersService;
import com.lisaeva.email.controller.service.FolderUpdateService;
import com.lisaeva.email.controller.service.LoginService;
import com.lisaeva.email.view.ViewGenerator;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class EmailManager {
	
	private static final String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "/Downloads/";
	private FolderTreeItem foldersRoot = new FolderTreeItem();
	private FolderTreeItem selectedFolder;
	private EmailMessage selectedMessage;
	private EmailAccount emailAccount;
	private FolderUpdateService folderUpdateService;
	
	public void login(String account, String password) {
		emailAccount = new EmailAccount(account, password);
		new LoginService(emailAccount);
		folderUpdateService = new FolderUpdateService(emailAccount.getFolders());
		folderUpdateService.start();
	}
	
	public void fetchFolders() {
		FetchFoldersService ffs = new FetchFoldersService(emailAccount.getStore(), foldersRoot, emailAccount.getFolders());
		ffs.setOnSucceeded(e -> ViewGenerator.showMainWindow(this));
		ffs.start();
	}
	
	public static String getDownloadPath() { return LOCATION_OF_DOWNLOADS; }
	public FolderTreeItem getFolderRoot() { return foldersRoot; }
	public void setSelectedFolder(FolderTreeItem item) { selectedFolder = item; }
	public void setSelectedMessage(EmailMessage emailMessage) { this.selectedMessage = emailMessage; }
	public EmailMessage getSelectedMessage() { return selectedMessage; }	
	public FolderTreeItem getSelectedFolder() { return selectedFolder; }
	public EmailAccount getEmailAccount() { return emailAccount; }	
	
	public void deleteSelectedMessage() {
		try {
/* !! */		Service<Void> deleteMessageService = new Service<Void>() {
				@Override
				protected Task<Void> createTask() {
					return new Task<Void>() {
						@Override
						protected Void call() throws Exception {
							Message message = selectedMessage.getMessage();
							Folder folder = message.getFolder();
							folder.close();
							folder.open(Folder.READ_WRITE);
							message.setFlag(Flags.Flag.DELETED, true);
//							message.saveChanges();
//							selectedFolder.getFolder().setFlags(selectedMessage.getMessage(), Flags.Flag.DELETED, true);
							return null;
						}
						
					};
				}
				
			}; deleteMessageService.setOnSucceeded(e -> {
				try {
					selectedMessage.getMessage().getFolder().expunge();
					selectedFolder.getEmailMessages().remove(selectedMessage);
				} catch (MessagingException e1) { e1.printStackTrace(); }
			}); deleteMessageService.start();
			
		} catch (Exception e) { e.printStackTrace(); }	
	}
}
