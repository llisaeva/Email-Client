package com.lisaeva.email.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lisaeva.email.model.EmailMessage;
import com.lisaeva.email.controller.service.MessageRendererService;
import com.lisaeva.email.model.EmailCell;
import com.lisaeva.email.model.EmailManager;
import com.lisaeva.email.model.FolderTreeItem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.web.WebView;
import javafx.util.Callback;

public class MainWindowController extends BaseController implements Initializable{
	
	 @FXML private TreeView<String> folderSelection;
	 @FXML private ListView<EmailMessage> emailSelection; 
	 @FXML private Button messageKeyTrash;
	 @FXML private Button messageKeyStar;
	 @FXML private Label messageTitle;
	 @FXML private Label messageSenderName;
	 @FXML private Label messageTime;
	 @FXML private Label messageAttachmentLabel;
	 @FXML private Label messageDate;
	 @FXML private WebView messageViewShort;
	 @FXML private WebView messageViewLong;
	 @FXML private ImageView defaultMessageViewBG;
	 @FXML private Line headerDiv;
	 @FXML private Line footerDiv;
	 
	 private MessageRendererService messageRendererService;
	 
	 public MainWindowController(EmailManager emailManager) {
		 super("/fxml/main.fxml", emailManager);
	 }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		emailSelection.setCellFactory(new Callback<ListView<EmailMessage>, ListCell<EmailMessage>>(){
			@Override
		    public ListCell<EmailMessage> call(ListView<EmailMessage> param) {
				return new EmailCell();

			}
		});
		
		messageViewShort.setVisible(false);
		footerDiv.setVisible(false);
		
		setVisibility(false);
		
		setUpFolderSelection();
		setUpMessageRendererService();
		setUpMessageSelection();
		
//		folderSelection.setCellFactory(new Callback<TreeView<Folder>, TreeItem<Folder>>() {
//
//			@Override
//			public TreeItem<Folder> call(TreeView<Folder> param) {
//				return new FolderTreeItem();
//			}
//			
//		});
		
//		for(int i = 0; i < 10; i++) {
//			emailSelection.getItems().add(new Email());
//		 }
	}
	

	private void setUpFolderSelection() {
		folderSelection.setRoot(getEmailManager().getFolderRoot());
		folderSelection.setShowRoot(false);
		folderSelection.setOnMouseClicked(e -> {
			FolderTreeItem item = (FolderTreeItem)folderSelection.getSelectionModel().getSelectedItem();
			
			if(item != null) {
				getEmailManager().setSelectedFolder(item);
				emailSelection.setItems(item.getEmailMessages());
			}
		});
	}
	
	private void setUpMessageRendererService() {
		messageRendererService = new MessageRendererService(messageViewLong.getEngine());
	}
	
	private void setUpMessageSelection() {
		
		emailSelection.setOnMouseClicked(event -> {
			setVisibility(true);
			EmailMessage emailMessage = emailSelection.getSelectionModel().getSelectedItem();
			if(emailMessage != null) {
				getEmailManager().setSelectedMessage(emailMessage);
//				if (!emailMessage.isRead()) {
//					getEmailManager().setRead();
//				}
				getEmailManager().setSelectedMessage(emailMessage);
				messageRendererService.setEmailMessage(emailMessage);
				messageRendererService.restart();
				messageSenderName.setText(emailMessage.getSender());
				messageTitle.setText(emailMessage.getTitle());
			}
		});
	}
	
	private void setVisibility(boolean b) {
		headerDiv.setVisible(b);
		messageKeyTrash.setVisible(b);
		messageKeyStar.setVisible(b);
		messageTitle.setVisible(b);
		messageSenderName.setVisible(b);
		messageTime.setVisible(b);
		messageAttachmentLabel.setVisible(b);
		messageDate.setVisible(b);
		messageViewLong.setVisible(b);
		defaultMessageViewBG.setVisible(!b);
	}
}
