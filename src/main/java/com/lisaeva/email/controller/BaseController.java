package com.lisaeva.email.controller;

import com.lisaeva.email.model.EmailManager;

public class BaseController {
	
	private String fxml;
	private EmailManager emailManager;

	public BaseController(String fxml, EmailManager emailManager) {
		this.fxml = fxml;
		this.emailManager = emailManager;
	}
	
	public String getFXML() { return fxml; }
	public EmailManager getEmailManager() { return emailManager; }
}
