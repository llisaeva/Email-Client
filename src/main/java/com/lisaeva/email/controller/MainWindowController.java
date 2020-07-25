package com.lisaeva.email.controller;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import com.lisaeva.email.model.EmailMessage;
import com.lisaeva.email.controller.service.EmailSendingService;
import com.lisaeva.email.controller.service.FolderUpdateService;
import com.lisaeva.email.controller.service.MessageRendererService;
import com.lisaeva.email.model.EmailCell;
import com.lisaeva.email.model.EmailManager;
import com.lisaeva.email.model.FolderTreeItem;
import com.lisaeva.email.model.Attachment;
import com.lisaeva.email.model.AttachmentCell;
import com.lisaeva.email.model.EmailAccount;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.util.Callback;

public class MainWindowController extends BaseController implements Initializable{
	
	 @FXML private TreeView<String> folderSelection;
	 @FXML private ListView<EmailMessage> emailSelection;
	 @FXML private ListView<Attachment> attachmentList;
	 @FXML private AnchorPane messagePane;
	 @FXML private Button messageKeyTrash;
	 @FXML private Button messageKeyStar;
	 @FXML private Label messageTitle;
	 @FXML private Label messageSenderName;
	 @FXML private Label messageAttachmentLabel;
	 @FXML private Label messageDate;
	 @FXML private WebView messageViewShort;
	 @FXML private WebView messageViewLong;
	 @FXML private ImageView defaultMessageViewBG;
	 @FXML private Button composeKey;
	 @FXML private Line headerDiv;
	 @FXML private Line footerDiv;
	 @FXML private Label userNameLabel;
	 @FXML private Label userEmailLabel;
	 
	 @FXML private AnchorPane composePane;
	 @FXML private TextField composeTo;
	 @FXML private TextField composeTitle;
	 @FXML private HTMLEditor htmlEditor;
	 
	 private static SimpleDateFormat dateFormat = new SimpleDateFormat("M'/'d'/'YYYY   H:mm");
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
		
		attachmentList.setCellFactory(new Callback<ListView<Attachment>, ListCell<Attachment>>(){
			@Override
			public ListCell<Attachment> call(ListView<Attachment> param) {				
				return new AttachmentCell();
			}
			
		});
		
		ObservableList<Attachment> ats = FXCollections.observableArrayList();
		for (int i = 0; i < 9; i++)
			ats.add(new Attachment());
		attachmentList.getItems().addAll(ats);
		
		messageViewShort.setVisible(false);
		footerDiv.setVisible(false);
		
		
		
		setUpFolderSelection();
		setUpMessageRendererService();
		setUpMessageSelection();
	}
	
	@FXML
    void composeCancel() {
		setComposeViewVisible(false);
		if (!messageSenderName.getText().isBlank())
			setMessageViewVisible(true);
    	
    }

    @FXML
    void composeKeyPressed() {
    	setMessageViewVisible(false);
    	setComposeViewVisible(true);
    }
    
    @FXML
    void composeSendPressed() {
    	EmailSendingService emailSenderService = new EmailSendingService(
				getEmailManager().getEmailAccount(),
				composeTitle.getText(),
				composeTo.getText(),
				htmlEditor.getHtmlText(),
				new ArrayList<File>());
    	emailSenderService.start();
    	
    	setComposeViewVisible(false);
    	if (!messageSenderName.getText().isBlank())
    		setMessageViewVisible(true);
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
			if (!composePane.isVisible())
				setMessageViewVisible(true);
			EmailMessage emailMessage = emailSelection.getSelectionModel().getSelectedItem();
			
			if(emailMessage != null) {
				getEmailManager().setSelectedMessage(emailMessage);
//				if (!emailMessage.isRead()) {
//					getEmailManager().setRead();
//				}
				messageRendererService.setEmailMessage(emailMessage);
				messageRendererService.restart();
				messageSenderName.setText(emailMessage.getSender());
				messageTitle.setText(emailMessage.getTitle());
				messageDate.setText(dateFormat.format(emailMessage.getDate()));
				
			}
		});
		
	}
	
	private void setMessageViewVisible(boolean b) {
		messagePane.setVisible(b);
		defaultMessageViewBG.setVisible(!b);
	}
	
	private void setComposeViewVisible(boolean b) {
		composePane.setVisible(b);
		defaultMessageViewBG.setVisible(!b);
	}
}
