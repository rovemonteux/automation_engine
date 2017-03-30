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
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cf.monteux.automation.engine.Vocabulary;
import cf.monteux.automation.engine.io.FileIO;
import cf.monteux.automation.engine.localization.Messages;
import cf.monteux.automation.engine.storage.ObjectStack;

/**
 * Displays a given file.
 */
public class ViewFile extends TaskFactory {

	private static final Logger logger = LogManager.getLogger("ViewFile");
	
	public ViewFile(ObjectStack objectStack_, Vocabulary vocabulary_, String language_, Messages messages_) {
		super(objectStack_, vocabulary_, language_, messages_);
	}
	
	@Override
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		print(output, args, description);
		stack(output, args, description);
	}
	
	@Override
	public void print(BufferedWriter output, String[] args, String description) throws IOException {
		output.write(viewFile(args));
		output.write(String.format("%n"));
		output.flush();
	}

	@Override
	public void stack(BufferedWriter output, String[] args, String description) throws IOException {
		this.getObjectStack().add(viewFile(args));
	}
	
	public String viewFile(String[] args) throws IOException {
		if (args.length > 1) {
			try {
				return FileIO.read(args[args.length-1]);
			} catch (FileNotFoundException e) {
				logger.error("No such file exists, '"+args[args.length-1]+"'.");
				e.printStackTrace();
				return null;
			}
		}
		else {
			logger.error("Please specify a file to be displayed.");
			return null;
		}
	}
}
