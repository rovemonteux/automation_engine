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

package cf.monteux.automation.engine.hid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cf.monteux.automation.engine.Vocabulary;
import cf.monteux.automation.engine.exception.StackTrace;
import cf.monteux.automation.engine.io.FileIO;
import cf.monteux.automation.engine.localization.Messages;
import cf.monteux.automation.engine.storage.ObjectStack;
import cf.monteux.automation.engine.tasks.TaskRunner;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;

/**
 * Console as Human Interaction Device for the Automation Engine.
 */
public class Console implements HIDFactory {

	private static final Logger logger = LogManager.getLogger("Console");
	
	private java.io.Console console = null;
	private Vocabulary vocabulary = null;
	private ObjectStack objectStack = null;
	private String scriptFile = "";
	private String languageCode = "";
	private Messages messages = null;
	
	public Console(Vocabulary vocabulary_, ObjectStack objectStack_, String scriptFile_, String languageCode_, Messages messages_) {
		this.setVocabulary(vocabulary_);
		this.setObjectStack(objectStack_);
		this.setScriptFile(scriptFile_);
		this.setLanguageCode(languageCode_);
		this.setMessages(messages_);
		logger.info(this.getMessages().get("console_started", new Object[]{}));
	}
	
	@Override
	public void setup() {
		logger.info(this.getMessages().get("setting_up_console", new Object[]{}));
		this.setConsole(System.console());
		logger.info(this.getMessages().get("console_setup", new Object[]{}));
	}

	@Override
	public void run() throws IOException {
            this.setup();
            logger.info("Console: "+this.getConsole());
		if (this.getScriptFile() != null && this.getScriptFile().length() > 0) {
			try {
				String[] script = FileIO.read(this.getScriptFile()).split("\n");
				logger.info(this.getMessages().get("executing_script", new Object[]{new File(this.getScriptFile()).getAbsolutePath()}));
				for (String scriptEntry: script) {
					processTask(scriptEntry, this.getLanguageCode());
				}
				logger.info(this.getMessages().get("executed_script", new Object[]{new File(this.getScriptFile()).getAbsolutePath()}));
			}
			catch (Exception e) {
				logger.error(this.getMessages().get("script_error", new Object[]{this.getScriptFile()}));
				logger.error(StackTrace.asString(e));
			}
		}
		String result = "";
        ConsoleReader reader = new ConsoleReader();
        reader.setPrompt(System.getProperty("user.name")+"@mae> ");
        Collection<String> commands = new LinkedList<String>();
        commands.add("clear");
        commands.add("help");
        Set<String> keySet = this.getVocabulary().getVocabularyProperties().keySet();
		for (Object key: keySet.toArray()) {
			String value = this.getVocabulary().getVocabularyProperties().get(key.toString());
			if (value.split("\\|")[0].equals(this.getLanguageCode())) {
				commands.add(key.toString());
			}
		}
        List<Completer> completors = new LinkedList<Completer>();
        StringsCompleter stringsCompleter = new StringsCompleter(commands);
        completors.add(stringsCompleter);
        for (Completer c : completors) {
            reader.addCompleter(c);
        }
        this.getConsole().writer().write(this.getMessages().get("console_welcome", new Object[]{}));
        this.getConsole().flush();
        while (!(result.equals("exit"))) {
			result = reader.readLine(reader.getPrompt()).toLowerCase().trim();
			 if (result.equals("clear")) {
                 reader.clearScreen();
             }
			 else {
				 processTask(result, this.getLanguageCode());
			 }
		}
	}
	
	@Override
	public void processTask(String task, String language) {
		List<ArrayList<String>> results = this.getVocabulary().search(task, language);
		ArrayList<String> associatedClass = results.get(0);
		ArrayList<String> associatedMode = results.get(1);
		if (associatedClass.size() > 0) {
			for (int i=0; i<associatedMode.size(); i++) {
				TaskRunner runner = new TaskRunner(new BufferedWriter(this.getConsole().writer()), associatedClass.get(i+1), vocabulary.getVocabularyProperties().get(associatedClass.get(0)), task.split(" "), this.getObjectStack(), associatedMode.get(i), this.getVocabulary(), this.getLanguageCode(), this.getMessages());
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
	
	public java.io.Console getConsole() {
		return this.console;
	}

	public void setConsole(java.io.Console console) {
		this.console = console;
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
	public ObjectStack getObjectStack() {
		return this.objectStack;
	}

	@Override
	public void setObjectStack(ObjectStack objectStack_) {
		this.objectStack = objectStack_;
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
