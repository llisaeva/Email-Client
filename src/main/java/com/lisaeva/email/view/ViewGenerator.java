package com.lisaeva.email.view;

import java.io.IOException;
import java.io.InputStream;
import com.lisaeva.email.controller.BaseController;
import com.lisaeva.email.controller.MainWindowController;
import com.lisaeva.email.model.EmailManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewGenerator {
	
	private static String theme = "/css/themeDefault.css";
	private static String fontFile1 = "/font/Myriad_Pro_Regular.ttf";
	private static String fontFile2 = "/font/Myriad_Pro_Light_SemiCondensed.otf";
	
	public static void initialize(EmailManager em) {	
		loadFont();
		BaseController controller = new MainWindowController(em);
		Stage stage = new Stage();
		stage.setScene(initializeScene(controller));
		stage.show();
	}
	
	private static Scene initializeScene(BaseController controller) {
		FXMLLoader fxmlLoader = new FXMLLoader(ViewGenerator.class.getResource(controller.getFXML()));
		fxmlLoader.setController(controller);
		Parent parent;
		try { parent = fxmlLoader.load(); } 
		catch (IOException e) { e.printStackTrace(); return null; }
		Scene scene = new Scene(parent);
		updateStyles(scene);
		return scene;	
	}
	
	private static void updateStyles(Scene scene) {
		scene.getStylesheets().clear();
		scene.getStylesheets().add(ViewGenerator.class.getResource(theme).toExternalForm());
	}
	
	private static void loadFont() {
		try (InputStream in = ViewGenerator.class.getResourceAsStream(fontFile1);
			 InputStream in2 = ViewGenerator.class.getResourceAsStream(fontFile2)) {
			if (in != null) {
				Font.loadFont(in, 1);
				Font.loadFont(in2, 1);
			}
		} catch (IOException e) { e.printStackTrace(); }
	}
}
