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

package cf.monteux.automation.engine.hid;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cf.monteux.automation.engine.Vocabulary;
import cf.monteux.automation.engine.localization.Messages;
import cf.monteux.automation.engine.storage.ObjectStack;

public class HID {

	private static final Logger logger = LogManager.getLogger("HID");
	
	private String hidInterfaceName = "";
	private String scriptFile = "";
	private String languageCode = "";
	private Method startupMethod = null;
	private Object hidClass = null;
	private Vocabulary vocabulary = null;
	private ObjectStack objectStack = null;
	private Messages messages = null;
	
	public HID(String hidInterfaceName_, Vocabulary vocabulary_, ObjectStack objectStack_, String scriptFile_, String languageCode_, Messages messages_) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		this.setObjectStack(objectStack_);
		this.setHidInterfaceName("net.rovemonteux.automation.engine.hid."+hidInterfaceName_);
		this.setScriptFile(scriptFile_);
		this.setVocabulary(vocabulary_);
		this.setLanguageCode(languageCode_);
		this.setMessages(messages_);
		Class<?> driverClass = Class.forName(this.getHidInterfaceName());
		Class[] argumentTypes = new Class[] { };
		this.setStartupMethod(driverClass.getDeclaredMethod("setup"));
		this.setHidClass(this.hidInterface());
	}

	public Object hidInterface() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, NoSuchMethodException, SecurityException {
        Class<?> parserClass = Class.forName(this.getHidInterfaceName());
        Constructor<?> constructor = parserClass.getConstructor(Vocabulary.class, ObjectStack.class, String.class, String.class, Messages.class);
        Object object = (Object)constructor.newInstance(this.getVocabulary(), this.getObjectStack(), this.getScriptFile(), this.getLanguageCode(), this.getMessages());
        return object;
    }
	
	public String getHidInterfaceName() {
		return this.hidInterfaceName;
	}

	public void setHidInterfaceName(String hidInterfaceName) {
		this.hidInterfaceName = hidInterfaceName;
	}

	public Method getStartupMethod() {
		return startupMethod;
	}

	public void setStartupMethod(Method startupMethod) {
		this.startupMethod = startupMethod;
	}

	public Object getHidClass() {
		return hidClass;
	}

	public void setHidClass(Object hidClass) {
		this.hidClass = hidClass;
	}

	public ObjectStack getObjectStack() {
		return this.objectStack;
	}

	public void setObjectStack(ObjectStack objectStack_) {
		this.objectStack = objectStack_;
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(Vocabulary vocabulary) {
		this.vocabulary = vocabulary;
	}

	public String getScriptFile() {
		return scriptFile;
	}

	public void setScriptFile(String scriptFile) {
		this.scriptFile = scriptFile;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	
}
