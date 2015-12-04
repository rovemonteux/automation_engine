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

import java.io.IOException;
import java.io.OutputStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class LoggingOutputStream extends OutputStream {

    /**
     * Default number of bytes in the buffer.
     */
    private static final int DEFAULT_BUFFER_LENGTH = 2048;

    /**
     * Indicates stream state.
     */
    private boolean hasBeenClosed = false;

    /**
     * Internal buffer where data is stored.
     */
    private byte[] buf;

    /**
     * The number of valid bytes in the buffer.
     */
    private int count;

    /**
     * Remembers the size of the buffer.
     */
    private int curBufLength;

    /**
     * The logger to write to.
     */
    private Logger log;

    /**
     * The log level.
     */
    private Level level;

    /**
     * Creates the Logging instance to flush to the given logger.
     *
     * @param log         the Logger to write to
     * @param level       the log level
     * @throws IllegalArgumentException in case if one of arguments
     *                                  is  null.
     */
    public LoggingOutputStream(final Logger log,
                               final Level level)
            throws IllegalArgumentException {
        if (log == null || level == null) {
            throw new IllegalArgumentException(
                    "Logger or log level must be not null");
        }
        this.log = log;
        this.level = level;
        curBufLength = DEFAULT_BUFFER_LENGTH;
        buf = new byte[curBufLength];
        count = 0;
    }

    /**
     * Writes the specified byte to this output stream.
     *
     * @param b the byte to write
     * @throws IOException if an I/O error occurs.
     */
    public void write(final int b) throws IOException {
        if (hasBeenClosed) {
            throw new IOException("The stream has been closed.");
        }
        // don't log nulls
        if (b == 0) {
            return;
        }
        // would this be writing past the buffer?
        if (count == curBufLength) {
            // grow the buffer
            final int newBufLength = curBufLength +
                    DEFAULT_BUFFER_LENGTH;
            final byte[] newBuf = new byte[newBufLength];
            System.arraycopy(buf, 0, newBuf, 0, curBufLength);
            buf = newBuf;
            curBufLength = newBufLength;
        }

        buf[count] = (byte) b;
        count++;
    }

    /**
     * Flushes this output stream and forces any buffered output
     * bytes to be written out.
     */
    public void flush() {
        if (count == 0) {
            return;
        }
        final byte[] bytes = new byte[count];
        System.arraycopy(buf, 0, bytes, 0, count);
        String str = new String(bytes);
        log.log(level, str);
        count = 0;
    }

    /**
     * Closes this output stream and releases any system resources
     * associated with this stream.
     */
    public void close() {
        flush();
        hasBeenClosed = true;
    }
}
