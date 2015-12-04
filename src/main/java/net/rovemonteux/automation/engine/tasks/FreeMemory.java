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

import net.rovemonteux.automation.engine.library.os.Environment;
import net.rovemonteux.automation.engine.storage.ObjectStack;

public class FreeMemory implements TaskFactory {
	
	private static final Logger logger = LogManager.getLogger("FreeMemory");
	private ObjectStack objectStack = null;
	
	public FreeMemory(ObjectStack objectStack_) {
		this.setObjectStack_(objectStack_);
	}

	@Override
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		stack(output, args, description);
		print(output, args, description);
	}

	@Override
	public void print(BufferedWriter output, String[] args, String description)
			throws IOException {
		output.write("Free memory: "+this.getObjectStack_().findByType("java.lang.Long"));
		output.newLine();
		output.flush();
	}

	@Override
	public void stack(BufferedWriter output, String[] args, String description)
			throws IOException {
		this.getObjectStack_().add(Environment.getFreeMemory());
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
