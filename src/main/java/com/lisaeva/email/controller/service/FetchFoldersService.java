package com.lisaeva.email.controller.service;

import java.util.ArrayList;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

import com.lisaeva.email.model.FolderTreeItem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FetchFoldersService extends Service<Void>{
	private ArrayList<Folder> folderList;
	private FolderTreeItem foldersRoot;
	private Store store;
	
	public FetchFoldersService(Store store, FolderTreeItem foldersRoot, ArrayList<Folder> folderList) {
		this.store = store;
		this.foldersRoot = foldersRoot;
		this.folderList = folderList;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				fetchFolders();
				return null;
			}

		};
	}
	
	private void fetchFolders() throws MessagingException {
		Folder[] folders = store.getDefaultFolder().list();
		handleFolders(folders, foldersRoot);
	}

	private void handleFolders(Folder[] folders, FolderTreeItem foldersRoot) throws MessagingException {
		for(Folder folder: folders) {
			folderList.add(folder);
			FolderTreeItem folderTreeItem = new FolderTreeItem(folder);
			foldersRoot.getChildren().add(folderTreeItem);
//			System.out.println("added folder");
			foldersRoot.setExpanded(true);
			fetchMessagesOnFolder(folderTreeItem);
//			addMessageListenerToFolder(folder, emailTreeItem);
//			if (folder.getName().equalsIgnoreCase("inbox")) {
//				EmailManager.setUpInbox(emailTreeItem);
//			}
			if (folder.getType() == Folder.HOLDS_FOLDERS) {
				Folder[] subFolders = folder.list();
				handleFolders(subFolders, folderTreeItem);
			}
			
		}
		
	}
	
	private void fetchMessagesOnFolder(FolderTreeItem folderTreeItem) {
//		System.out.println("fetchMessages called");
		Folder folder = folderTreeItem.getFolder();
		Service<Void> fetchMessagesService = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						if(folder.getType() != Folder.HOLDS_FOLDERS) {
//							System.out.println("folder does not hold folders");
							folder.open(Folder.READ_WRITE);
							int folderSize = folder.getMessageCount();
//							System.out.println(folderSize);
							for(int i = folderSize; i > 0; i--) {
								folderTreeItem.addEmail(folder.getMessage(i));
//								System.out.println("added mess");
							}
						}
						return null;
					}
				};
			}
		};
		fetchMessagesService.start();
	}
}
