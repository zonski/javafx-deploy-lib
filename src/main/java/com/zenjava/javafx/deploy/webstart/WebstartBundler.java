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

import com.zenjava.javafx.deploy.log.Log;
import com.zenjava.javafx.deploy.log.SimpleLog;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WebstartBundler {

    private Log log;
    private VelocityEngine velocityEngine;

    public WebstartBundler() {
        this(Log.LogLevel.info);
    }

    public WebstartBundler(Log.LogLevel logLevel) {
        this(new SimpleLog(logLevel));
    }

    public WebstartBundler(Log log) {
        this.log = log;
    }

    public void bundle(WebstartBundleConfig config) throws WebstartBundleException {

        log.info("Creating 'Webstart' bundle");

        validate(config);

        // initialise velocity template engine

        log.debug("Initialising velocity engine");
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "file");
        velocityEngine.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        velocityEngine.setProperty("file.resource.loader.path", "");
        velocityEngine.init();


        // create base output directory

        log.debug("Creating base output directory: '%s'", config.getOutputDir());
        File jnlpFile = new File(config.getOutputDir(), config.getJnlpFileName());
        if (!jnlpFile.getParentFile().exists() && !jnlpFile.getParentFile().mkdirs()) {
            throw new WebstartBundleException("Failed to create target directory for JNLP file '" + jnlpFile + "'");
        }

        // setup the JNLP velocity template

        String templatePath;
        if (config.getJnlpTemplate() != null) {
            templatePath = config.getJnlpTemplate().getPath();
            log.debug("Using custom JNLP velocity template: '%s'", templatePath);
        } else {
            templatePath = getClass().getResource("/default-jnlp-template.vm").getPath();
            log.debug("Using default JNLP velocity template: '%s'", templatePath);
        }
        Template template = velocityEngine.getTemplate(templatePath);
        VelocityContext context = new VelocityContext();
        context.put("config", config);

        // generate the JNLP using the velocity template

        log.info("Generating JNLP file using template '%s', output file is '%s'", templatePath, jnlpFile);
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(jnlpFile));
            template.merge(context, out);
        } catch (IOException e){
            throw new WebstartBundleException("Failed to create JNLP file from template '" + jnlpFile + "'", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

        log.info("Successfully generated JNLP file at '%s'", jnlpFile);
    }

    protected void validate(WebstartBundleConfig config) throws WebstartBundleException {

        log.debug("Validating Webstart config");
        assertNotNull("title", config.getTitle());
        assertNotNull("vendor", config.getVendor());
        assertNotNull("mainClass", config.getMainClass());
        assertNotNull("jarFile", config.getJarFile());
    }

    private void assertNotNull(String key, Object value) throws WebstartBundleException {
        if (value == null) {
            throw new WebstartBundleException("A value must be set for '" + key + "'");
        }
    }
}
