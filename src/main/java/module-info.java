module com.lisaeva.email {
	exports com.lisaeva.email;
	
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.base;
	requires java.mail;
	requires javafx.web;
	requires activation;
	
	opens com.lisaeva.email.controller;
	opens com.lisaeva.email.controller.service;
	opens com.lisaeva.email to javafx.fxml, javafx.graphics;
	opens com.lisaeva.email.model to javafx.fxml;

}