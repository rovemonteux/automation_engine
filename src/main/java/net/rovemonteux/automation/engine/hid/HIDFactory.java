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

import java.io.IOException;

import net.rovemonteux.automation.engine.Vocabulary;
import net.rovemonteux.automation.engine.storage.ObjectStack;

/**
 * Factory for the instantiation of HIDs.
 */
public interface HIDFactory {

	/**
	 * Initializes the internal components used by the HID.
	 */
	public void setup();
	
	/**
	 * Starts and runs the HID.
	 * 
	 * @throws IOException	Error on input and output
	 */
	public void run() throws IOException;
	
	/**
	 * Runs the given task.
	 * 
	 * @param task	Task to be run
	 */
	public void processTask(String task, String language);
	
	/**
	 * Gets the parsed rule set of tasks and modes.
	 * 
	 * @return Parsed Vocabulary containing the set of tasks and modes to be used by the HID
	 */
	public Vocabulary getVocabulary();
	
	/**
	 * Sets the parsed rule set of tasks and modes.
	 * 
	 * @param vocabulary_	Parsed Vocabulary containing the set of tasks and modes to be used by the HID
	 */
	public void setVocabulary(Vocabulary vocabulary_);
	
	/**
	 * Sets the Object Stack to be used by the HID.
	 * 
	 * @param objectStack_	Object Stack to be used by the HID
	 */
	public void setObjectStack(ObjectStack objectStack_);
	
	/**
	 * Gets the Object Stack to be used by the HID.
	 * 
	 * @return	Object Stack to be used by the HID
	 */
	public ObjectStack getObjectStack();
	
	/**
	 * Sets the script file to be executed upon HID startup.
	 * 
	 * @param scriptFile_	Script file to be executed
	 */
	public void setScriptFile(String scriptFile_);
	
	/**
	 * Gets the script file to be executed upon HID startup.
	 * 
	 * @return	Script file to be executed
	 */
	public String getScriptFile();
	
}
