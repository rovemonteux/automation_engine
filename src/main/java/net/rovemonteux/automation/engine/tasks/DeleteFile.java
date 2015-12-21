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
 * Deletes a given file.
 */
public class DeleteFile extends TaskFactory {

	private static final Logger logger = LogManager.getLogger("DeleteFile");
	
	public DeleteFile(ObjectStack objectStack_, Vocabulary vocabulary_, String language_) {
		super(objectStack_, vocabulary_, language_);
	}

	@Override
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		deleteFile(args);
	}
	
	@Override
	public void print(BufferedWriter output, String[] args, String description) throws IOException {
		run(output, args, description);
	}
	
	@Override
	public void stack(BufferedWriter output, String[] args, String description) throws IOException {
		run(output, args, description);
	}
	
	public void deleteFile(String[] args) {
		if (args.length > 1) {
			if (FileIO.exists(args[args.length-1])) {
				FileIO.delete(args[args.length-1]);
				logger.debug("Deleted file "+args[args.length-1]+".");
			}
			else {
				specifyFile();
			}
		}
		else {
			specifyFile();
		}
	}
	
	public void specifyFile() {
		logger.error("Please specify a file to be deleted.");
	}
	
}
