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

package net.rovemonteux.automation.engine.library.os;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Environment {

    private String hostname = "";
    private String osname = "";
    private int processors = 0;
    private String homeFolder = "";
    private String currentFolder = "";
    private int systemArchitecture = 0;
    private String currentUser = "";
    private String javaVersion = "";
    private String javaSpecificationVersion = "";

    @SuppressWarnings("static-access")
    public Environment() {
        this.setOsname(System.getProperty("os.name"));
        try {
            this.setHostname(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            this.setHostname("localhost");
        }
       
        this.setProcessors(Runtime.getRuntime().availableProcessors());
        this.setHomeFolder(System.getProperty("user.home"));
        this.setCurrentFolder(new File("").getAbsoluteFile().getAbsolutePath());
        this.setSystemArchitecture(Integer.parseInt(System.getProperty("sun.arch.data.model")));
        this.setCurrentUser(System.getProperty("user.name"));
        this.setJavaVersion(System.getProperty("java.version"));
        this.setJavaSpecificationVersion(System.getProperty("java.specification.version"));
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getOsname() {
        return osname;
    }

    public void setOsname(String osname) {
        this.osname = osname;
    }

    public int getProcessors() {
        return processors;
    }

    public void setProcessors(int processors) {
        this.processors = processors;
    }

    public String getHomeFolder() {
        return homeFolder;
    }

    public void setHomeFolder(String homeFolder) {
        this.homeFolder = homeFolder;
    }

    public String getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(String currentFolder) {
        this.currentFolder = currentFolder;
    }

    public int getSystemArchitecture() {
        return systemArchitecture;
    }

    public void setSystemArchitecture(int systemArchitecture) {
        this.systemArchitecture = systemArchitecture;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getJavaSpecificationVersion() {
        return javaSpecificationVersion;
    }

    public void setJavaSpecificationVersion(String javaSpecificationVersion) {
        this.javaSpecificationVersion = javaSpecificationVersion;
    }

    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long getMaximumMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * Creates an Object array containing the current Thread's details.
     *
     * @return	The 4-element object array containing the Thread count (int), the peak Thread count (int), the current Thread's CPU time (double) and the current Thread's user time (double)
     */
    public static Object[] getThreadDetails() {
        ThreadMXBean mxbean = ManagementFactory.getThreadMXBean();
        Object[] objects = new Object[] {0,0,0,0};
        objects[0] = mxbean.getThreadCount(); // int
        objects[1] = mxbean.getPeakThreadCount(); // int
        objects[2] = mxbean.getCurrentThreadCpuTime() / 1000000.0; // double
        objects[3] = mxbean.getCurrentThreadUserTime() / 1000000.0; // double
        mxbean = null;
        return objects;
    }

}