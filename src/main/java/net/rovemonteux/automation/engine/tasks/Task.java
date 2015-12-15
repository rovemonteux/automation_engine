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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Task {

	private String taskInterfaceName = "";
	private Method startupMethod = null;
	private Object taskClass = null;
	
	public Task(String taskInterfaceName_) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		this.setTaskInterfaceName("net.rovemonteux.automation.engine.tasks.extensions."+taskInterfaceName_);
		Class<?> driverClass = Class.forName(this.getTaskInterfaceName());
		Class[] argumentTypes = new Class[] { };
		this.setStartupMethod(driverClass.getDeclaredMethod("run", argumentTypes));
		this.setTaskClass(this.taskInterface());
	}

	public Object taskInterface() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, NoSuchMethodException, SecurityException {
        Class<?> parserClass = Class.forName(this.getTaskInterfaceName());
        Constructor<?> constructor = parserClass.getConstructor();
        Object object = (Object)constructor.newInstance();
        return object;
    }
	
	public String getTaskInterfaceName() {
		return this.taskInterfaceName;
	}

	public void setTaskInterfaceName(String hidInterfaceName) {
		this.taskInterfaceName = hidInterfaceName;
	}

	public Method getStartupMethod() {
		return startupMethod;
	}

	public void setStartupMethod(Method startupMethod) {
		this.startupMethod = startupMethod;
	}

	public Object getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(Object taskClass) {
		this.taskClass = taskClass;
	}
	
}
