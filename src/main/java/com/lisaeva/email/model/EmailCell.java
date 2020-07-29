package com.lisaeva.email.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import com.lisaeva.email.controller.service.MessageRendererService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class EmailCell extends ListCell<EmailMessage>{
	
	@FXML private Label sender;
    @FXML private Label date;
    @FXML private Label title;
    @FXML private Pane selected;
    @FXML private TextArea message;
    @FXML private ImageView attachment;
    private MenuItem markUnread = new MenuItem("mark as unread");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d");

    public EmailCell() { loadFXML(); }

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
        } else {  	
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            MessageRendererService mrs = new MessageRendererService(item);
            mrs.setOnSucceeded(e -> {
            	sender.setText(item.getSender().replaceAll("[<].*[>]", ""));
    		    title.setText(item.getTitle());
    		    date.setText(dateFormat.format(item.getDate()));   
    		    makeBoldRows(!item.isRead());
    		    
    		    if(item.getDemoMessage() != null)message.setText(item.getDemoMessage());
            });
            mrs.start();
            
            this.focusedProperty().addListener(new ChangeListener<Boolean>() {	
    			@Override
    			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    				setSelectedIcon(false);
    		}});
        	
        	this.setOnMouseClicked(e -> {
        		this.setSelectedIcon(true);
        		item.setRead(true);
        		makeBoldRows(false);	
    	    }); 
        	
        	this.setContextMenu(new ContextMenu(markUnread));
        	markUnread.setOnAction(e -> {
    			item.setRead(false);
    			makeBoldRows(true);
    		});	
        }						
    }
	
	private void makeBoldRows(boolean b) {
		String style = "";
		if(b)style = "-fx-font-weight:bold;\n-fx-text-fill:black;";	
		sender.setStyle(style);
	    date.setStyle(style);
	    message.setStyle(style);
	}
	
	public void setSelectedIcon(boolean b) { this.selected.setVisible(b); }
}
