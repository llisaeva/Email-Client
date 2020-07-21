package com.lisaeva.email.model;

import java.io.IOException;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class EmailCell extends ListCell<EmailMessage>{
	
	@FXML
    private Label sender;

    @FXML
    private Label date;

    @FXML
    private Label title;

    @FXML
    private ImageView selected;

    @FXML
    private TextArea message;

    @FXML
    private ImageView attachment;
    
    public EmailCell() {
    	loadFXML();
    	
    	
//  	EmailMessage temp = this.getListView().getItems().get(this.getListView().getItems().indexOf(this));
//   	this.sender.setText(temp.getSender());
//    	this.title.setText(temp.getTitle());
//   	this.message.setText(message);
    }

	private void loadFXML() {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/email_cell.fxml"));
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
    protected void updateItem(EmailMessage item, boolean empty) {
        super.updateItem(item, empty);
        if(empty) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {  	
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            EmailMessage email = this.getItem();
        	sender.setText(email.getSender());
        	title.setText(email.getTitle());
        }

 
    }
}
