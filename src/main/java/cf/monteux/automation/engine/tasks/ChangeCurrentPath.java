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
package cf.monteux.automation.engine.tasks;

import cf.monteux.automation.engine.Vocabulary;
import cf.monteux.automation.engine.localization.Messages;
import cf.monteux.automation.engine.storage.ObjectStack;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rove Monteux
 */
public class ChangeCurrentPath extends TaskFactory {

    private static final Logger logger = LogManager.getLogger("ChangeCurrentPath");

    public ChangeCurrentPath(ObjectStack objectStack_, Vocabulary vocabulary_, String language_, Messages messages_) {
        super(objectStack_, vocabulary_, language_, messages_);
    }

    @Override
    public void run(BufferedWriter output, String[] args, String description) throws IOException {
        stack(output, args, description);
        print(output, args, description);
    }

    @Override
    public void print(BufferedWriter output, String[] args, String description)
            throws IOException {
        if (args.length > 1) {
            if (setCurrentDirectory(args[args.length-1])) {
                logger.info("Changed the current path to " + CurrentPath.path());
            } else {
                logger.error("Can not change current path to " + args[1]);
            }
        } else {
            logger.error("Please provide a path to change to.");
        }
    }

    @Override
    public void stack(BufferedWriter output, String[] args, String description)
            throws IOException {
        if (args.length > 1) {
            if (setCurrentDirectory(args[args.length-1])) {
                this.getObjectStack().add(CurrentPath.path());
                logger.info("Stored the changed working directory " + CurrentPath.path() + " into the object stack.");
            }
            else {
                logger.error("Can not change current path to " + args[1]);
            }
        }
        else {
            logger.error("Please provide a path to change to.");
        }
    }

    public static boolean setCurrentDirectory(String folderName) {
        Boolean result = false;
        File directory = new File(folderName).getAbsoluteFile();
        if (directory.exists() || directory.mkdirs()) {
            result = (System.setProperty("user.dir", directory.getAbsolutePath()) != null);
        }
        return result;
    }

}
