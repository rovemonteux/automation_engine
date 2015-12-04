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

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLTraverse {

	 public static Element getChild(Element parent, String name) {
	        for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
	            if (child instanceof Element && name.equals(child.getNodeName())) {
	                return (Element) child;
	            }
	        }
	        return null;
	    }

	    public static Node search(Node root, String elementName, boolean deep, boolean elementsOnly) {
	        if (!(root.hasChildNodes()))
	            return null;

	        Node matchingNode = null;
	        String nodeName = null;
	        Node child = null;

	        NodeList childNodes = root.getChildNodes();
	        int noChildren = childNodes.getLength();
	        for (int i = 0; i < noChildren; i++) {
	            if (matchingNode == null) {
	                child = childNodes.item(i);
	                nodeName = child.getNodeName();
	                if ((nodeName != null) & (nodeName.equals(elementName)))
	                    return child;
	                if (deep)
	                    matchingNode = search(child, elementName, deep, elementsOnly);
	            } else
	                break;
	        }

	        if (!elementsOnly) {
	            NamedNodeMap childAttrs = root.getAttributes();
	            noChildren = childAttrs.getLength();
	            for (int i = 0; i < noChildren; i++) {
	                if (matchingNode == null) {
	                    child = childAttrs.item(i);
	                    nodeName = child.getNodeName();
	                    if ((nodeName != null) & (nodeName.equals(elementName)))
	                        return child;
	                } else
	                    break;
	            }
	        }
	        return matchingNode;
	    }
	    
}
