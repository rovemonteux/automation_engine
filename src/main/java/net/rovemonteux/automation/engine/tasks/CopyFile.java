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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rovemonteux.automation.engine.Vocabulary;
import net.rovemonteux.automation.engine.io.FileIO;
import net.rovemonteux.automation.engine.storage.ObjectStack;

/**
 * Copies a given file to another given file.
 */
public class CopyFile extends TaskFactory {

	private static final Logger logger = LogManager.getLogger("CopyFile");
	
	public CopyFile(ObjectStack objectStack_, Vocabulary vocabulary_, String language_) {
		super(objectStack_, vocabulary_, language_);
	}
	
	@Override
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		copyFile(args);
	}
	
	@Override
	public void print(BufferedWriter output, String[] args, String description) throws IOException {
		if (copyFile(args)) {
			output.write("File "+args[args.length-2]+" has been successfully copied to "+args[args.length-1]+".");
		}
		else {
			output.write("Failed copying file "+args[args.length-2]+" to "+args[args.length-1]+".");
		}
		output.write(String.format("%n"));
		output.flush();
	}
	
	@Override
	public void stack(BufferedWriter output, String[] args, String description) throws IOException {
		this.getObjectStack().add(copyFile(args));
	}
	
	public boolean copyFile(String[] args) {
		if (args.length > 2) {
			if (FileIO.exists(args[args.length-2])) {
				boolean result = FileIO.copy(args[args.length-2], args[args.length-1]);
				if (result) {
					logger.debug("Copied "+args[args.length-2]+" to "+args[args.length-1]);
				}
				else {
					logger.error("Failed copying "+args[args.length-2]+" to "+args[args.length-1]);
				}
				return result;
			}
			else {
				specifyFile();
				return false;
			}
		}
		else {
			specifyFile();
			return false;
		}
	}
	
	public void specifyFile() {
		logger.error("Please specify a file to be copied, and a destination file.");
	}
}
