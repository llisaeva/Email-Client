package com.lisaeva.email.controller;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.lisaeva.email.controller.service.EmailSendingService;
import com.lisaeva.email.controller.service.MessageRendererService;
import com.lisaeva.email.model.Attachment;
import com.lisaeva.email.model.AttachmentCell;
import com.lisaeva.email.model.EmailCell;
import com.lisaeva.email.model.EmailManager;
import com.lisaeva.email.model.EmailMessage;
import com.lisaeva.email.model.FolderTreeItem;
import com.lisaeva.email.view.IconResolver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class MainWindowController extends BaseController implements Initializable {
	
	 @FXML private TreeView<String> folderSelection;
	 @FXML private ListView<EmailMessage> emailSelection;
	 @FXML private ListView<Attachment> attachmentList;
	 @FXML private AnchorPane messagePane;
	 @FXML private Label messageTitle;
	 @FXML private Label messageSenderName;
	 @FXML private Label messageAttachmentLabel;
	 @FXML private Label messageDate;
	 @FXML private WebView messageViewShort;
	 @FXML private WebView messageViewLong;
	 @FXML private ImageView defaultMessageViewBG;
	 @FXML private Label userNameLabel; 
	 @FXML private AnchorPane composePane;
	 @FXML private TextField composeTo;
	 @FXML private TextField composeTitle;
	 @FXML private HTMLEditor htmlEditor;
	 @FXML private Line footerDiv;
	 @FXML private ImageView composeAttachIcon;
	 private static SimpleDateFormat dateFormat = new SimpleDateFormat("M'/'d'/'YYYY   H:mm");
	 private ArrayList<File> attachments = new ArrayList<File>();
	 private MessageRendererService mrs;
	 private EmailManager emailManager;
	 private StringBuffer stringBuffer;
	 private String htmlBackup;
	 
	 public MainWindowController(EmailManager emailManager) {
		 super("/fxml/main.fxml", emailManager);
		 this.emailManager = emailManager;
		 this.stringBuffer = new StringBuffer();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		emailSelection.setCellFactory(new Callback<ListView<EmailMessage>, ListCell<EmailMessage>>(){
			@Override
		    public ListCell<EmailMessage> call(ListView<EmailMessage> param) { return new EmailCell(); }
		});
		
		attachmentList.setCellFactory(new Callback<ListView<Attachment>, ListCell<Attachment>>(){
			@Override
			public ListCell<Attachment> call(ListView<Attachment> param) { return new AttachmentCell(); }	
		});

		htmlBackup = htmlEditor.getHtmlText();
		messageAttachmentLabel.setGraphic(IconResolver.getIcon("paper-clip"));
//		userNameLabel.setText(emailManager.getEmailAccount().getAddress());
		userNameLabel.setText("email.account@gmail.com");
		setUpMRS();
		setUpFolderSelection();
		setUpMessageSelection();
		setUpAttachmentSelection();
	}
	
	private void setUpMRS() { mrs = new MessageRendererService(stringBuffer); }
	
	private void setUpFolderSelection() {
		folderSelection.setRoot(emailManager.getFolderRoot());
		folderSelection.setShowRoot(false);
		folderSelection.setOnMouseClicked(e -> {
			FolderTreeItem item = (FolderTreeItem)folderSelection.getSelectionModel().getSelectedItem();	
			if(item != null) {
				emailManager.setSelectedFolder(item);
				emailSelection.setItems(item.getEmailMessages());	
			}
		});
	}
	
	private void setUpMessageSelection() { 
		emailSelection.setOnMouseClicked(event -> {	
			attachmentList.getItems().clear();
			EmailMessage emailMessage = emailSelection.getSelectionModel().getSelectedItem();
			
			if(emailMessage != null) {
				emailManager.setSelectedMessage(emailMessage);
				if (!composePane.isVisible())setMessageViewVisible(true);						
				mrs.setEmailMessage(emailMessage);
				mrs.setOnSucceeded(e -> {
					if(emailMessage.hasAttachment()) {
						messageViewShort.getEngine().loadContent(stringBuffer.toString());
						setAttachmentView(true);
						emailMessage.setAttachmentLoaded();
						loadAttachments();
					} else {
						messageViewLong.getEngine().loadContent(stringBuffer.toString());
						setAttachmentView(false);
					} 
				});
				mrs.restart();
				messageSenderName.setText(emailMessage.getSender().replaceAll("lis.email.ttest@gmail.com", "email.account@gmail.com"));
				messageTitle.setText(emailMessage.getTitle());
				messageDate.setText(dateFormat.format(emailMessage.getDate()));			
			}
		});
	}
	
	private void setUpAttachmentSelection() {
		attachmentList.setOnMouseClicked(e -> {
			Attachment selected = attachmentList.getSelectionModel().getSelectedItem();
			attachmentList.getSelectionModel().clearSelection();
			if (selected != null) {
				File attachment = new File(selected.getDownloadPath());
				if (!attachment.exists())selected.downloadAttachment();	
				else {
					Desktop desktop = Desktop.getDesktop();
					try { desktop.open(attachment); } 
					catch (Exception exp) { exp.printStackTrace(); }
				}
			}
		});
	}

	@FXML 
	void composeCancel() {
		setComposeViewVisible(false);
		if (!messageSenderName.getText().isBlank())setMessageViewVisible(true);
    }

    @FXML 
    void composeKeyPressed() {
    	setMessageViewVisible(false);
    	setComposeViewVisible(true);
    }
    
    @FXML 
    void composeSendPressed() {
    	EmailSendingService emailSenderService = new EmailSendingService(
				emailManager.getEmailAccount(),
				composeTitle.getText(),
				composeTo.getText(),
				htmlEditor.getHtmlText(),
				attachments);
    	emailSenderService.setOnSucceeded(e -> {
    	});
    	emailSenderService.start();
    	setComposeViewVisible(false);
    	composeAttachIcon.setVisible(false);
		composeTitle.clear();
		composeTo.clear();
		htmlEditor.setHtmlText(htmlBackup);
    	if (!messageSenderName.getText().isBlank())setMessageViewVisible(true);
    }
    
    @FXML
    void addAttachment() {
    	FileChooser fileChooser = new FileChooser();
    	File selectedFile = fileChooser.showOpenDialog(null);
    	if(selectedFile != null){
    		attachments.add(selectedFile);
    		composeAttachIcon.setVisible(true);
    	}
    }
    
    private void loadAttachments() {
		EmailMessage emailMessage = emailManager.getSelectedMessage();
		if (emailMessage != null && emailMessage.hasAttachment()) {
			ObservableList<Attachment> attachments = FXCollections.observableArrayList(emailMessage.getAttachments());
			attachmentList.getItems().addAll(attachments);
			attachmentList.setVisible(true);
		} else { attachmentList.setVisible(false); }
	}
	
	private void setAttachmentView(boolean b) {
		messageViewLong.setVisible(!b);
		messageViewShort.setVisible(b);
		footerDiv.setVisible(b);
		messageAttachmentLabel.setVisible(b);
		attachmentList.setVisible(b);
	}
	
	private void setMessageViewVisible(boolean b) {
		messagePane.setVisible(b);
		defaultMessageViewBG.setVisible(!b);
	}
	
	private void setComposeViewVisible(boolean b) {
		composePane.setVisible(b);
		defaultMessageViewBG.setVisible(!b);
	}
	
    @FXML
    void starKeyAction() {}

    @FXML
    void trashKeyAction() {}
}
