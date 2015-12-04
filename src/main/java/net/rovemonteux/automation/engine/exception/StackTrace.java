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

package net.rovemonteux.automation.engine.exception;

/**
 * Utilities for dealing with Stack Traces.
 */
public class StackTrace {

	/**
	 * Returns a full Stack Trace as a String.
	 * 
	 * @param exception	Exception containing the full Stack Trace.
	 * @return	String containing the full Stack Trace
	 */
	public static String asString(Exception exception) {
		 StringBuilder fullException = new StringBuilder();
		 fullException.append(exception.getLocalizedMessage());
		 fullException.append(String.format("%n"));
		 StackTraceElement[] ste = exception.getStackTrace();
		 for (StackTraceElement element: ste) {
        	 fullException.append(element.toString());
        	 fullException.append(String.format("%n"));
         }
         Throwable cause = exception.getCause();
         if (cause != null) {
        	 fullException.append("Caused by " );
        	 fullException.append(cause.getLocalizedMessage());
        	 fullException.append(String.format("%n"));
        	 ste = cause.getStackTrace();
        	 for (StackTraceElement element: ste) {
        		 fullException.append(element.toString());
        		 fullException.append(String.format("%n"));
        	 }
        	 cause = null;
         }
         ste = null;
		 return fullException.toString();
	}
}
