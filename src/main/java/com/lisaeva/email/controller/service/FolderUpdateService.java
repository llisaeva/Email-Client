package com.lisaeva.email.controller.service;

import java.util.List;
import javax.mail.Folder;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FolderUpdateService extends Service<Object> {

	private List<Folder> folderList;
	private boolean initialized = true;
	
	public FolderUpdateService(List<Folder> folderList) { this.folderList = folderList; }

	@Override
	protected Task<Object> createTask() {
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {
				while (initialized) {
					try {
						Thread.sleep(3000);
						for (Folder folder: folderList) {
							if (folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen())folder.getMessageCount();							
						}
					} catch (Exception e) { e.printStackTrace(); }
				}
				return null;	
			}
		};
	}
}
