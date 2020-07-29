package com.lisaeva.email.model;

import javax.mail.Flags;
import com.lisaeva.email.controller.service.FetchFoldersService;
import com.lisaeva.email.controller.service.FolderUpdateService;
import com.lisaeva.email.controller.service.LoginService;


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
			selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
//			selectedFolder.getFolder().setFlags(selectedMessage.getMessage(), Flags.Flag.DELETED, true);
			selectedMessage.getMessage().getFolder().expunge();
			selectedFolder.getEmailMessages().remove(selectedMessage);
//			while (!folder.getName().toLowerCase().contains("inbox"))folder = folder.getParent();
//			folder.expunge();	
		} catch (Exception e) { e.printStackTrace(); }	
	}
}
