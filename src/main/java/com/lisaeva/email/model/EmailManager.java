package com.lisaeva.email.model;

import com.lisaeva.email.controller.service.FetchFoldersService;
import com.lisaeva.email.controller.service.LoginService;
import com.lisaeva.email.view.ViewGenerator;

public class EmailManager {
	
	private static final String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "/Downloads/";
	private EmailAccount emailAccount;
	private FolderTreeItem foldersRoot = new FolderTreeItem();
	private FolderTreeItem selectedFolder;
	private EmailMessage selectedMessage;
	
	public void login(String account, String password) {
		emailAccount = new EmailAccount(account, password);
		new LoginService(emailAccount);
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
	
}
