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

package net.rovemonteux.automation.engine.tasks;

import java.io.BufferedWriter;
import java.io.IOException;

import net.rovemonteux.automation.engine.Vocabulary;
import net.rovemonteux.automation.engine.storage.ObjectStack;

public abstract class TaskFactory {

	private ObjectStack objectStack = null;
	private Vocabulary vocabulary = null;
	
	public TaskFactory(ObjectStack objectStack_, Vocabulary vocabulary_) {
		this.setObjectStack(objectStack_);
		this.setVocabulary(vocabulary_);
	}
	
	/**
	 * Runs the Task. Run generically implies to both stack and print the Task in sequence.
	 * 
	 * @param output	Output stream
	 * @param args	Extra arguments to the Task
	 * @param description	Task description
	 * @throws IOException	Error on input and output
	 */
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		
	}
	
	/**
	 * Prints the result object of the Task and/or write the result of operations to the output stream.
	 * 
	 * @param output	Output stream
	 * @param args	Extra arguments to the Task
	 * @param description	Task description
	 * @throws IOException	Error on input and output
	 */
	public void print(BufferedWriter output, String[] args, String description) throws IOException {
		
	}
	
	/**
	 * Stacks the result object of the Task.
	 * 
	 * @param output	Output stream
	 * @param args	Extra arguments to the Task
	 * @param description	Task description
	 * @throws IOException	Error on input and output
	 */
	public void stack(BufferedWriter output, String[] args, String description) throws IOException {
		
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
	
}
