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

package cf.monteux.automation.engine.io;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Utilities for handling Streams.
 * @author root
 *
 */
public class Stream {

	/**
	 * Converts an InputStream to String.
	 * 
	 * @param input	InputStream to be converted
	 * @param encoding	Encoding to use
	 * @return	Converted InputStream
	 */
	public static String InputToString(InputStream input, String encoding) {
		Scanner scanner = new Scanner(input, encoding);
		scanner.useDelimiter("\\A");
	    String result = scanner.hasNext() ? scanner.next() : "";
	    scanner.close();
	    scanner = null;
	    return result;
	}
	
}
