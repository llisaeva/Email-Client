package com.lisaeva.email.controller.service;

import com.lisaeva.email.model.Attachment;
import com.lisaeva.email.model.EmailMessage;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;

public class AttachmentLoader extends Service<Void>{

	private ListView<Attachment> attachmentList;
	
	public AttachmentLoader(ListView<Attachment> attachmentList, EmailMessage email) {
		this.attachmentList = attachmentList;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				
				return null;
			}};
	}
}
