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
package cf.monteux.automation.engine.net;

import cf.monteux.automation.engine.io.ExecIO;
import cf.monteux.automation.engine.io.FileIO;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Rove Monteux
 */
public class Host {

    public static String name() {
        String name = "";
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            name = addr.getHostName();
        } catch (UnknownHostException ex) {
            name = System.getenv("HOSTNAME");
            if (name == null || name.length() < 1) {
                name = System.getenv("COMPUTERNAME");
            }
            if (name == null || name.length() < 1) {
                try {
                    name = FileIO.read("/etc/hostname");
                }
                catch (Exception e) {
                    try {
                        ExecIO.read("hostname");
                    }
                    catch (Exception ignore) {
                    }
                }
            }
            if (name == null || name.length() < 1) {
                name = "mae";
            }
        }
        return name;
    }
}
