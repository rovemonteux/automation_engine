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

package cf.monteux.automation.engine.storage;

import java.util.LinkedList;

/**
 * In memory cookie-like Object buffer storage.
 */
public class ObjectStack {

	private LinkedList<Object> storage = new LinkedList<Object>();
	
	public ObjectStack() {
		
	}
	
	/**
	 * Adds an Object to the stack.
	 * 
	 * @param object	Object to be added to the stack
	 */
	public void add(Object object) {
		if (!(this.getStorage().contains(object))) {
			this.getStorage().add(object);
		}
	}
	
	/**
	 * Deletes an Object from the stack by index.
	 * 
	 * @param index	Index of the item to be deleted from the stack
	 */
	public void delete(int index) {
		this.getStorage().remove(index);
	}
	
	/**
	 * Deletes an Object from the stack.
	 * 
	 * @param object	Object to be deleted from the stack;
	 */
	public void delete(Object object) {
		while (this.getStorage().contains(object)) {
			this.getStorage().remove(object);
		}
	}
	
	/**
	 * Gets an Object from the stack by index.
	 * 
	 * @param index	Index of the Object in the stack
	 * @return	Object from the stack
	 */
	public Object get(int index) {
		return this.getStorage().get(index);
	}
	
	/**
	 * Gets the first Object from the stack.
	 * 
	 * @return	First Object in the stack
	 */
	public Object getFirst() {
		return this.getStorage().getFirst();
	}
	
	/**
	 * Gets the last Object from the stack.
	 * 
	 * @return	Last Object in the stack
	 */
	public Object getLast() {
		return this.getStorage().getLast();
	}
	
	/**
	 * Returns a count of the number of items in the stack.
	 * 
	 * @return	number of items in the stack
	 */
	public int count() {
		return this.getStorage().size();
	}
	
	/**
	 * Removes all items from the stack.
	 */
	public void clear() {
		this.setStorage(new LinkedList<Object>());
	}
	
	/**
	 * Returns the Class name of an Object in the stack
	 * 
	 * @param index	Index of the item in the stack
	 * @return	Class name of the Object in the stack
	 */
	public String getObjectType(int index) {
		return this.getStorage().get(index).getClass().getName();
	}
	
	/**
	 * Lists all objects in the stack.
	 * 
	 * @return	List of all Objects in the stack
	 */
	public String list() {
		StringBuffer result = new StringBuffer();
		int counter = 0;
		for (Object object: this.getStorage()) {
			result.append(String.format("%n"));
			result.append("[");
			result.append(counter);
			result.append("] ");
			result.append(object.getClass());
			counter++;
		}
		return result.toString();
	}

	/**
	 * Find an Object in the stack by Class name.
	 * 
	 * @param type	Class name to search
	 * @return	First instance of the given Class name in the stack
	 */
	public Object findByType(String type) {
		for (Object object: this.getStorage()) {
			if (object.getClass().getName().endsWith(type)) {
				return object;
			}
		}
		return null;
	}
	
	/**
	 * Gets the Object storage.
	 * 
	 * @return	The Object storage
	 */
	public LinkedList<Object> getStorage() {
		return this.storage;
	}

	/**
	 * Sets the Object storage.
	 * 
	 * @param storage Object storage to be set
	 */
	public void setStorage(LinkedList<Object> storage) {
		this.storage = storage;
	}
	
	@Override
	public String toString() {
		return list();
	}
	
}
