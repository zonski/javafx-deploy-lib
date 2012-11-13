/*
 * The JavaFX Deploy Lib provides a core functionality for assembling JavaFX applications
 * Copyright (C) 2012  Daniel Zwolenski
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.zenjava.javafx.deploy.webstart;

import java.io.File;

public class WebstartBundleConfig {

    private File outputDir;
    private String jnlpFileName;
    private File jnlpTemplate;
    private String title;
    private String vendor;
    private String description;
    private String homepage;
    private String icon;
    private String splashImage;
    private boolean offlineAllowed;
    private String jreVersion;
    private String jreArgs;
    private String jarFile;
    private String mainClass;

    public WebstartBundleConfig(File outputDir,
                                String jnlpFileName,
                                File jnlpTemplate,
                                String title,
                                String vendor,
                                String jarFile,
                                String mainClass) {

        this.outputDir = outputDir;
        this.jnlpFileName = jnlpFileName;
        this.jnlpTemplate = jnlpTemplate;
        this.title = title;
        this.vendor = vendor;
        this.jarFile = jarFile;
        this.mainClass = mainClass;

        this.jreVersion = "1.7+";
        this.offlineAllowed = false;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    public String getJnlpFileName() {
        return jnlpFileName;
    }

    public void setJnlpFileName(String jnlpFileName) {
        this.jnlpFileName = jnlpFileName;
    }

    public File getJnlpTemplate() {
        return jnlpTemplate;
    }

    public void setJnlpTemplate(File jnlpTemplate) {
        this.jnlpTemplate = jnlpTemplate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSplashImage() {
        return splashImage;
    }

    public void setSplashImage(String splashImage) {
        this.splashImage = splashImage;
    }

    public boolean isOfflineAllowed() {
        return offlineAllowed;
    }

    public void setOfflineAllowed(boolean offlineAllowed) {
        this.offlineAllowed = offlineAllowed;
    }

    public String getJreVersion() {
        return jreVersion;
    }

    public void setJreVersion(String jreVersion) {
        this.jreVersion = jreVersion;
    }

    public String getJreArgs() {
        return jreArgs;
    }

    public void setJreArgs(String jreArgs) {
        this.jreArgs = jreArgs;
    }

    public String getJarFile() {
        return jarFile;
    }

    public void setJarFile(String jarFile) {
        this.jarFile = jarFile;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }
}