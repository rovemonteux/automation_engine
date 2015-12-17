package net.rovemonteux.automation.engine.tasks;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rovemonteux.automation.engine.Vocabulary;
import net.rovemonteux.automation.engine.storage.ObjectStack;

public class ListStack extends TaskFactory {

	private static final Logger logger = LogManager.getLogger("ListStack");
	private ObjectStack objectStack = null;
	
	public ListStack(ObjectStack objectStack_, Vocabulary vocabulary_, String language_) {
		super(objectStack_, vocabulary_, language_);
	}
	
	@Override
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		stack(output,args,description);
		print(output,args,description);
	}

	@Override
	public void print(BufferedWriter output, String[] args, String description) throws IOException {
		String list = this.getObjectStack().list();
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
		this.getObjectStack().add(this.getObjectStack().list());
	}

}
