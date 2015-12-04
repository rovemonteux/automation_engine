package net.rovemonteux.automation.engine.tasks;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rovemonteux.automation.engine.storage.ObjectStack;

public class ListStack implements TaskFactory {

	private static final Logger logger = LogManager.getLogger("ListStack");
	private ObjectStack objectStack = null;
	
	public ListStack(ObjectStack objectStack_) {
		this.setObjectStack_(objectStack_);
	}
	
	@Override
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		stack(output,args,description);
		print(output,args,description);
	}

	@Override
	public void print(BufferedWriter output, String[] args, String description) throws IOException {
		String list = this.getObjectStack_().list();
		if (list != null && list.length() > 0) {
			output.write("Objects in the stack: ");
			output.write(list);
		}
		else {
			output.write("No objects in the stack.");
		}
		output.write(String.format("%n"));
		output.flush();
		list = null;
	}

	@Override
	public void stack(BufferedWriter output, String[] args, String description) throws IOException {
		this.getObjectStack_().add(this.getObjectStack_().list());
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
