package com.lisaeva.email;

import com.lisaeva.email.model.EmailManager;
import com.lisaeva.email.view.ViewGenerator;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
	
	private String account = "";
	private String password = "";
	public static void main(String... args) {launch(args);}
	
	@Override
	public void start(Stage primaryStage) throws Exception {			
		EmailManager em = new EmailManager();
		em.login(account, password);
		em.fetchFolders();	
		ViewGenerator.initialize(em);	
	}
}
