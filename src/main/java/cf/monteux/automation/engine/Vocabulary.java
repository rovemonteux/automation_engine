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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cf.monteux.automation.engine.io.FileIO;
import cf.monteux.automation.engine.xml.XMLIO;

/**
 * Vocabulary configuration, binding instructions to functionality.
 */
public class Vocabulary {

	private static final Logger logger = LogManager.getLogger("Vocabulary");
	
	private String vocabularyFile = "";
	private HashMap<String, ArrayList<String>> vocabulary = null;
	private HashMap<String, ArrayList<String>> language = null;
	private HashMap<String, ArrayList<String>> taskMode = null;
	private HashMap<String, String> vocabularyProperties = null;
	
	public Vocabulary(String vocabularyFile_) throws DOMException, FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		this.setVocabularyFile(vocabularyFile_);
		this.setVocabulary(new HashMap<String, ArrayList<String>>());
		this.setLanguage(new HashMap<String, ArrayList<String>>());
		this.setTaskMode(new HashMap<String, ArrayList<String>>());
		this.setVocabularyProperties(new HashMap<String, String>());
		if (this.getVocabularyFile() != null && this.getVocabularyFile().length() > 0 && !this.getVocabularyFile().toLowerCase().trim().equals("builtin")) {
			populate(FileIO.getInputStream(this.getVocabularyFile()));
		}
		populate(getClass().getClassLoader().getResourceAsStream("vocabulary.xml"));
	}
	
	/**
	 * Populates the vocabulary memory, containing the relationship between the terms and strategies.
	 * 
	 * @param	inputStream	Input stream to read data from
	 * @throws DOMException	Error with the DOM XML parser
	 * @throws ParserConfigurationException	Error with the XML parser configuration
	 * @throws SAXException	Error with the SAX XML parser
	 * @throws IOException	Error on reading the vocabulary configuration from XML
	 * @throws FileNotFoundException	Error that the file does not exists
	 */
	public void populate(InputStream inputStream) throws DOMException, ParserConfigurationException, SAXException, IOException, FileNotFoundException {
		Document doc = XMLIO.read(inputStream);
		NodeList nodeList = doc.getElementsByTagName("term");
		for (int i=0; i<nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			NodeList children = node.getChildNodes();
			ArrayList<String> tasks = new ArrayList<String>();
			ArrayList<String> modes = new ArrayList<String>();
			String languageCode = null;
			String value = "";
			String threaded = "";
			String description = "";
			String packageName = "";
			for (int a=0; a<children.getLength(); a++) {
				Node childNode = children.item(a);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					switch(childNode.getNodeName()) {
						case "language":
							languageCode = childNode.getTextContent();
							break;
						case "value":
							value = childNode.getTextContent();
							break;
						case "threaded":
							threaded = childNode.getTextContent();
							break;
						case "description":
							description = childNode.getTextContent();
							break;
						case "task":
							tasks.add(childNode.getTextContent());
							modes.add(childNode.getAttributes().getNamedItem("mode").getTextContent());
							break;
						case "package":
							packageName = childNode.getTextContent();
							break;
					}
					if (languageCode != null) {
						if (this.getLanguage().containsKey(languageCode)) {
							this.getLanguage().get(languageCode).add(value);
						}
						else {
							ArrayList<String> values = new ArrayList<String>();
							values.add(value);
							this.getLanguage().put(languageCode, values);
						}
					}
				}
                        }
			if (value != null && value.length() > 0 && !(vocabulary.containsKey(value))) {
				vocabulary.put(value, tasks);
				taskMode.put(value, modes);
				vocabularyProperties.put(value, languageCode+"|"+threaded+"|"+description+"|"+packageName);
			}
			value = null;
			threaded = null;
			description = null;
			packageName = null;
		}
		nodeList = null;
		doc = null;
	}

	public List<ArrayList<String>> search(String arguments, String language) {
		arguments = arguments.replace("?", "").replace("!", "");
		ArrayList<String> items = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> mode = new ArrayList<String>();
		for (String item : arguments.split(" ")) {
			items.add(item);
		}
		int counter = 0;
		if (items.size() > 0) {
			String argument = items.get(0);
			while (counter < items.size()) {
				if (this.getLanguage().get(language).contains(argument)) {
					result.add(argument);
					if (this.getVocabulary().get(argument) != null) {
					for (int i = 0; i < this.getVocabulary().get(argument).size(); i++) {
						result.add(this.getVocabulary().get(argument).get(i));
						mode.add(this.getTaskMode().get(argument).get(i));
						}
					}
					break;
				} else {
					argument += " " + items.get(counter).trim();
					counter++;
				}
			}
			if (result.size() < 2 && items.size() > 1) {
				counter = 1;
				argument = "";
				while (counter < items.size()) {
					argument = items.get(counter - 1) + " " + items.get(counter);
					if (this.getLanguage().get(language).contains(argument)) {
						result.add(argument);
						for (int i = 0; i < this.getVocabulary().get(argument).size(); i++) {
							result.add(this.getVocabulary().get(argument).get(i));
							mode.add(this.getTaskMode().get(argument).get(i));
						}
						break;
					} else {
						counter++;
					}
				}
			}
			if (result.size() < 2 && items.size() > 2) {
				counter = 2;
				argument = items.get(counter - 2) + " " + items.get(counter - 1) + " " + items.get(counter);
				while (counter < items.size()) {
					if (this.getLanguage().get(language).contains(argument)) {
						result.add(argument);
						for (int i = 0; i < this.getVocabulary().get(argument).size(); i++) {
							result.add(this.getVocabulary().get(argument).get(i));
							mode.add(this.getTaskMode().get(argument).get(i));
						}
						break;
					} else {
						counter++;
					}
				}
			}
		}
		return Arrays.asList(result, mode);
	}
	
	/**
	 * Lists all available tasks.
	 * 
	 * @param languageCode	Language to be used
	 * @return	String representation of all available tasks
	 */
	public String listAvailableTasks(String languageCode) {
		StringBuilder result = new StringBuilder();
		Set<String> keySet = this.getVocabularyProperties().keySet();
		ArrayList<String> keyList = new ArrayList<String>();
		keyList.addAll(keySet);
		Collections.sort(keyList);
		for (String key: keyList) {
			String value = this.getVocabularyProperties().get(key);
			if (value.split("\\|")[0].equals(languageCode)) {
				result.append(key);
				result.append(" - ");
				result.append(value.split("\\|")[2]);
				result.append(String.format("%n"));
			}
		}
		keySet = null;
		keyList = null;
		return result.toString();
	}
	
	public HashMap<String, ArrayList<String>> getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(HashMap<String, ArrayList<String>> vocabulary) {
		this.vocabulary = vocabulary;
	}

	public HashMap<String, String> getVocabularyProperties() {
		return vocabularyProperties;
	}

	public void setVocabularyProperties(HashMap<String, String> vocabularyProperties) {
		this.vocabularyProperties = vocabularyProperties;
	}

	public String getVocabularyFile() {
		return vocabularyFile;
	}

	public void setVocabularyFile(String vocabularyFile) {
		this.vocabularyFile = vocabularyFile;
	}

	public HashMap<String, ArrayList<String>> getTaskMode() {
		return taskMode;
	}

	public void setTaskMode(HashMap<String, ArrayList<String>> taskMode) {
		this.taskMode = taskMode;
	}

	public HashMap<String, ArrayList<String>> getLanguage() {
		return language;
	}

	public void setLanguage(HashMap<String, ArrayList<String>> language) {
		this.language = language;
	}
	
}
