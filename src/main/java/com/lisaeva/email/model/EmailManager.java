package com.lisaeva.email.model;

import com.lisaeva.email.controller.service.FetchFoldersService;
import com.lisaeva.email.controller.service.LoginService;


public class EmailManager {
	
	private FolderTreeItem foldersRoot = new FolderTreeItem();
	private FolderTreeItem selectedFolder;
	private EmailMessage selectedMessage;
	private EmailAccount emailAccount;
	
	public void login(String account, String password) {
		emailAccount = new EmailAccount(account, password);
		new LoginService(emailAccount);
	}
	
	public void fetchFolders() {
		FetchFoldersService ffs = new FetchFoldersService(emailAccount.getStore(), foldersRoot, emailAccount.getFolders());
		ffs.start();
	}
	
	public FolderTreeItem getFolderRoot() {
		return foldersRoot;
	}
	
	public void setSelectedFolder(FolderTreeItem item) {
		selectedFolder = item;
	}

	public void setSelectedMessage(EmailMessage emailMessage) {
		this.selectedMessage = emailMessage;
		
	}
	
	
}
