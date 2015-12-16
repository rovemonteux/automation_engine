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
import net.rovemonteux.automation.engine.hid.Script;
import net.rovemonteux.automation.engine.storage.ObjectStack;

/**
 * Runs a given script file.
 */
public class RunScript extends TaskFactory {

	private static final Logger logger = LogManager.getLogger("RunScript");
	
	public RunScript(ObjectStack objectStack_, Vocabulary vocabulary_) {
		super(objectStack_, vocabulary_);
	}
	
	@Override
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		Script script = new Script(this.getVocabulary(), this.getObjectStack(), description);
		script.setup();
		if (args.length > 2) {
			script.setScriptFile(args[args.length-1]);
			script.run();
		}
		else {
			logger.error("Please provide the name of the script to be run.");
		}
		script = null;
	}

	@Override
	public void print(BufferedWriter output, String[] args, String description) throws IOException {
		run(output, args, description);
	}

	@Override
	public void stack(BufferedWriter output, String[] args, String description) throws IOException {
		run(output, args, description);
	}

}
