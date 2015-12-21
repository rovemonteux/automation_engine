package net.rovemonteux.automation.engine.tasks;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rovemonteux.automation.engine.Vocabulary;
import net.rovemonteux.automation.engine.localization.Messages;
import net.rovemonteux.automation.engine.storage.ObjectStack;

/**
 * Lists all available commands to the current loaded Automation Engine.
 */
public class Help extends TaskFactory {

	private static final Logger logger = LogManager.getLogger("Help");
	
	public Help(ObjectStack objectStack_, Vocabulary vocabulary_, String language_, Messages messages_) {
		super(objectStack_, vocabulary_, language_, messages_);
	}

	@Override
	public void run(BufferedWriter output, String[] args, String description) throws IOException {
		print(output, args, description);
		stack(output, args, description);
	}
	
	@Override
	public void print(BufferedWriter output, String[] args, String description) throws IOException {
		output.write(this.getVocabulary().listAvailableTasks(this.getLanguage()));
		output.flush();
	}
	
	@Override
	public void stack(BufferedWriter output, String[] args, String description) throws IOException {
		this.getObjectStack().add(this.getVocabulary().listAvailableTasks(this.getLanguage()));
	}

}
