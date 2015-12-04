package net.rovemonteux.automation.engine.tasks;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rovemonteux.automation.engine.storage.ObjectStack;

public class EmptyStack implements TaskFactory {

	private static final Logger logger = LogManager.getLogger("EmptyStack");
	private ObjectStack objectStack = null;

	public EmptyStack(ObjectStack objectStack_) {
		this.setObjectStack_(objectStack_);
	}
	
	@Override
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		print(output,args,description);
	}

	@Override
	public void print(BufferedWriter output, String[] args, String description) throws IOException {
		stack(output,args,description);
		output.write("Emptied the object stack.");
		output.write(String.format("%n"));
		output.flush();
	}

	@Override
	public void stack(BufferedWriter output, String[] args, String description) throws IOException {
		this.getObjectStack_().clear();
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
