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

package net.rovemonteux.automation.engine.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.rovemonteux.automation.engine.exception.StackTrace;

/**
 * Utilities for XML parsing.
 */
public class XMLIO {

	private static final Logger logger = LogManager.getLogger("XMLIO");
	
    /**
     * Reads an XML DOM Document from an InputStream.
     * 
     * @param input	InputStream sending the XML data
     * @return	Parsed XML DOM Document
     * @throws ParserConfigurationException	Error with the XML parser configuration
     * @throws IOException	Error while reading the XML data
     * @throws DOMException	Error while parsing the XML
     */
    public static Document read(InputStream input) throws ParserConfigurationException, IOException, DOMException {
        try {
            DocumentBuilder docBuilder = getDocumentBuilder(false);
            Document doc = docBuilder.parse(input, "UTF-8");
            doc.setStrictErrorChecking(false);
            doc.setXmlStandalone(true);
            return doc;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Instantiates a DocumentBuilder for XML parsing.
     * 
     * @param useexternal	Boolean used to allow the use of external Namespaces and DTDs
     * @return	Instantiated DocumentBuilder for XML parsing
     * @throws ParserConfigurationException	Error with the XML parser configuration
     */
    private static DocumentBuilder getDocumentBuilder(boolean useexternal) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        if (useexternal) {
            docFactory.setNamespaceAware(true);
            docFactory.setIgnoringComments(true);
            docFactory.setFeature("http://xml.org/sax/features/namespaces", true);
        } else {
            docFactory.setValidating(false);
            docFactory.setNamespaceAware(false);
            docFactory.setIgnoringComments(true);
            docFactory.setExpandEntityReferences(false);
            docFactory.setFeature("http://xml.org/sax/features/namespaces", false);
            docFactory.setFeature("http://xml.org/sax/features/validation", false);
            docFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            docFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        }
        if (!(useexternal)) {
            docFactory.setIgnoringElementContentWhitespace(true);
        }
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        if (!(useexternal)) {
            docBuilder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId, String systemId)
                throws SAXException, IOException {
                    return null;
                }
            });
        }
        return docBuilder;
    }

    /**
     * Reads an XML Document from a given path and a file name.
     * 
     * @param path	The path were to read the file from
     * @param filename	The file name to read the XML data from
     * @return	Parsed XML Document
     * @throws DOMException	Error while parsing the XML
     * @throws ParserConfigurationException	Error with the XML parser configuration
     * @throws IOException	Error while reading the XML data
     */
    public static Document populate(String path, String filename) throws DOMException, ParserConfigurationException, IOException {
        logger.info("XML: path: "+path+", filename: "+filename);
    	String finalfile = filename;
    	if (path != null && path.length() > 0) {
    		if (!(filename.contains(path))) {
    			finalfile = path + filename;
    		}
    	}
        logger.info("XMLIO: Trying to open file "+finalfile);
        FileInputStream fis = new FileInputStream(finalfile);
        logger.info("XMLIO: Opened input stream for "+finalfile);
        Document doc = XMLIO.read(fis);
        logger.info("XMLIO: Read doc for "+finalfile);
        fis.close();
        fis = null;
        finalfile = null;
        return doc;
    }

    /**
     * Returns a textual representation of an XML Object.
     * 
     * @param doc	XML Dom Document
     * @return	String containing a textual representation of the object
     */
    public static String asString(Document doc) {
        try {
            DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
            LSSerializer lsSerializer = domImplementation.createLSSerializer();
            LSOutput lsOutput =  domImplementation.createLSOutput();
            lsOutput.setEncoding("UTF-8");
            return lsSerializer.writeToString(doc);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                DOMSource domSource = new DOMSource(doc);
                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.transform(domSource, result);
                return writer.toString();
            } catch(Exception ex) {
                logger.error(ex);
                return StackTrace.asString(ex);
            }
        }
        
    }
}
