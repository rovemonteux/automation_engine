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

package cf.monteux.automation.engine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import cf.monteux.automation.engine.exception.StackTrace;
import cf.monteux.automation.engine.hid.HID;
import cf.monteux.automation.engine.hid.HIDFactory;
import cf.monteux.automation.engine.localization.Messages;
import cf.monteux.automation.engine.storage.ObjectStack;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Robot {

	private static final Logger logger = LogManager.getLogger("Robot");
	
	@Option(name="-hid",usage="HID interface name")
    private String hid = "Console";
	
	@Option(name="-vocabulary",usage="Vocabulary file name")
    private String vocabulary = "builtin";
	
	@Option(name="-script",usage="Execution script file name")
    private String script = "";
	
	@Option(name="-language",usage="Language code")
    private String language = "en";
	
	@Argument
    private List<String> arguments = new ArrayList<String>();
	
	public static void main(String[] args) {
		Robot robot = new Robot();
		try {
			CmdLineParser parser = new CmdLineParser(robot);
			parser.parseArgument(args);
			robot.startup(robot.hid, robot.vocabulary, robot.script, robot.language);
		}
		catch (Exception e) {
			logger.error(StackTrace.asString(e));
			logger.error(usage());
		}
	}

	public static String usage() {
		return "Usage: Robot -hid <HID mode> -vocabulary <vocabulary file> -script <script file> -language <language code>\n<HID mode> is the Human Interaction mode, i.e. 'Console', 'Script'\n<vocabulary file> is an XML file containg the custom vocabulary rules. Specifiy 'builtin' to use the default bundled vocabulary.\n<script file> is a file containg command to be executed in the HID upon startup.\n<language code> is the language to be used for commands and messages, i.e. 'en' for English (default) and 'pt' for Portuguese.";
	}
	
	public void startup(String hidInterface, String vocabularyFile, String scriptFile, String languageCode) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, DOMException, ParserConfigurationException, SAXException, IOException {
		Vocabulary vocabulary = new Vocabulary(vocabularyFile);
		ObjectStack objectStack = new ObjectStack();
		Messages messages = new Messages(languageCode);
		HID hid = new HID(hidInterface, vocabulary, objectStack, scriptFile, languageCode, messages);
		HIDFactory hidFactory = HIDFactory.class.cast(hid.getHidClass());
		hidFactory.setup();
		hidFactory.run();
	}
	
}
