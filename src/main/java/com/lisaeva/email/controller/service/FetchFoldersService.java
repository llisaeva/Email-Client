package com.lisaeva.email.controller.service;

import java.util.ArrayList;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import com.lisaeva.email.model.FolderTreeItem;
import com.lisaeva.email.view.IconResolver;
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
			folderTreeItem.setGraphic(IconResolver.getIcon(folder.getName()));
			foldersRoot.getChildren().add(folderTreeItem);
			foldersRoot.setExpanded(true);
			fetchMessagesOnFolder(folderTreeItem);
			addMessageListenerToFolder(folder, folderTreeItem);
//			if (folder.getName().equalsIgnoreCase("inbox")) {
//				EmailManager.setUpInbox(emailTreeItem);
//			}
			if (folder.getType() == Folder.HOLDS_FOLDERS) {
				Folder[] subFolders = folder.list();
				handleFolders(subFolders, folderTreeItem);
			}
		}
	}
	
	private void addMessageListenerToFolder(Folder folder, FolderTreeItem folderTreeItem) {
		folder.addMessageCountListener(new MessageCountListener() {

			@Override
			public void messagesAdded(MessageCountEvent e) {
				for(int i = 0; i < e.getMessages().length; i++) {
					try {
						Message message = folder.getMessage(folder.getMessageCount() - i);
						folderTreeItem.addEmailToTop(message);
					} catch (MessagingException ex) {
						ex.printStackTrace();
					}
				}
			}
			@Override
			public void messagesRemoved(MessageCountEvent e) {}	
		});
	}
	
	private void fetchMessagesOnFolder(FolderTreeItem folderTreeItem) {
		Folder folder = folderTreeItem.getFolder();
		Service<Void> fetchMessagesService = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						if(folder.getType() != Folder.HOLDS_FOLDERS) {
							folder.open(Folder.READ_WRITE);
							int folderSize = folder.getMessageCount();
							for(int i = folderSize; i > 0; i--) {
								folderTreeItem.addEmail(folder.getMessage(i));
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
