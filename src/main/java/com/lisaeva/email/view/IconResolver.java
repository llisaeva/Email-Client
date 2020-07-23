package com.lisaeva.email.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconResolver {

	public static Node getIconForFolder(String name) {
		
		String lowerCaseFolderName = name.toLowerCase();
		ImageView imageView = null;
		
		try {
			if(lowerCaseFolderName.contains("@")) {
//				imageView = new ImageView(new Image(IconResolver.class.getResourceAsStream("/img/at-dark.png")));
			} else if (lowerCaseFolderName.contains("inbox")){
				imageView = new ImageView(new Image(IconResolver.class.getResourceAsStream("/img/default/inbox.png")));
			} /*else if (lowerCaseFolderName.contains("all")){
				imageView = new ImageView(new Image(IconResolver.class.getResourceAsStream("img/emails-dark.png")));
			} else if (lowerCaseFolderName.contains("sent")){
				imageView = new ImageView(new Image(IconResolver.class.getResourceAsStream("img/sent-dark.png")));
			} else if (lowerCaseFolderName.contains("spam")) {
				imageView = new ImageView(new Image(IconResolver.class.getResourceAsStream("img/spam-dark.png")));
			} else if (lowerCaseFolderName.contains("important")) {
				imageView = new ImageView(new Image(IconResolver.class.getResourceAsStream("img/flag-dark.png")));
			} else if (lowerCaseFolderName.contains("draft")) {
				imageView = new ImageView(new Image(IconResolver.class.getResourceAsStream("img/draft-dark.png")));
			} else if (lowerCaseFolderName.contains("star")) {
				imageView = new ImageView(new Image(IconResolver.class.getResourceAsStream("img/starred-dark.png")));
			} else if (lowerCaseFolderName.contains("trash")) {
				imageView = new ImageView(new Image(IconResolver.class.getResourceAsStream("img/trash-dark.png")));
			} else {
				imageView = new ImageView(new Image(IconResolver.class.getResourceAsStream("img/email-dark.png")));
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		if (imageView != null) {
			imageView.setFitWidth(16);
			imageView.setFitHeight(16);
			return imageView;
		}
		
		return null;
		
	}
	
}
