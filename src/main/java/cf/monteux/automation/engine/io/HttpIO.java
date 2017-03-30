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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.URL;

/**
 *
 * @author Rove Monteux
 */
public class HttpIO {

    public static String download(String endpoint, String destination) {
        BufferedOutputStream output = null;
        try {
            URL url = new URL(endpoint);
            BufferedInputStream input = new BufferedInputStream(url.openStream());
            output = new BufferedOutputStream(new FileOutputStream("test.html"));
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
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (output != null) {
                try {
                    output.flush();
                } catch (Exception e) {
                }
            }
        }
        return destination;
    }

}
