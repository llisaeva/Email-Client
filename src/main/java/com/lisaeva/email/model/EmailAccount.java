package com.lisaeva.email.model;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;

public class EmailAccount {
	
	private String address;
	private String password;
	private Properties properties;
	private Store store;
	private Session session;
	private ArrayList<Folder> folders = new ArrayList<Folder>();
	
	public EmailAccount(String address, String password) {
		this.address = address;
		this.password = password;
		
		properties = new Properties();
		properties.put("incomingHost", "imap.gmail.com");
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.transport.protocol", "smtps");
		properties.put("mail.smtps.host", "smtp.gmail.com");
		properties.put("mail.smtps.auth", "true");
		properties.put("outgoingHost", "smtp.gmail.com");
	}

	public String getAddress() {return address;}
	public String getPassword() {return password;}
	public Properties getProperties() {return properties;}
	public Store getStore() {return store;}
	public Session getSession() {return session;}
	public ArrayList<Folder> getFolders() {return folders;}
	
	public void setProperties(Properties properties) {this.properties = properties;}
	public void setStore(Store store) {this.store = store;}
	public void setSession(Session session) {this.session = session;}

	@Override public String toString() {return address;}
}
