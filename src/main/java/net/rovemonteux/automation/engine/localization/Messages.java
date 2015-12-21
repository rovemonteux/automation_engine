package net.rovemonteux.automation.engine.localization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Messages {

	private String languageFile = null;
	private HashMap<String, String> messages = new HashMap<String, String>();
	
	public Messages(String language_) throws IOException {
		this.setLanguageFile("localization/"+language_+".txt");
		populate();
	}
	
	private void populate() throws IOException {
		 Properties properties = new Properties();
		 properties.load(getClass().getClassLoader().getResourceAsStream(this.getLanguageFile()));
		 for (String key : properties.stringPropertyNames()) {
			    String value = properties.getProperty(key);
			    this.getMessage().put(key, value);
		 }
	}
	
	public String get(String message, Object[] args) {
		return String.format(this.getMessage().get(message), args);
	}

	public String getLanguageFile() {
		return this.languageFile;
	}

	public void setLanguageFile(String languageFile_) {
		this.languageFile = languageFile_;
	}

	public HashMap<String, String> getMessage() {
		return messages;
	}

	public void setMessage(HashMap<String, String> messages_) {
		this.messages = messages_;
	}

}
