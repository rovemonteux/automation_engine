/*
 * Monteux Automation Engine - Java SE 6+ automation framework
 * Copyright (c) 2015 Rove Monteux
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
package net.rovemonteux.automation.engine.hid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rovemonteux.automation.engine.Vocabulary;
import net.rovemonteux.automation.engine.io.FileIO;
import net.rovemonteux.automation.engine.io.LoggingOutputStream;
import net.rovemonteux.automation.engine.localization.Messages;
import net.rovemonteux.automation.engine.storage.ObjectStack;
import net.rovemonteux.automation.engine.tasks.TaskRunner;

/** 
 * Non-HID script interpreter for the Automation Engine.
 */
public class Script implements HIDFactory {

	private static final Logger logger = LogManager.getLogger("Script");
	
	private Vocabulary vocabulary = null;
	private ObjectStack objectStack = null;
	private String scriptFile = "";
	private String languageCode = "";
	private Messages messages = null;
	
	public Script(Vocabulary vocabulary_, ObjectStack objectStack_, String scriptFile_, String languageCode_, Messages messages_) {
		this.setVocabulary(vocabulary_);
		this.setObjectStack(objectStack_);
		this.setScriptFile(scriptFile_);
		this.setLanguageCode(languageCode_);
		this.setMessages(messages_);
		logger.info("Script HID started");
	}
	
	@Override
	public void setup() {
		
	}

	@Override
	public void run() throws IOException {
		if (this.getScriptFile() != null && this.getScriptFile().length() > 0) {
			try {
				String[] script = FileIO.read(this.getScriptFile()).split("\n");
				logger.info("Executing script "+new File(this.getScriptFile()).getAbsolutePath());
				for (String scriptEntry: script) {
					processTask(scriptEntry, this.getLanguageCode());
				}
				logger.info("Executed script "+new File(this.getScriptFile()).getAbsolutePath());
			}
			catch (Exception e) {
				logger.error("Error reading script file "+this.getScriptFile());
				logger.error(e);
			}
		}
	}

	@Override
	public void processTask(String task, String language) {
		List<ArrayList<String>> results = vocabulary.search(task, language);
		ArrayList<String> associatedClass = results.get(0);
		ArrayList<String> associatedMode = results.get(1);
		if (associatedClass.size() > 0) {
			for (int i=0; i<associatedMode.size(); i++) {
				TaskRunner runner = new TaskRunner(new BufferedWriter(new OutputStreamWriter(new LoggingOutputStream(logger, Level.INFO))), associatedClass.get(i+1), vocabulary.getVocabularyProperties().get(associatedClass.get(0)), task.split(" "), this.getObjectStack(), associatedMode.get(i), this.getVocabulary(), this.getLanguageCode(), this.getMessages());
				runner.process();
			}
		}
		else {
			logger.error(this.getMessages().get("syntax_error", new Object[]{task}));
		}
		associatedClass = null;
		associatedMode = null;
		results = null;
	}
	
	@Override
	public Vocabulary getVocabulary() {
		return this.vocabulary;
	}

	@Override
	public void setVocabulary(Vocabulary vocabulary_) {
		this.vocabulary = vocabulary_;
	}

	@Override
	public void setObjectStack(ObjectStack objectStack_) {
		this.objectStack = objectStack_;
	}

	@Override
	public ObjectStack getObjectStack() {
		return this.objectStack;
	}

	@Override
	public void setScriptFile(String scriptFile_) {
		this.scriptFile = scriptFile_;
	}

	@Override
	public String getScriptFile() {
		return this.scriptFile;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

}
