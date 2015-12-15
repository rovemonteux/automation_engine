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
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rovemonteux.automation.engine.io.FileIO;
import net.rovemonteux.automation.engine.storage.ObjectStack;

/**
 * Task for listing files in a given folder.
 */
public class ListFiles implements TaskFactory {

	private static final Logger logger = LogManager.getLogger("ListFiles");
	private ObjectStack objectStack = null;
	
	public ListFiles(ObjectStack objectStack_) {
		this.setObjectStack_(objectStack_);
	}
	
	@Override
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		stack(output,args,description);
		print(output,args,description);
	}

	@Override
	public void print(BufferedWriter output, String[] args, String description) throws IOException {
		output.write(list(args));
		output.flush();
	}

	@Override
	public void stack(BufferedWriter output, String[] args, String description) throws IOException {
		this.getObjectStack_().add(list(args));
	}

	/**
	 * Returns a String formatted list of files in a given folder.
	 * 
	 * @param args	Extra arguments from the Task
	 * @return	Formatted list of files in a given folder
	 */
	public String list(String[] args) {
		StringBuilder result = new StringBuilder();
		String folder = ".";
		if (args.length > 2) {
			folder = args[2];
		}
		logger.debug("Listing folder "+folder);
		for (File file: FileIO.listFiles(folder)) {
			result.append(file.getName());
			result.append(String.format("%n"));
		}
		return result.toString();
	}
	
	@Override
	public ObjectStack getObjectStack_() {
		return this.objectStack;
	}

	@Override
	public void setObjectStack_(ObjectStack objectStack_) {
		this.objectStack = objectStack_;
	}

}
