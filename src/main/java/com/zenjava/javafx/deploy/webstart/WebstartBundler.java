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
import com.zenjava.javafx.deploy.template.TemplateProcessor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WebstartBundler {

    private Log log;
    private TemplateProcessor templateProcessor;

    public WebstartBundler() {
        this(Log.LogLevel.info);
    }

    public WebstartBundler(Log.LogLevel logLevel) {
        this(new SimpleLog(logLevel));
    }

    public WebstartBundler(Log log) {
        this.log = log;
        this.templateProcessor = new TemplateProcessor(log);
    }

    public void bundle(WebstartBundleConfig config) throws WebstartBundlerException {

        log.info("Creating Webstart bundle");

        validate(config);

        // pass entire config objject to template engine to use as token replacements

        Map<String, Object> values = new HashMap<String, Object>();
        values.put("config", config);

        // generate the JNLP velocity template

        log.info("Generating JNLP file for Webstart Bundle");

        String jnlpTemplatePath;
        if (config.getJnlpTemplate() != null) {
            jnlpTemplatePath = config.getJnlpTemplate().getPath();
            log.debug("Using custom JNLP velocity template: '%s'", jnlpTemplatePath);
        } else {
            jnlpTemplatePath = "classpath:/webstart/default-jnlp-template.vm";
            log.debug("Using default JNLP velocity template: '%s'", jnlpTemplatePath);
        }

        File jnlpFile = new File(config.getOutputDir(), config.getJnlpFileName());
        templateProcessor.processTemplate(jnlpFile, jnlpTemplatePath, values);

        log.info("Successfully generated JNLP file at '%s'", jnlpFile);


        // generate the HTML file (if required)

        if (config.isBuildHtmlFile()) {

            log.info("Generating HTML file for Webstart Bundle");

            String htmlTemplatePath;
            if (config.getJnlpTemplate() != null) {
                htmlTemplatePath = config.getJnlpTemplate().getPath();
                log.debug("Using custom Webstart HTML velocity template: '%s'", htmlTemplatePath);
            } else {
                htmlTemplatePath = "classpath:/webstart/default-webstart-html-template.vm";
                log.debug("Using default Webstart HTML velocity template: '%s'", htmlTemplatePath);
            }

            File htmlFile = new File(config.getOutputDir(), config.getHtmlFileName());
            templateProcessor.processTemplate(htmlFile, htmlTemplatePath, values);

            log.info("Successfully generated HTML file at '%s'", htmlFile);
        }

    }

    protected void validate(WebstartBundleConfig config) throws WebstartBundlerException {

        log.debug("Validating JNLP config");
        assertNotNull("title", config.getTitle());
        assertNotNull("vendor", config.getVendor());
        assertNotNull("mainClass", config.getMainClass());
        assertNotNull("jarFile", config.getJarResources());

        if (config.isBuildHtmlFile()) {
            assertNotNull("htmlFileName", config.getHtmlFileName());
        }
    }

    private void assertNotNull(String key, Object value) throws WebstartBundlerException {
        if (value == null) {
            throw new WebstartBundlerException("A value must be set for '" + key + "'");
        }
    }
}
