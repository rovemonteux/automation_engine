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
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rovemonteux.automation.engine.Vocabulary;
import net.rovemonteux.automation.engine.exception.StackTrace;
import net.rovemonteux.automation.engine.io.FileIO;
import net.rovemonteux.automation.engine.storage.ObjectStack;
import net.rovemonteux.automation.engine.tasks.TaskRunner;

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
	
	public Console(Vocabulary vocabulary_, ObjectStack objectStack_, String scriptFile_, String languageCode_) {
		this.setVocabulary(vocabulary_);
		this.setObjectStack(objectStack_);
		this.setScriptFile(scriptFile_);
		this.setLanguageCode(languageCode_);
		logger.info("Console HID started");
	}
	
	@Override
	public void setup() {
		logger.info("Setting up the Console");
		this.setConsole(System.console());
		logger.info("Set the Console as the system's console, starting up");
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
				logger.error("Error interpreting script file "+this.getScriptFile());
				logger.error(StackTrace.asString(e));
			}
		}
		String result = "";
        ConsoleReader reader = new ConsoleReader();
        reader.setPrompt(System.getProperty("user.name")+"@mae> ");
        Collection<String> commands = new LinkedList<String>();
        commands.add("clear");
        commands.add("exit");
        commands.add("help");
        Set<String> keySet = this.getVocabulary().getVocabularyProperties().keySet();
		for (Object key: keySet.toArray()) {
			commands.add(key.toString());
		}
        List<Completer> completors = new LinkedList<Completer>();
        StringsCompleter stringsCompleter = new StringsCompleter(commands);
        completors.add(stringsCompleter);
        for (Completer c : completors) {
            reader.addCompleter(c);
        }
        this.getConsole().writer().write("Console auto completion is enabled, type the first letter of the command, and then the <tab> key. A <tab> on its own lists all available commands. Command history is available by pressing the up and down keys."+String.format("%n"));
        this.getConsole().flush();
        while (!(result.equals("exit"))) {
			result = reader.readLine(System.getProperty("user.name")+"@mae> ").toLowerCase().trim();
			 if (result.equals("clear")) {
                 reader.clearScreen();
             }
			 else {
				 processTask(result, this.getLanguageCode());
			 }
		}
	}
	
	public void list() {
		this.getConsole().writer().write("Available commands: "+String.format("%n"));
		this.getConsole().writer().write("clear - Clears the screen"+String.format("%n"));
		this.getConsole().writer().write("exit - Exits the automation engine"+String.format("%n"));
		this.getConsole().writer().write(this.getVocabulary().listAvailableTasks(this.getLanguageCode()));
		this.getConsole().flush();
	}
	
	@Override
	public void processTask(String task, String language) {
		if (!(task.equals("exit"))) {
			if (task.equals("help")) {
				list();
			} else {
		List<ArrayList<String>> results = this.getVocabulary().search(task, language);
		ArrayList<String> associatedClass = results.get(0);
		ArrayList<String> associatedMode = results.get(1);
		if (associatedClass.size() > 0) {
			for (int i=0; i<associatedMode.size(); i++) {
				TaskRunner runner = new TaskRunner(new BufferedWriter(this.getConsole().writer()), associatedClass.get(i+1), vocabulary.getVocabularyProperties().get(associatedClass.get(0)), task.split(" "), this.getObjectStack(), associatedMode.get(i), this.getVocabulary(), this.getLanguageCode());
				runner.process();
			}
		}
		else {
			this.getConsole().writer().write("Syntax error: unknown command '"+task+"'\n");
			list();
		}
		associatedClass = null;
		associatedMode = null;
		results = null;
		}
		}
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
	
}
