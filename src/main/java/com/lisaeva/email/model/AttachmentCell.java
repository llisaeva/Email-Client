package com.lisaeva.email.model;

import java.io.IOException;

import com.lisaeva.email.controller.service.MessageRendererService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

public class AttachmentCell extends ListCell<Attachment>{

	  @FXML private Label attachmentName;
	  @FXML private ImageView attachmentImg;
	  
	  public AttachmentCell() {
		  loadFXML();
	  }
	  
	  private void loadFXML() {
			try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/attachment_cell.fxml"));
	            loader.setController(this);
	            loader.setRoot(this);
	            loader.load();
	        }
	        catch (IOException e) {
	        	e.getMessage();
	            e.printStackTrace();
	        }		
		}
	  
	  @Override
	    protected void updateItem(Attachment item, boolean empty) {
	        super.updateItem(item, empty);
	        if(empty) {
	            setText(null);
	            setContentDisplay(ContentDisplay.TEXT_ONLY);
	        }
	        else {  	
	            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//	            attachmentName.setText("AAAAA.jpg");

	        }
	    }
	
}
