/*
 * Monteux Automation Engine - Java SE 7+ automation framework
 * Copyright (c) 2017 Rove Monteux
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package cf.monteux.automation.engine.localization;

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
