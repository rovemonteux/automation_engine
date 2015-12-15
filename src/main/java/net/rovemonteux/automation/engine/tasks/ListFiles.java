package net.rovemonteux.automation.engine.tasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rovemonteux.automation.engine.io.FileIO;
import net.rovemonteux.automation.engine.storage.ObjectStack;

public class ListFiles implements TaskFactory {

	private static final Logger logger = LogManager.getLogger("ListFiles");
	private ObjectStack objectStack = null;
	
	public ListFiles(ObjectStack objectStack_) {
		this.setObjectStack_(objectStack_);
	}
	
	@Override
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		stack(output,args,description);
		print(output,args,description);
	}

	@Override
	public void print(BufferedWriter output, String[] args, String description) throws IOException {
		output.write(list(args));
		output.flush();
	}

	@Override
	public void stack(BufferedWriter output, String[] args, String description) throws IOException {
		this.getObjectStack_().add(list(args));
	}

	public String list(String[] args) {
		StringBuilder result = new StringBuilder();
		String folder = ".";
		if (args.length > 2) {
			folder = args[2];
		}
		logger.debug("Listing folder "+folder);
		for (File file: FileIO.listFiles(folder)) {
			result.append(file.getName());
			result.append(String.format("%n"));
		}
		return result.toString();
	}
	
	@Override
	public ObjectStack getObjectStack_() {
		return this.objectStack;
	}

	@Override
	public void setObjectStack_(ObjectStack objectStack_) {
		this.objectStack = objectStack_;
	}

}
