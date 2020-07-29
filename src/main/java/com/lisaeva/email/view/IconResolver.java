package com.lisaeva.email.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconResolver {

	public static Node getIconForFolder(String name) {
		
		String lowerCaseFolderName = name.toLowerCase();
		
		ImageView imageView = new ImageView();
		imageView.setFitWidth(16);
		imageView.setFitHeight(16);
		
		try {
			if(lowerCaseFolderName.contains("gmail"))imageView.setId("gmail-folder");			
			else if (lowerCaseFolderName.contains("inbox"))imageView.setId("inbox-folder");
			else if (lowerCaseFolderName.contains("all"))imageView.setId("all-folder");
			else if (lowerCaseFolderName.contains("sent"))imageView.setId("sent-folder");
			else if (lowerCaseFolderName.contains("spam"))imageView.setId("spam-folder");
			else if (lowerCaseFolderName.contains("important"))imageView.setId("important-folder");
			else if (lowerCaseFolderName.contains("draft"))imageView.setId("draft-folder");
			else if (lowerCaseFolderName.contains("star"))imageView.setId("star-folder");
			else if (lowerCaseFolderName.contains("trash"))imageView.setId("trash-folder");
			else imageView.setId("mail-folder");
			
		} catch (Exception e) { e.printStackTrace(); return null; }		

		return imageView;
	}	
	
	public static Node getIcon(String name) {
		ImageView imageView = new ImageView();
		if(name.contains("paper-clip")) {
//			imageView = new ImageView(new Image(IconResolver.class.getResourceAsStream("/img/default/paper-clip.png")));
			imageView.setFitWidth(23);
			imageView.setFitHeight(23);
		}
		
		return imageView;
	}
}
