package com.lisaeva.email.controller;

import com.lisaeva.email.model.EmailManager;
import com.lisaeva.email.view.ViewGenerator;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends BaseController{

	@FXML private TextField emailField;
    @FXML private PasswordField passwordField;
	private EmailManager em;

	public LoginController(EmailManager emailManager) {
		super("/fxml/login.fxml", emailManager);
		em = emailManager;
	}
	
	@FXML void loginAction() {
		em.login(emailField.getText(), passwordField.getText());
		em.fetchFolders();
    }

}
