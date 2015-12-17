package net.rovemonteux.automation.engine.tasks;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rovemonteux.automation.engine.Vocabulary;
import net.rovemonteux.automation.engine.storage.ObjectStack;

public class EmptyStack extends TaskFactory {

	private static final Logger logger = LogManager.getLogger("EmptyStack");

	public EmptyStack(ObjectStack objectStack_, Vocabulary vocabulary_, String language_) {
		super(objectStack_, vocabulary_, language_);
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
		this.getObjectStack().clear();
	}

}
