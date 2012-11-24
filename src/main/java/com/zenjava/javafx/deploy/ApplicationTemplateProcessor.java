package com.zenjava.javafx.deploy;

import com.zenjava.javafx.deploy.log.Log;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ApplicationTemplateProcessor {

    private Log log;

    public ApplicationTemplateProcessor(Log log) {
        this.log = log;
    }

    public void processTemplate(ApplicationTemplate templatePath, ApplicationProfile appProfile, File outputFile)
            throws BuildException {

        log.info("Processing template '%s' to create '%s'", templatePath, outputFile);

        log.debug("Initialising velocity templating engine");
        VelocityEngine velocityEngine = new VelocityEngine();
        if (templatePath.isRelativeToClasspath()) {
            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        } else {
            velocityEngine.setProperty("resource.loader", "file");
            velocityEngine.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            velocityEngine.setProperty("file.resource.loader.path", "");
        }
        velocityEngine.init();


        // create base output directory

        File outputDir = outputFile.getParentFile();
        log.debug("Creating base output directory: '%s'", outputDir);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new BuildException("Failed to create target directory '" + outputDir + "'for generation of '" + outputFile + "'");
        }

        // setup the velocity template

        Template template = velocityEngine.getTemplate(templatePath.getPath());
        VelocityContext context = new VelocityContext();
        context.put("app", appProfile);
        context.put("fileName", outputFile.getName());

        // generate the output file using the velocity template

        log.info("Generating file using template '%s', output file is '%s'", templatePath, outputFile);
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(outputFile));
            template.merge(context, out);
        } catch (IOException e){
            throw new BuildException("Failed to create file '" + outputFile + "' from template '" + templatePath + "'", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

        log.info("Successfully generated file at '%s'", outputFile);
    }
}
