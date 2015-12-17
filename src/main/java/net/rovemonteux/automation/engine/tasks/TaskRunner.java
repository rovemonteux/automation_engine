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
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rovemonteux.automation.engine.Vocabulary;
import net.rovemonteux.automation.engine.exception.StackTrace;
import net.rovemonteux.automation.engine.storage.ObjectStack;

public class TaskRunner extends Thread {

	private static final Logger logger = LogManager.getLogger("TaskRunner");
	
	private String taskClassName = "";
	private String mode = "";
	private String[] arguments = null;
	private ArrayList<String> taskProperties = new ArrayList<String>();
	private BufferedWriter output = null;
	private ObjectStack objectStack = null;
	private Vocabulary vocabulary = null;
	private String language = null;
	
	public TaskRunner(BufferedWriter output_, String taskClassName_, String taskProperties_, String[] arguments_, ObjectStack objectStack_, String mode_, Vocabulary vocabulary_, String language_) {
		this.setTaskClassName(taskClassName_);
		for (String taskProperty: taskProperties_.split("\\|")) {
			this.getTaskProperties().add(taskProperty);
		}
		this.setArguments(arguments_);
		this.setOutput(output_);
		this.setObjectStack(objectStack_);
		this.setMode(mode_);
		this.setVocabulary(vocabulary_);
		this.setLanguage(language_);
	}
	
	public void process() {
		if (this.getTaskProperties().get(1).trim().equals("true")) {
			run();
		}
		else {
			runTask();
		}
	}
	
	public void runTask() {
		try {
			Class<?> taskClass = Class.forName(this.getTaskProperties().get(3)+"."+taskClassName);
			Constructor<?> constructor = taskClass.getConstructor(ObjectStack.class, Vocabulary.class, String.class);
			TaskFactory task = (TaskFactory) constructor.newInstance(this.getObjectStack(), this.getVocabulary(), this.getLanguage());
			if (this.getMode().equals("") || this.getMode().toLowerCase().equals("run")) {
				task.run(output, this.arguments, this.getTaskProperties().get(2));
			}
			else if (this.getMode().equals("stack")) {
				task.stack(output, this.arguments, this.getTaskProperties().get(2));
			}
			else if (this.getMode().equals("print")) {
				task.print(output, this.arguments, this.getTaskProperties().get(2));
			}
		}
		catch (Exception e) {
			logger.error(StackTrace.asString(e));
		}
	}
	
	@Override
	public void run() {
		runTask();
	}
	
	public String getTaskClassName() {
		return this.taskClassName;
	}
	
	public void setTaskClassName(String taskClassName) {
		this.taskClassName = taskClassName;
	}

	public ArrayList<String> getTaskProperties() {
		return this.taskProperties;
	}

	public void setTaskProperties(ArrayList<String> taskProperties) {
		this.taskProperties = taskProperties;
	}

	public BufferedWriter getOutput() {
		return this.output;
	}

	public void setOutput(BufferedWriter output) {
		this.output = output;
	}

	public ObjectStack getObjectStack() {
		return this.objectStack;
	}

	public void setObjectStack(ObjectStack objectStack_) {
		this.objectStack = objectStack_;
	}

	public String[] getArguments() {
		return this.arguments;
	}

	public void setArguments(String[] arguments) {
		this.arguments = arguments;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(Vocabulary vocabulary_) {
		this.vocabulary = vocabulary_;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
}
