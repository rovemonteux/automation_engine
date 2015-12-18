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

package net.rovemonteux.automation.engine.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utilities related to File input and output.
 */
public class FileIO {

	private static final Logger logger = LogManager.getLogger("FileIO");
	
	/**
	 * Reads the contents of a given file into a String.
	 * 
	 * @param filename	The file to read from
	 * @return	The String-encapsulated file contents
	 * @throws FileNotFoundException	An error indicating that the files does not exists
	 * 
	 * @see File
	 * @see Scanner
	 */
    public static String read(String filename) throws FileNotFoundException {
    	File file = new File(filename);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\\Z");
        String result = scanner.next();
        scanner.close();
        scanner = null;
        file = null;
        return result;
    }

    /**
     * Returns an InputStream for a given file name.
     * 
     * @param filename	Name of the file
     * @return	InputStream for the given file name
     * @throws FileNotFoundException	Error when the file is not found
     */
    public static InputStream getInputStream(String filename) throws FileNotFoundException {
    	return new FileInputStream(filename);
    }
    
    /**
     * Writes data to a new file in disk.
     * 
     * @param filename	Name of the file
     * @param content	Data to be written to the file
     * @throws IOException	Error while writing data to the file
     */
    public static void write(String filename, String content) throws IOException {
        write(filename, content, false);
    }

    /**
     * Writes data to a file in disk.
     * 
     * @param filename	Name of the file
     * @param content	Data to be written to the file
     * @param append	Boolean signalling if the data is to be appended to the file
     * @throws IOException	Error while writing data to the file
     */
    public static void write(String filename, String content, boolean append) throws IOException {
    	File file = new File(filename);
        try {
        	file.getParentFile().mkdirs();
        }
        catch (Exception e) { }
        FileWriter fileWriter = new FileWriter(file, append);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(content);
        bufferedWriter.newLine();
        bufferedWriter.close();
        file = null;
        bufferedWriter = null;
        fileWriter = null;
    }

    /**
     * De-serialize an Object from a file in disk.
     * 
     * @param filename	Name of the file
     * @return	De-serialized Object
     * @throws FileNotFoundException	Error if the file does not exists
     * @throws IOException	Error while reading from the file
     * @throws ClassNotFoundException	Error when de-serializing the object
     */
    public static Object readObject(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
        BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(filename));
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        Object objectResult =  objectInput.readObject();
        objectInput.close();
        return objectResult;
    }

    /**
     * Persists an object to disk.
     * 
     * @param filename	The file name to persist the object to
     * @param objectContent	The object to be persisted
     * @throws IOException	An input and output error
     * 
     * @see ObjectOutputStream
     */
    public static void writeObject(String filename, Object objectContent) throws IOException {
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(filename, false));
        ObjectOutputStream objectOutput = new ObjectOutputStream(output);
        objectOutput.writeObject(objectContent);
        output.flush();
        output.close();
        output = null;
    }

    /**
     * Deletes a file from the disk.
     * 
     * @param filename	Name of the file
     * @return	A boolean indicating the success of the file deletion
     */
    public static boolean delete(String filename) {
        File file = new File(filename);
        boolean result = file.delete();
        file = null;
        return result;
    }

    /**
     * Checks if a file exists in the file system.
     * 
     * @param filename	The name of the file to check
     * @return	a boolean indicating the existence of the given file
     * 
     * @see File
     */
    public static boolean exists(String filename) {
        File file = new File(filename);
        boolean result = file.exists();
        file = null;
        return result;
    }

    /**
     * Recursively deletes a folder in the disk.
     * 
     * @param directory	Folder to be deleted
     * @return	A boolean indicating the success of the recursive file deletion
     */
    public static boolean deleteFolder(File directory) {
        if(directory.exists()) {
            File[] files = directory.listFiles();
            if(null!=files) {
                for(int i=0; i<files.length; i++) {
                    if(files[i].isDirectory()) {
                        deleteFolder(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
        }
        return(directory.delete());
    }

    /**
     * Returns a File Object for a given file name.
     * 
     * @param path	Path to read the file from
     * @param filename	Name of the file
     * @return	File Object for the given path and file name
     */
    public static File getFile(String path, String filename) {
    	if (!(filename.contains(path))) {
            filename = path + filename;
        }
        return new File(filename);
    }
    
    /**
     * Writes data to a file in a specified path, and debug it to a running instance of the Log Framework.
     * 
     * @param path	Full path to write the file to
     * @param filename	The file name
     * @param data	String-encapsulated data to be written to the specified file
     * @throws IOException	An input and output error
     * 
     */
    public static void write(String path, String filename, String data) throws IOException {
        write(path, filename, data.getBytes("UTF-8"));
    }

    /**
     * Writes data to a file in a specified path, and debug it to a running instance of the Log Framework.
     * 
     * @param path	Full path to write the file to
     * @param filename	The file name
     * @param data	Byte array-encapsulated data to be written to the specified file
     * @throws IOException	An input and output error
     * 
     */
    public static void write(String path, String filename, byte[] data) throws IOException {
    	File file = getFile(path, filename);
        logger.info("Writing "+filename);
        file.getParentFile().mkdirs();
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        out.write(data);
        out.flush();
        out.close();
        logger.info("Wrote "+filename);
        data = null;
        filename = null;
        out = null;
        file = null;
    }
    
    /**
     * Creates a folder in the file system.
     * 
     * @param foldername	The name of the folder to be created
     * 
     * @see File
     */
    public static void createFolder(String foldername) {
        File file = new File(foldername);
        file.mkdirs();
        file = null;
    }

    /**
     * Copies folder contents from sourceFolderPath to destinationFolderPath recursively.
     *
     * @param sourceFolderPath	The source folder path
     * @param destinationFolderPath	The destination folder path
     * @throws IOException	An input and output error
     */
    public static void copyFolderRecursively(String sourceFolderPath, String destinationFolderPath) throws IOException {
        File file = new File(sourceFolderPath);
        if (!file.isDirectory()) return;
        if (!destinationFolderPath.endsWith(File.separator)) destinationFolderPath += File.separator;

        for (final File fileEntry : file.listFiles()) {
            if (fileEntry.isDirectory()) {
                copyFolderRecursively(fileEntry.getAbsolutePath(), destinationFolderPath + fileEntry.getName());
            } else {
                String fileString = read(fileEntry.getCanonicalPath());
                write(destinationFolderPath + fileEntry.getName(), fileString);
            }
        }
    }

    /**
     * Copies a given file to another given file.
     * 
     * @param sourceFile	File to copy from
     * @param destinationFile	File to copy to
     * @return Boolean indicating the success or failure of the file copy
     */
	public static boolean copy(String sourceFile, String destinationFile) {
		FileChannel source = null;
		FileChannel destination = null;
		FileInputStream sourceStream = null;
		FileOutputStream destinationStream = null;
		try {
			sourceStream = new FileInputStream(sourceFile);
			source = sourceStream.getChannel();
			destinationStream = new FileOutputStream(destinationFile);
			destination = destinationStream.getChannel();
			destination.transferFrom(source, 0, source.size());
		} catch (Exception e) {
			logger.error("Error copying file: "+e.getLocalizedMessage());
			logger.error(e);
			return false;
		} finally {
			if (source != null) {
				try {
					source.close();
					sourceStream.close();
					source = null;
					sourceStream = null;
				} catch (IOException e) {
					logger.error(e);
				}
			}
			if (destination != null) {
				try {
					destination.close();
					destinationStream.close();
					destination = null;
					destinationStream = null;
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return true;
	}
    
	/**
	 * Gets the size of a file.
	 * 
	 * @param sourceFile	File to get the size from
	 * @return	File size
	 */
	public static long size(String sourceFile) {
		File file = new File(sourceFile);
		if (file.exists()) {
			long result = file.length();
			file = null;
			return result;
		}
		else {
			file = null;
			logger.error("File "+sourceFile+" does not exists.");
			return 0;
		}
	}
	
    /**
     * Reads the data from a file in disk.
     * 
     * @param path	Path of the file to be read
     * @param filename	Name of the file
     * @return	Data from the file
     * @throws FileNotFoundException	Error when the file has not been found
     * @throws IOException	Error while reading the file
     */
    public static String read(String path, String filename) throws FileNotFoundException, IOException {
        if (path != null && path.length() > 0) {
        	if (!(filename.contains(path))) {
        		filename = path + filename;
        	}
    	}
        return read(filename);
    }

    /**
     * Returns the file name, without a path.
     * 
     * @param filename	Name of the file
     * @return	The file name
     */
    public static String getFilename(String filename) {
        return new File(filename).getName();
    }
    
    /**
     * Returns a list of files in a given folder.
     * 
     * @param folder	Folder to get the files from
     * @return	List of files
     */
    public static ArrayList<File> listFiles(String folder) {
    	File file = new File(folder);
    	ArrayList<File> files = new ArrayList<File>(Arrays.asList(file.listFiles()));
    	file = null;
    	return files;
    }

}
