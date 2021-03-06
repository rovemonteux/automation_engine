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

package cf.monteux.automation.engine.tasks;

import java.io.BufferedWriter;
import java.io.IOException;

import cf.monteux.automation.engine.Vocabulary;
import cf.monteux.automation.engine.localization.Messages;
import cf.monteux.automation.engine.storage.ObjectStack;
import java.net.URISyntaxException;

public abstract class TaskFactory {

	private ObjectStack objectStack = null;
	private Vocabulary vocabulary = null;
	private String language = null;
	private Messages messages = null;
	
	public TaskFactory(ObjectStack objectStack_, Vocabulary vocabulary_, String language_, Messages messages_) {
		this.setObjectStack(objectStack_);
		this.setVocabulary(vocabulary_);
		this.setLanguage(language_);
		this.setMessages(messages_);
	}
	
	/**
	 * Runs the Task. Run generically implies to both stack and print the Task in sequence.
	 * 
	 * @param output	Output stream
	 * @param args	Extra arguments to the Task
	 * @param description	Task description
	 * @throws IOException	Exception indicating an error on input and output
         * @throws URISyntaxException   Exception indicating an error in an URI
	 */
	public void run(BufferedWriter output, String[] args, String description) throws IOException, URISyntaxException {
		
	}
	
	/**
	 * Prints the result object of the Task and/or write the result of operations to the output stream.
	 * 
	 * @param output	Output stream
	 * @param args	Extra arguments to the Task
	 * @param description	Task description
	 * @throws IOException	Exception indicating an error on input and output
         * @throws URISyntaxException   Exception indicating an error in an URI
	 */
	public void print(BufferedWriter output, String[] args, String description) throws IOException, URISyntaxException {
		
	}
	
	/**
	 * Stacks the result object of the Task.
	 * 
	 * @param output	Output stream
	 * @param args	Extra arguments to the Task
	 * @param description	Task description
	 * @throws IOException	Exception indicating an error on input and output
         * @throws URISyntaxException   Exception indicating an error in an URI
	 */
	public void stack(BufferedWriter output, String[] args, String description) throws IOException, URISyntaxException {
		
	}
	
	/**
	 * Returns the current Automation Engine Object Stack.
	 * 
	 * @return	Object Stack
	 */
	public ObjectStack getObjectStack() {
		return this.objectStack;
	}
	
	/**
	 * Sets the current Automation Engine Object Stack.
	 * 
	 * @param objectStack_	Object Stack to be set
	 */
	public void setObjectStack(ObjectStack objectStack_) {
		this.objectStack = objectStack_;
	}
	
	/**
	 * Returns the current Vocabulary.
	 * 
	 * @return	Vocabulary
	 */
	public Vocabulary getVocabulary() {
		return this.vocabulary;
	}
	
	/**
	 * Sets the current Vocabulary.
	 * 
	 * @param vocabulary_	Vocabulary to be set
	 */
	public void setVocabulary(Vocabulary vocabulary_) {
		this.vocabulary = vocabulary_;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	
}
