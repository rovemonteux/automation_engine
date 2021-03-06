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
package cf.monteux.automation.engine.io;

import cf.monteux.automation.engine.exception.ExceptionFormatter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rove Monteux
 */
public class HttpIO {

    private static final Logger logger = LogManager.getLogger("ViewFile");
    
    public static String download(String endpoint, String destination) {
        BufferedOutputStream output = null;
        try {
            URL url = new URL(endpoint);
            BufferedInputStream input = new BufferedInputStream(url.openStream());
            File file = new File(destination);
            output = new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath().toString()));
            byte[] buf = new byte[8192];
            int bytesread = 0, bytesBuffered = 0;
            while ((bytesread = input.read(buf)) > -1) {
                output.write(buf, 0, bytesread);
                bytesBuffered += bytesread;
                if (bytesBuffered > 1024 * 1024) {
                    bytesBuffered = 0;
                    output.flush();
                }
            }
            logger.info(endpoint+" has been successfully downloaded.");
        } catch (Exception e) {
            logger.error(ExceptionFormatter.format(e, ""));
            return "";
        } finally {
            if (output != null) {
                try {
                    output.flush();
                    output.close();
                } catch (Exception e) {
                }
            }
        }
        return destination;
    }

}
